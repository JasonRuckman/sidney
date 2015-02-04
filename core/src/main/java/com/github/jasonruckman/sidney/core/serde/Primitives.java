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

import com.github.jasonruckman.sidney.core.JavaTypeRefBuilder;
import com.github.jasonruckman.sidney.core.SidneyConf;

public class Primitives {
  static abstract class PrimitiveWriter extends BaseWriter {
    public PrimitiveWriter(SidneyConf conf, Class<?> type) {
      super(conf, JavaTypeRefBuilder.typeRef(type));
    }
  }

  public static class BoolWriter extends PrimitiveWriter {
    public BoolWriter(SidneyConf conf) {
      super(conf, boolean.class);
    }

    public void writeBool(boolean value) {
      getWriteContext().writeBool(value);
    }
  }

  public static class ByteWriter extends PrimitiveWriter {
    public ByteWriter(SidneyConf conf) {
      super(conf, byte.class);
    }

    public void writeByte(byte value) {
      getWriteContext().writeByte(value);
    }
  }

  public static class CharWriter extends PrimitiveWriter {
    public CharWriter(SidneyConf conf) {
      super(conf, char.class);
    }

    public void writeChar(char value) {
      getWriteContext().writeChar(value);
    }
  }

  public static class ShortWriter extends PrimitiveWriter {
    public ShortWriter(SidneyConf conf) {
      super(conf, short.class);
    }

    public void writeShort(short value) {
      getWriteContext().writeShort(value);
    }
  }

  public static class IntWriter extends PrimitiveWriter {
    public IntWriter(SidneyConf conf) {
      super(conf, int.class);
    }

    public void writeInt(int value) {
      getWriteContext().writeInt(value);
    }
  }

  public static class LongWriter extends PrimitiveWriter {
    public LongWriter(SidneyConf conf) {
      super(conf, long.class);
    }

    public void writeLong(long value) {
      getWriteContext().writeLong(value);
    }
  }

  public static class FloatWriter extends PrimitiveWriter {
    public FloatWriter(SidneyConf conf) {
      super(conf, float.class);
    }
  }

  public static class DoubleWriter extends PrimitiveWriter {
    public DoubleWriter(SidneyConf conf) {
      super(conf, double.class);
    }

    public void writeDouble(double value) {
      getWriteContext().writeDouble(value);
    }
  }

  public static class PrimitiveReader extends BaseReader {
    public PrimitiveReader(SidneyConf conf, Class<?> type) {
      super(conf, JavaTypeRefBuilder.typeRef(type));
    }
  }

  public static class BoolReader extends PrimitiveReader {
    public BoolReader(SidneyConf conf) {
      super(conf, boolean.class);
    }

    public boolean readBoolean() {
      return getContext().readBoolean();
    }
  }

  public static class ByteReader extends PrimitiveReader {
    public ByteReader(SidneyConf conf) {
      super(conf, byte.class);
    }

    public byte readByte() {
      return getContext().readByte();
    }
  }

  public static class CharReader extends PrimitiveReader {
    public CharReader(SidneyConf conf) {
      super(conf, char.class);
    }

    public char readChar() {
      return getContext().readChar();
    }
  }

  public static class ShortReader extends PrimitiveReader {
    public ShortReader(SidneyConf conf) {
      super(conf, short.class);
    }

    public short readShort() {
      return getContext().readShort();
    }
  }

  public static class IntReader extends PrimitiveReader {
    public IntReader(SidneyConf conf) {
      super(conf, int.class);
    }

    public int readInt() {
      return getContext().readInt();
    }
  }

  public static class LongReader extends PrimitiveReader {
    public LongReader(SidneyConf conf) {
      super(conf, long.class);
    }

    public long readLong() {
      return getContext().readLong();
    }
  }

  public static class FloatReader extends PrimitiveReader {
    public FloatReader(SidneyConf conf) {
      super(conf, float.class);
    }

    public float readFloat() {
      return getContext().readFloat();
    }
  }

  public static class DoubleReader extends PrimitiveReader {
    public DoubleReader(SidneyConf conf) {
      super(conf, double.class);
    }

    public double readDouble() {
      return getContext().readDouble();
    }
  }
}