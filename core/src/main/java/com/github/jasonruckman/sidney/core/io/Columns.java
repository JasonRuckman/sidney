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
import com.github.jasonruckman.sidney.core.io.input.Input;
import com.github.jasonruckman.sidney.core.io.int32.Int32Decoder;
import com.github.jasonruckman.sidney.core.io.int32.Int32Encoder;
import com.github.jasonruckman.sidney.core.io.int64.Int64Decoder;
import com.github.jasonruckman.sidney.core.io.int64.Int64Encoder;
import com.github.jasonruckman.sidney.core.io.output.Output;
import com.github.jasonruckman.sidney.core.io.string.StringDecoder;
import com.github.jasonruckman.sidney.core.io.string.StringEncoder;
import com.github.jasonruckman.sidney.core.serde.Contexts;

import java.io.OutputStream;

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
      this.encoder.writeBool(value, getColumnOutput());
    }

    @Override
    public boolean readBoolean() {
      return decoder.nextBool();
    }

    @Override
    public Encoder getEncoder() {
      return encoder;
    }

    @Override
    public Decoder getDecoder() {
      return decoder;
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
      this.encoder.writeBytes(bytes, getColumnOutput());
    }

    @Override
    public byte[] readBytes() {
      //fix this call, the arg is ignored
      return decoder.readBytes(0);
    }

    @Override
    public Encoder getEncoder() {
      return encoder;
    }

    @Override
    public Decoder getDecoder() {
      return decoder;
    }
  }

  public abstract static class ColumnIO {
    private MetaEncoding encoding;
    private Output columnOutput;
    private Output repetitionOutput;
    private Output definitionOutput;
    private Output referencesOutput;
    private Input columnInput;
    private Input repetitionInput;
    private Input definitionInput;
    private Input referencesInput;

    public Input getColumnInput() {
      return columnInput;
    }

    public void setColumnInput(Input columnInput) {
      this.columnInput = columnInput;
    }

    public Input getRepetitionInput() {
      return repetitionInput;
    }

    public void setRepetitionInput(Input repetitionInput) {
      this.repetitionInput = repetitionInput;
    }

    public Input getDefinitionInput() {
      return definitionInput;
    }

    public void setDefinitionInput(Input definitionInput) {
      this.definitionInput = definitionInput;
    }

    public Input getReferencesInput() {
      return referencesInput;
    }

    public void setReferencesInput(Input referencesInput) {
      this.referencesInput = referencesInput;
    }

    public Output getColumnOutput() {
      return columnOutput;
    }

    public void setColumnOutput(Output columnOutput) {
      this.columnOutput = columnOutput;
    }

    public Output getRepetitionOutput() {
      return repetitionOutput;
    }

    public void setRepetitionOutput(Output repetitionOutput) {
      this.repetitionOutput = repetitionOutput;
    }

    public Output getDefinitionOutput() {
      return definitionOutput;
    }

    public void setDefinitionOutput(Output definitionOutput) {
      this.definitionOutput = definitionOutput;
    }

    public Output getReferencesOutput() {
      return referencesOutput;
    }

    public void setReferencesOutput(Output referencesOutput) {
      this.referencesOutput = referencesOutput;
    }

    public MetaEncoding getEncoding() {
      return encoding;
    }

    public void setEncoding(MetaEncoding encoding) {
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
      encoding.writeNullMarker(true, getDefinitionOutput());
    }

    public void writeConcreteType(Class<?> type, Contexts.WriteContext context) {
      throw new IllegalStateException();
    }

    public void writeNull() {
      encoding.writeNullMarker(false, getDefinitionOutput());
    }

    public void writeRepetitionCount(int value) {
      encoding.writeRepetitionCount(value, getRepetitionOutput());
    }

    public void writeReference(int definition) {
      encoding.writeDefinition(definition, getReferencesOutput());
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

    public Class readConcreteType(Contexts.ReadContext context) {
      throw new IllegalStateException();
    }

    public int readDefinition() {
      return encoding.readDefinition();
    }

    public Encoder getEncoder() {
      return null;
    }

    public Decoder getDecoder() {
      return null;
    }

    public boolean shouldLoadColumn() {
      return getDecoder() != null;
    }

    public boolean shouldLoadReferences() {
      return getEncoding() instanceof ReferencesMetaEncoding;
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
      encoder.writeDouble(value, getColumnOutput());
    }

    @Override
    public double readDouble() {
      return decoder.nextDouble();
    }

    @Override
    public Encoder getEncoder() {
      return encoder;
    }

    @Override
    public Decoder getDecoder() {
      return decoder;
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
      encoder.writeFloat(value, getColumnOutput());
    }

    @Override
    public float readFloat() {
      return decoder.nextFloat();
    }

    @Override
    public Encoder getEncoder() {
      return encoder;
    }

    @Override
    public Decoder getDecoder() {
      return decoder;
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
      encoder.writeInt(value, getColumnOutput());
    }

    @Override
    public int readInt() {
      return decoder.nextInt();
    }

    @Override
    public Encoder getEncoder() {
      return encoder;
    }

    @Override
    public Decoder getDecoder() {
      return decoder;
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
      encoder.writeLong(value, getColumnOutput());
    }

    @Override
    public long readLong() {
      return decoder.nextLong();
    }

    @Override
    public Encoder getEncoder() {
      return encoder;
    }

    @Override
    public Decoder getDecoder() {
      return decoder;
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
      encoder.writeString(value, getColumnOutput());
    }

    @Override
    public String readString() {
      return decoder.readString();
    }

    @Override
    public Encoder getEncoder() {
      return encoder;
    }

    @Override
    public Decoder getDecoder() {
      return decoder;
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
    public void writeConcreteType(Class<?> type, Contexts.WriteContext context) {
      concreteTypeEncoder.writeInt(context.getPageHeader().valueForType(type), getColumnOutput());
    }

    @Override
    public Class readConcreteType(Contexts.ReadContext context) {
      return context.getPageHeader().readConcreteType(concreteTypeDecoder.nextInt());
    }

    @Override
    public Encoder getEncoder() {
      return concreteTypeEncoder;
    }

    @Override
    public Decoder getDecoder() {
      return concreteTypeDecoder;
    }
  }
}