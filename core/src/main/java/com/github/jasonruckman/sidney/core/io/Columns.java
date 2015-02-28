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
import com.github.jasonruckman.sidney.core.io.character.CharDecoder;
import com.github.jasonruckman.sidney.core.io.character.CharEncoder;
import com.github.jasonruckman.sidney.core.io.float32.Float32Decoder;
import com.github.jasonruckman.sidney.core.io.float32.Float32Encoder;
import com.github.jasonruckman.sidney.core.io.float64.Float64Decoder;
import com.github.jasonruckman.sidney.core.io.float64.Float64Encoder;
import com.github.jasonruckman.sidney.core.io.input.Input;
import com.github.jasonruckman.sidney.core.io.int16.ShortDecoder;
import com.github.jasonruckman.sidney.core.io.int16.ShortEncoder;
import com.github.jasonruckman.sidney.core.io.int32.Int32Decoder;
import com.github.jasonruckman.sidney.core.io.int32.Int32Encoder;
import com.github.jasonruckman.sidney.core.io.int64.Int64Decoder;
import com.github.jasonruckman.sidney.core.io.int64.Int64Encoder;
import com.github.jasonruckman.sidney.core.io.output.Output;
import com.github.jasonruckman.sidney.core.io.string.StringDecoder;
import com.github.jasonruckman.sidney.core.io.string.StringEncoder;
import com.github.jasonruckman.sidney.core.serde.Contexts;

public class Columns {
  public abstract static class ColumnIO {
    private MetaEncoding encoding;

    public void setColumnOutput(Output columnOutput) {
      if (shouldLoadColumn() && !getEncoder().isDirect()) {
        getEncoder().asIndirect().setOutput(columnOutput);
      }
    }

    public void setRepetitionOutput(Output repetitionOutput) {
      if (!getEncoding().getRepetitionEncoder().isDirect()) {
        getEncoding().getRepetitionEncoder().asIndirect().setOutput(repetitionOutput);
      }
    }

    public void setDefinitionOutput(Output definitionOutput) {
      if (!getEncoding().getDefinitionEncoder().isDirect()) {
        getEncoding().getDefinitionEncoder().asIndirect().setOutput(definitionOutput);
      }
    }

