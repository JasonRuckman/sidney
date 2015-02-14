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

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public abstract class BaseWriter<T> {
  private final TypeRef typeRef;
  private OutputStream outputStream;
  private int recordCount = 0;
  private WriteContext writeContext;
  private SerializerContextImpl serializerContext;
  private boolean isOpen = false;
  private Serializer rootSerializer;
  private SidneyConf conf;
  private References references = new References();
  public BaseWriter(SidneyConf conf, TypeRef typeRef) {
    this.conf = conf;
    this.typeRef = typeRef;
    this.serializerContext = new SerializerContextImpl(conf, references);
    this.rootSerializer = serializerContext.serializer(typeRef);
    if (conf.isReferenceTrackingEnabled()) {
      this.writeContext = new ReferenceTrackingWriteContext(new PageHeader(), conf);
    } else {
      this.writeContext = new WriteContextImpl(new PageHeader(), conf);
    }

    this.serializerContext.finish((WriteContextImpl)writeContext);
  }

  protected Serializer getRootSerializer() {
    return rootSerializer;
  }

  protected WriteContext getWriteContext() {
    return writeContext;
  }

  /**
   * Write the given value
   */
  public void write(T value) {
    if (!isOpen) {
      throw new SidneyException("Cannot write to a closed writer");
    }
    writeContext.setColumnIndex(0);
    rootSerializer.writeValue(value, writeContext);

    if (++recordCount == conf.getPageSize()) {
      flush();
    }
  }

  /**
   * Open this writer against the given {@link java.io.OutputStream}
   */
  public void open(OutputStream outputStream) {
    if(conf.isReferenceTrackingEnabled()) {
      references.clear();
    }

    this.outputStream = outputStream;
    this.recordCount = 0;
    this.isOpen = true;
    this.writeContext.setPageHeader(new PageHeader());
  }

  public void flush() {
    flushPage(false);
  }

  /**
   * Flush the last page and mark the writer as closed
   */
  public void close() {
    flushPage(true);
    isOpen = false;
  }

  public Class<T> getType() {
    return (Class<T>) typeRef.getType();
  }

  private void flushPage(boolean isLastPage) {
    try {
      writePage(isLastPage);
      writeContext.setPageHeader(new PageHeader());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void writePage(boolean isLastPage) throws IOException {
    Bytes.writeBoolToStream(isLastPage, outputStream);
    Bytes.writeIntToStream(recordCount, outputStream);
    Bytes.writeIntToStream(writeContext.getPageHeader().getClassToValueMap().size(), outputStream);
    for (Map.Entry<Class, Integer> entry : writeContext.getPageHeader().getClassToValueMap().entrySet()) {
      Bytes.writeStringToStream(entry.getKey().getName(), outputStream);
      Bytes.writeIntToStream(entry.getValue(), outputStream);
    }
    recordCount = 0;
    writeContext.flushToOutputStream(outputStream);
  }

  public static class ByteWriter extends PrimitiveWriter<com.github.jasonruckman.sidney.core.serde.serializer.jdkserializers.Primitives.ByteSerializer> {
    public ByteWriter(SidneyConf conf) {
      super(conf, byte.class);
    }

    public void writeByte(byte value) {
      getWriteContext().setColumnIndex(0);
      getSerializer().writeByte(value, getWriteContext());
    }
  }

  public static class CharWriter extends PrimitiveWriter<com.github.jasonruckman.sidney.core.serde.serializer.jdkserializers.Primitives.CharSerializer> {
    public CharWriter(SidneyConf conf) {
      super(conf, char.class);
    }

    public void writeChar(char value) {
      getWriteContext().setColumnIndex(0);
      getSerializer().writeChar(value, getWriteContext());
    }
  }

  public static class ShortWriter extends PrimitiveWriter<Primitives.ShortSerializer> {
    public ShortWriter(SidneyConf conf) {
      super(conf, short.class);
    }

    public void writeShort(short value) {
      getWriteContext().setColumnIndex(0);
      getSerializer().writeShort(value, getWriteContext());
    }
  }

  public static class IntWriter extends PrimitiveWriter<Primitives.IntSerializer> {
    public IntWriter(SidneyConf conf) {
      super(conf, int.class);
    }

    public void writeInt(int value) {
      getWriteContext().setColumnIndex(0);
      getSerializer().writeInt(value, getWriteContext());
    }
  }

  public static class LongWriter extends PrimitiveWriter<Primitives.LongSerializer> {
    public LongWriter(SidneyConf conf) {
      super(conf, long.class);
    }

    public void writeLong(long value) {
      getWriteContext().setColumnIndex(0);
      getSerializer().writeLong(value, getWriteContext());
    }
  }

  public static class FloatWriter extends PrimitiveWriter<com.github.jasonruckman.sidney.core.serde.serializer.jdkserializers.Primitives.FloatSerializer> {
    public FloatWriter(SidneyConf conf) {
      super(conf, float.class);
    }

    public void writeFloat(float value) {
      getWriteContext().setColumnIndex(0);
      getSerializer().writeFloat(value, getWriteContext());
    }
  }

  public static class DoubleWriter extends PrimitiveWriter<Primitives.DoubleSerializer> {
    public DoubleWriter(SidneyConf conf) {
      super(conf, double.class);
    }

    public void writeDouble(double value) {
      getWriteContext().setColumnIndex(0);
      getSerializer().writeDouble(value, getWriteContext());
    }
  }

  static abstract class PrimitiveWriter<T extends PrimitiveSerializer> extends BaseWriter {
    private T serializer;

    public PrimitiveWriter(SidneyConf conf, Class<?> type) {
      super(conf, JavaTypeRefBuilder.typeRef(type));
      serializer = (T) getRootSerializer();
    }

    public T getSerializer() {
      return serializer;
    }
  }

  public static class BoolWriter extends PrimitiveWriter<Primitives.BooleanSerializer> {
    public BoolWriter(SidneyConf conf) {
      super(conf, boolean.class);
    }

    public void writeBool(boolean value) {
      getWriteContext().setColumnIndex(0);
      getSerializer().writeBoolean(value, getWriteContext());
    }
  }
}