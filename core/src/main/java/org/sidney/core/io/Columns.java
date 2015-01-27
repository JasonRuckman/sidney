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
package org.sidney.core.io;

import org.sidney.core.io.Decoder;
import org.sidney.core.io.Encoder;
import org.sidney.core.io.bool.BoolDecoder;
import org.sidney.core.io.bool.BoolEncoder;
import org.sidney.core.io.bytes.BytesDecoder;
import org.sidney.core.io.bytes.BytesEncoder;
import org.sidney.core.io.float32.Float32Decoder;
import org.sidney.core.io.float32.Float32Encoder;
import org.sidney.core.io.float64.Float64Decoder;
import org.sidney.core.io.float64.Float64Encoder;
import org.sidney.core.io.int32.Int32Decoder;
import org.sidney.core.io.int32.Int32Encoder;
import org.sidney.core.io.int64.Int64Decoder;
import org.sidney.core.io.int64.Int64Encoder;
import org.sidney.core.io.string.StringDecoder;
import org.sidney.core.io.string.StringEncoder;
import org.sidney.core.serde.ReadContext;
import org.sidney.core.serde.WriteContext;

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
        private BoolEncoder definitionEncoder;
        private Int32Encoder repetitionEncoder;
        private BoolDecoder definitionDecoder;
        private Int32Decoder repetitionDecoder;

        public BoolEncoder getDefinitionEncoder() {
            return definitionEncoder;
        }

        public void setDefinitionEncoder(BoolEncoder definitionEncoder) {
            this.definitionEncoder = definitionEncoder;
        }

        public Int32Encoder getRepetitionEncoder() {
            return repetitionEncoder;
        }

        public void setRepetitionEncoder(Int32Encoder repetitionEncoder) {
            this.repetitionEncoder = repetitionEncoder;
        }

        public BoolDecoder getDefinitionDecoder() {
            return definitionDecoder;
        }

        public void setDefinitionDecoder(BoolDecoder definitionDecoder) {
            this.definitionDecoder = definitionDecoder;
        }

        public Int32Decoder getRepetitionDecoder() {
            return repetitionDecoder;
        }

        public void setRepetitionDecoder(Int32Decoder repetitionDecoder) {
            this.repetitionDecoder = repetitionDecoder;
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
            definitionEncoder.writeBool(true);
        }

        public void writeConcreteType(Class<?> type, WriteContext context) {
            throw new IllegalStateException();
        }

        public ColumnIO getChild(int index) {
            throw new IllegalStateException();
        }

        public void writeNull() {
            definitionEncoder.writeBool(false);
        }

        public void writeRepetitionCount(int value) {
            repetitionEncoder.writeInt(value);
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
            return definitionDecoder.nextBool();
        }

        public int readRepetitionCount() {
            return repetitionDecoder.nextInt();
        }

        public Class readConcreteType(ReadContext context) {
            throw new IllegalStateException();
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
