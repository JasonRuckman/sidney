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
package com.github.jasonruckman.sidney.core.io;

import com.github.jasonruckman.sidney.core.io.bool.BoolDecoder;
import com.github.jasonruckman.sidney.core.io.bool.BoolEncoder;
import com.github.jasonruckman.sidney.core.io.bytes.BytesDecoder;
import com.github.jasonruckman.sidney.core.io.bytes.BytesEncoder;
import com.github.jasonruckman.sidney.core.io.float32.Float32Decoder;
import com.github.jasonruckman.sidney.core.io.float32.Float32Encoder;
import com.github.jasonruckman.sidney.core.io.float64.Float64Decoder;
import com.github.jasonruckman.sidney.core.io.float64.Float64Encoder;
import com.github.jasonruckman.sidney.core.io.int32.Int32Decoder;
import com.github.jasonruckman.sidney.core.io.int32.Int32Encoder;
import com.github.jasonruckman.sidney.core.io.int64.Int64Decoder;
import com.github.jasonruckman.sidney.core.io.int64.Int64Encoder;
import com.github.jasonruckman.sidney.core.io.string.StringDecoder;
import com.github.jasonruckman.sidney.core.io.string.StringEncoder;
import com.github.jasonruckman.sidney.core.serde.ReadContext;
import com.github.jasonruckman.sidney.core.serde.WriteContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Columns {
  public static class BoolColumnIO extends ColumnIO {
    private final BoolEncoder encoder;
    private final BoolDecoder decoder;

    public BoolColumnIO(BoolEncoder encoder, BoolDecoder decoder) {
      this.encoder = encoder;
      this.decoder = decoder;
    }

    @Override
    public void writeBoolean(boolean value) {
      this.encoder.writeBool(value);
    }

    @Override
    public boolean readBoolean() {
      return decoder.nextBool();
    }

    @Override
    public List<Encoder> getEncoders() {
      return Arrays.asList((Encoder) encoder);
    }

    @Override
    public List<Decoder> getDecoders() {
      return Arrays.asList((Decoder) decoder);
    }
  }

  public static class BytesColumnIO extends ColumnIO {
    private final BytesEncoder encoder;
    private final BytesDecoder decoder;

    public BytesColumnIO(BytesEncoder encoder, BytesDecoder decoder) {
      this.encoder = encoder;
      this.decoder = decoder;
    }

    @Override
    public void writeBytes(byte[] bytes) {
      this.encoder.writeBytes(bytes);
    }

    @Override
    public byte[] readBytes() {
      //fix this call, the arg is ignored
      return decoder.readBytes(0);
    }

    @Override
    public List<Encoder> getEncoders() {
      return Arrays.asList((Encoder) encoder);
    }

    @Override
    public List<Decoder> getDecoders() {
      return Arrays.asList((Decoder) decoder);
    }
  }

  public static class ColumnIO {
    private static final List EMPTY = new ArrayList<>();
    private DefRepEncoding encoding;

    public DefRepEncoding getEncoding() {
      return encoding;
    }

    public void setEncoding(DefRepEncoding encoding) {
      this.encoding = encoding;
    }

    public void writeBoolean(boolean value) {
      throw new IllegalStateException();
    }

    public void writeInt(int value) {
      throw new IllegalStateException();
    }

    public void writeLong(long value) {
      throw new IllegalStateException();
    }

    public void writeFloat(float value) {
      throw new IllegalStateException();
    }

    public void writeDouble(double value) {
      throw new IllegalStateException();
    }

    public void writeBytes(byte[] bytes) {
      throw new IllegalStateException();
    }

    public void writeString(String value) {
      throw new IllegalStateException();
    }

    public void writeNotNull() {
      encoding.writeNullMarker(true);
    }

    public void writeConcreteType(Class<?> type, WriteContext context) {
      throw new IllegalStateException();
    }

    public void writeNull() {
      encoding.writeNullMarker(false);
    }

    public void writeRepetitionCount(int value) {
      encoding.writeRepetitionCount(value);
    }

    public void writeDefinition(int definition) {
      encoding.writeDefinition(definition);
    }

    public boolean readBoolean() {
      throw new IllegalStateException();
    }

    public int readInt() {
      throw new IllegalStateException();
    }

    public long readLong() {
      throw new IllegalStateException();
    }

    public float readFloat() {
      throw new IllegalStateException();
    }

    public double readDouble() {
      throw new IllegalStateException();
    }

    public String readString() {
      throw new IllegalStateException();
    }

    public byte[] readBytes() {
      throw new IllegalStateException();
    }

    public boolean readNullMarker() {
      return encoding.readNullMarker();
    }

    public int readRepetitionCount() {
      return encoding.readRepetitionCount();
    }

    public Class readConcreteType(ReadContext context) {
      throw new IllegalStateException();
    }

    public int readDefinition() {
      return encoding.readDefinition();
    }

    public List<Encoder> getEncoders() {
      return EMPTY;
    }

    public List<Decoder> getDecoders() {
      return EMPTY;
    }
  }

  public static class DoubleColumnIO extends ColumnIO {
    private final Float64Encoder encoder;
    private final Float64Decoder decoder;

    public DoubleColumnIO(Float64Encoder encoder, Float64Decoder decoder) {
      this.encoder = encoder;
      this.decoder = decoder;
    }

    @Override
    public void writeDouble(double value) {
      encoder.writeDouble(value);
    }

    @Override
    public double readDouble() {
      return decoder.nextDouble();
    }

    @Override
    public List<Encoder> getEncoders() {
      return Arrays.asList((Encoder) encoder);
    }

    @Override
    public List<Decoder> getDecoders() {
      return Arrays.asList((Decoder) decoder);
    }
  }

  public static class FloatColumnIO extends ColumnIO {
    private final Float32Encoder encoder;
    private final Float32Decoder decoder;

    public FloatColumnIO(Float32Encoder encoder, Float32Decoder decoder) {
      this.encoder = encoder;
      this.decoder = decoder;
    }

    @Override
    public void writeFloat(float value) {
      encoder.writeFloat(value);
    }

    @Override
    public float readFloat() {
      return decoder.nextFloat();
    }

    @Override
    public List<Encoder> getEncoders() {
      return Arrays.asList((Encoder) encoder);
    }

    @Override
    public List<Decoder> getDecoders() {
      return Arrays.asList((Decoder) decoder);
    }
  }

  public static class IntColumnIO extends ColumnIO {
    private final Int32Encoder encoder;
    private final Int32Decoder decoder;

    public IntColumnIO(Int32Encoder encoder, Int32Decoder decoder) {
      this.encoder = encoder;
      this.decoder = decoder;
    }

    @Override
    public void writeInt(int value) {
      encoder.writeInt(value);
    }

    @Override
    public int readInt() {
      return decoder.nextInt();
    }

    @Override
    public List<Encoder> getEncoders() {
      return Arrays.asList((Encoder) encoder);
    }

    @Override
    public List<Decoder> getDecoders() {
      return Arrays.asList((Decoder) decoder);
    }
  }

  public static class LongColumnIO extends ColumnIO {
    private final Int64Encoder encoder;
    private final Int64Decoder decoder;

    public LongColumnIO(Int64Encoder encoder, Int64Decoder decoder) {
      this.encoder = encoder;
      this.decoder = decoder;
    }

    @Override
    public void writeLong(long value) {
      encoder.writeLong(value);
    }

    @Override
    public long readLong() {
      return decoder.nextLong();
    }

    @Override
    public List<Encoder> getEncoders() {
      return Arrays.asList((Encoder) encoder);
    }

    @Override
    public List<Decoder> getDecoders() {
      return Arrays.asList((Decoder) decoder);
    }
  }

  public static class StringColumnIO extends ColumnIO {
    private final StringEncoder encoder;
    private final StringDecoder decoder;

    public StringColumnIO(StringEncoder encoder, StringDecoder decoder) {
      this.encoder = encoder;
      this.decoder = decoder;
    }

    @Override
    public void writeString(String value) {
      encoder.writeString(value);
    }

    @Override
    public String readString() {
      return decoder.readString();
    }

    @Override
    public List<Encoder> getEncoders() {
      return Arrays.asList((Encoder) encoder);
    }

    @Override
    public List<Decoder> getDecoders() {
      return Arrays.asList((Decoder) decoder);
    }
  }

  public static class TypeColumnIO extends ColumnIO {
    private Int32Encoder concreteTypeEncoder;
    private Int32Decoder concreteTypeDecoder;

    public TypeColumnIO(Int32Encoder concreteTypeEncoder, Int32Decoder concreteTypeDecoder) {
      this.concreteTypeEncoder = concreteTypeEncoder;
      this.concreteTypeDecoder = concreteTypeDecoder;
    }

    @Override
    public void writeConcreteType(Class<?> type, WriteContext context) {
      concreteTypeEncoder.writeInt(context.getPageHeader().valueForType(type));
    }

    @Override
    public Class readConcreteType(ReadContext context) {
      return context.getPageHeader().readConcreteType(concreteTypeDecoder.nextInt());
    }

    @Override
    public List<Encoder> getEncoders() {
      return Arrays.asList((Encoder) concreteTypeEncoder);
    }

    @Override
    public List<Decoder> getDecoders() {
      return Arrays.asList((Decoder) concreteTypeDecoder);
    }
  }
}