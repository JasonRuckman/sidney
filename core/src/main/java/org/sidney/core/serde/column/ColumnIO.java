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
package org.sidney.core.serde.column;

import org.sidney.core.encoding.Decoder;
import org.sidney.core.encoding.Encoder;
import org.sidney.core.encoding.bool.BoolDecoder;
import org.sidney.core.encoding.bool.BoolEncoder;
import org.sidney.core.encoding.int32.Int32Decoder;
import org.sidney.core.encoding.int32.Int32Encoder;
import org.sidney.core.serde.ReadContext;
import org.sidney.core.serde.WriteContext;

import java.util.ArrayList;
import java.util.List;

public class ColumnIO {
    private static final List EMPTY = new ArrayList<>();
    private BoolEncoder definitionEncoder;
    private Int32Encoder repetitionEncoder;
    private BoolDecoder definitionDecoder;
    private Int32Decoder repetitionDecoder;

    public void setRepetitionEncoder(Int32Encoder repetitionEncoder) {
        this.repetitionEncoder = repetitionEncoder;
    }

    public void setDefinitionEncoder(BoolEncoder definitionEncoder) {
        this.definitionEncoder = definitionEncoder;
    }

    public void setRepetitionDecoder(Int32Decoder repetitionDecoder) {
        this.repetitionDecoder = repetitionDecoder;
    }

    public void setDefinitionDecoder(BoolDecoder definitionDecoder) {
        this.definitionDecoder = definitionDecoder;
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