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

import com.github.jasonruckman.sidney.core.*;
import com.github.jasonruckman.sidney.core.serde.serializer.PrimitiveSerializer;
import com.github.jasonruckman.sidney.core.serde.serializer.Serializer;
import com.github.jasonruckman.sidney.core.serde.serializer.SerializerContextImpl;
import com.github.jasonruckman.sidney.core.serde.serializer.jdkserializers.Primitives;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseReader<T> {
  private InputStream inputStream;
  private ReadContext context;
  private Serializer rootSerializer;
  private int recordCount = 0;
  private SerializerContextImpl serializerContext;
  private PageHeader currentPageHeader = null;
  private boolean isOpen = false;
  private SidneyConf conf;
  private References references = new References();
  public BaseReader(SidneyConf conf, TypeRef typeRef) {
    this.conf = conf;
    this.serializerContext = new SerializerContextImpl(conf, references);
    this.rootSerializer = serializerContext.serializer(typeRef);
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
    if(conf.isReferenceTrackingEnabled()) {
      references.clear();
    }
    this.inputStream = inputStream;
    recordCount = 0;
    isOpen = true;
  }

  /**
   * Marks this reader as closed
   */
  public void close() {
    inputStream = null;
    currentPageHeader = null;
    isOpen = false;
  }

  protected Serializer getRootSerializer() {
    return rootSerializer;
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
      if(conf.isReferenceTrackingEnabled()) {
        context = new ReferenceTrackingReadContext(conf);
      } else {
        context = new ReadContextImpl(
            conf
        );
      }
      serializerContext.finish((ReadContextImpl)context);
      recordCount = currentPageHeader.getPageSize();
      context.setPageHeader(currentPageHeader);
      context.loadFromInputStream(inputStream);
    } catch (Exception e) {
      throw new SidneyException(e);
    }
  }

  public static class PrimitiveReader<T extends PrimitiveSerializer> extends BaseReader {
    private T serializer;

    public PrimitiveReader(SidneyConf conf, Class<?> type) {
      super(conf, JavaTypeRefBuilder.typeRef(type));
      serializer = (T)getRootSerializer();
    }

    public T getSerializer() {
      return serializer;
    }
  }

  public static class BoolReader extends PrimitiveReader<com.github.jasonruckman.sidney.core.serde.serializer.jdkserializers.Primitives.BooleanSerializer> {
    public BoolReader(SidneyConf conf) {
      super(conf, boolean.class);
    }

    public boolean readBoolean() {
      getContext().setColumnIndex(0);
      return getSerializer().readBoolean(getContext());
    }
  }

  public static class ByteReader extends PrimitiveReader<com.github.jasonruckman.sidney.core.serde.serializer.jdkserializers.Primitives.ByteSerializer> {
    public ByteReader(SidneyConf conf) {
      super(conf, byte.class);
    }

    public byte readByte() {
      getContext().setColumnIndex(0);
      return getSerializer().readByte(getContext());
    }
  }

  public static class CharReader extends PrimitiveReader<com.github.jasonruckman.sidney.core.serde.serializer.jdkserializers.Primitives.CharSerializer> {
    public CharReader(SidneyConf conf) {
      super(conf, char.class);
    }

    public char readChar() {
      getContext().setColumnIndex(0);
      return getSerializer().readChar(getContext());
    }
  }

  public static class ShortReader extends PrimitiveReader<Primitives.ShortSerializer> {
    public ShortReader(SidneyConf conf) {
      super(conf, short.class);
    }

    public short readShort() {
      getContext().setColumnIndex(0);
      return getSerializer().readShort(getContext());
    }
  }

  public static class IntReader extends PrimitiveReader<Primitives.IntSerializer> {
    public IntReader(SidneyConf conf) {
      super(conf, int.class);
    }

    public int readInt() {
      getContext().setColumnIndex(0);
      return getSerializer().readInt(getContext());
    }
  }

  public static class LongReader extends PrimitiveReader<Primitives.LongSerializer> {
    public LongReader(SidneyConf conf) {
      super(conf, long.class);
    }

    public long readLong() {
      getContext().setColumnIndex(0);
      return getSerializer().readLong(getContext());
    }
  }

  public static class FloatReader extends PrimitiveReader<com.github.jasonruckman.sidney.core.serde.serializer.jdkserializers.Primitives.FloatSerializer> {
    public FloatReader(SidneyConf conf) {
      super(conf, float.class);
    }

    public float readFloat() {
      getContext().setColumnIndex(0);
      return getSerializer().readFloat(getContext());
    }
  }

  public static class DoubleReader extends PrimitiveReader<com.github.jasonruckman.sidney.core.serde.serializer.jdkserializers.Primitives.DoubleSerializer> {
    public DoubleReader(SidneyConf conf) {
      super(conf, double.class);
    }

    public double readDouble() {
      getContext().setColumnIndex(0);
      return getSerializer().readDouble(getContext());
    }
  }
}