    public void setReferencesOutput(Output referencesOutput) {
      if (shouldLoadReferences()) {
        ReferencesMetaEncoding rme = (ReferencesMetaEncoding) getEncoding();
        if (!rme.getReferencesEncoder().isDirect()) {
          rme.getReferencesEncoder().asIndirect().setOutput(referencesOutput);
        }
      }
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

    public void writeBooleans(boolean[] values) {
      throw new IllegalStateException();
    }

    public void writeChar(char value) {
      throw new IllegalStateException();
    }

    public void writeChars(char[] values) {
      throw new IllegalStateException();
    }

    public void writeShort(short value) {
      throw new IllegalStateException();
    }

    public void writeShorts(short[] values) {
      throw new IllegalStateException();
    }

    public void writeInt(int value) {
      throw new IllegalStateException();
    }

    public void writeInts(int[] values) {
      throw new IllegalStateException();
    }

    public void writeLong(long value) {
      throw new IllegalStateException();
    }

    public void writeLongs(long[] values) {
      throw new IllegalStateException();
    }

    public void writeFloat(float value) {
      throw new IllegalStateException();
    }

    public void writeFloats(float[] values) {
      throw new IllegalStateException();
    }

    public void writeDouble(double value) {
      throw new IllegalStateException();
    }

    public void writeDoubles(double[] values) {
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

    public void writeConcreteType(Class<?> type, Contexts.WriteContext context) {
      throw new IllegalStateException();
    }

    public void writeNull() {
      encoding.writeNullMarker(false);
    }

    public void writeRepetitionCount(int value) {
      encoding.writeRepetitionCount(value);
    }

    public void writeReference(int definition) {
      encoding.writeDefinition(definition);
    }

    public boolean readBoolean() {
      throw new IllegalStateException();
    }

    public boolean[] readBooleans(int num) {
      throw new IllegalStateException();
    }

    public short readShort() {
      throw new IllegalStateException();
    }

    public short[] readShorts(int num) {
      throw new IllegalStateException();
    }

    public char readChar() {
      throw new IllegalStateException();
    }

    public char[] readChars(int num) {
      throw new IllegalStateException();
    }
    public int readInt() {
      throw new IllegalStateException();
    }

    public int[] readInts(int num) {
      throw new IllegalStateException();
    }

    public long readLong() {
      throw new IllegalStateException();
    }

    public long[] readLongs(int num) {
      throw new IllegalStateException();
    }

    public float readFloat() {
      throw new IllegalStateException();
    }

    public float[] readFloats(int num) {
      throw new IllegalStateException();
    }

    public double readDouble() {
      throw new IllegalStateException();
    }

    public double[] readDoubles(int num) {
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
    public void writeBooleans(boolean[] values) {
      encoder.writeBools(values);
    }

    @Override
    public boolean readBoolean() {
      return decoder.nextBool();
    }

    @Override
    public boolean[] readBooleans(int num) {
      return decoder.nextBools(num);
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
      this.encoder.writeBytes(bytes);
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
    public void writeDoubles(double[] values) {
      encoder.writeDoubles(values);
    }

    @Override
    public double readDouble() {
      return decoder.nextDouble();
    }

    @Override
    public double[] readDoubles(int num) {
      return decoder.nextDoubles(num);
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
      encoder.writeFloat(value);
    }

    @Override
    public void writeFloats(float[] values) {
      encoder.writeFloats(values);
    }

    @Override
    public float readFloat() {
      return decoder.nextFloat();
    }

    @Override
    public float[] readFloats(int num) {
      return decoder.nextFloats(num);
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

  public static class CharColumnIO extends ColumnIO {
    private final CharEncoder encoder;
    private final CharDecoder decoder;

    public CharColumnIO(CharEncoder encoder, CharDecoder decoder) {
      this.encoder = encoder;
      this.decoder = decoder;
    }

    @Override
    public void writeChar(char value) {
      encoder.writeChar(value);
    }

    @Override
    public void writeChars(char[] values) {
      encoder.writeChars(values);
    }

    @Override
    public char readChar() {
      return decoder.nextChar();
    }

    @Override
    public char[] readChars(int num) {
      return decoder.nextChars(num);
    }

    @Override
    public CharEncoder getEncoder() {
      return encoder;
    }

    @Override
    public CharDecoder getDecoder() {
      return decoder;
    }
  }

  public static class ShortColumnIO extends ColumnIO {
    private final ShortEncoder encoder;
    private final ShortDecoder decoder;

    public ShortColumnIO(ShortEncoder encoder, ShortDecoder decoder) {
      this.encoder = encoder;
      this.decoder = decoder;
    }

    @Override
    public void writeShort(short value) {
      encoder.writeShort(value);
    }

    @Override
    public void writeShorts(short[] values) {
      encoder.writeShorts(values);
    }

    @Override
    public short readShort() {
      return decoder.nextShort();
    }

    @Override
    public short[] readShorts(int num) {
      return decoder.nextShorts(num);
    }

    @Override
    public ShortEncoder getEncoder() {
      return encoder;
    }

    @Override
    public ShortDecoder getDecoder() {
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
      encoder.writeInt(value);
    }

    @Override
    public void writeInts(int[] values) {
      encoder.writeInts(values);
    }

    @Override
    public int readInt() {
      return decoder.nextInt();
    }

    @Override
    public int[] readInts(int num) {
      return decoder.nextInts(num);
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
      encoder.writeLong(value);
    }

    @Override
    public void writeLongs(long[] values) {
      encoder.writeLongs(values);
    }

    @Override
    public long readLong() {
      return decoder.nextLong();
    }

    @Override
    public long[] readLongs(int num) {
      return decoder.nextLongs(num);
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
      encoder.writeString(value);
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
      concreteTypeEncoder.writeInt(context.getPageHeader().valueForType(type));
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