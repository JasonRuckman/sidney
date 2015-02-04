/**
 * Copyright 2014 Jason Ruckman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jasonruckman.sidney.core.serde;

import com.github.jasonruckman.sidney.core.Bytes;
import com.github.jasonruckman.sidney.core.SidneyConf;
import com.github.jasonruckman.sidney.core.SidneyException;
import com.github.jasonruckman.sidney.core.TypeRef;
import com.github.jasonruckman.sidney.core.serde.serializer.Serializer;
import com.github.jasonruckman.sidney.core.serde.serializer.SerializerContextImpl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseReader<T> {
  private Class type;
  private InputStream inputStream;
  private ReadContext context;
  private Serializer rootSerializer;
  private int recordCount = 0;
  private SerializerContextImpl serializerContext;
  private PageHeader currentPageHeader = null;
  private boolean isOpen = false;
  private SidneyConf conf;

  public BaseReader(SidneyConf conf, TypeRef typeRef) {
    this.conf = conf;
    this.serializerContext = new SerializerContextImpl(conf);
    this.rootSerializer = serializerContext.serializer(typeRef);
  }

  public Class getType() {
    return type;
  }

  public InputStream getInputStream() {
    return inputStream;
  }

  public ReadContext getContext() {
    return context;
  }

  /**
   * Check for new items
   *
   * @return whether there are more items
   */
  public boolean hasNext() {
    if (currentPageHeader == null) {
      loadNextPage();
      return hasNext();
    }

    if (recordCount-- > 0) {
      return true;
    }

    if (!currentPageHeader.isLastPage()) {
      loadNextPage();
      return hasNext();
    }

    return false;
  }

  /**
   * Read the next item from the stream
   *
   * @return the next item
   */
  public T read() {
    if (!isOpen) {
      throw new SidneyException("Reader is not open.");
    }
    context.setColumnIndex(0);
    return (T) rootSerializer.readValue(context);
  }

  public List<T> readAll() {
    List<T> items = new ArrayList<>();
    while (hasNext()) {
      items.add(read());
    }
    return items;
  }

  /**
   * Open the given {@link java.io.InputStream} for reading.
   */
  public void open(InputStream inputStream) {
    this.inputStream = inputStream;
    this.recordCount = 0;
    this.isOpen = true;
  }

  /**
   * Marks this reader as closed
   */
  public void close() {
    inputStream = null;
    isOpen = false;
  }

  private void loadNextPage() {
    try {
      currentPageHeader = new PageHeader();
      currentPageHeader.setLastPage(Bytes.readBoolFromStream(inputStream));
      currentPageHeader.setPageSize(Bytes.readIntFromStream(inputStream));
      int mapSize = Bytes.readIntFromStream(inputStream);
      for (int i = 0; i < mapSize; i++) {
        Class<?> clazz = Class.forName(Bytes.readStringFromStream(inputStream));
        int value = Bytes.readIntFromStream(inputStream);
        currentPageHeader.getValueToClassMap().put(value, clazz);
      }
      ColumnReader reader = new ColumnReader();
      context = new ReadContextImpl(
          reader, conf
      );
      serializerContext.finish(reader);
      recordCount = currentPageHeader.getPageSize();
      context.setPageHeader(currentPageHeader);
      context.loadFromInputStream(inputStream);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}