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
package org.sidney.core.io.string;

import org.sidney.core.Accessors;
import org.sidney.core.Fields;
import org.sidney.core.io.BaseDecoder;
import org.sidney.core.io.BaseEncoder;
import org.sidney.core.io.int32.DeltaBitPacking;
import org.sidney.core.io.int32.Int32Decoder;
import org.sidney.core.io.int32.Int32Encoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;

public class CharAsInt {
    public static class CharAsIntStringDecoder extends BaseDecoder implements StringDecoder {
        private final Int32Decoder lengthDecoder = new DeltaBitPacking.DeltaBitPackingInt32Decoder();
        private final Int32Decoder characterDecoder = new DeltaBitPacking.DeltaBitPackingInt32Decoder();

        @Override
        public String readString() {
            char[] arr = new char[lengthDecoder.nextInt()];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = (char) characterDecoder.nextInt();
            }
            return new String(arr);
        }

        @Override
        public String[] readStrings(int num) {
            String[] strings = new String[num];
            for (int i = 0; i < num; i++) {
                strings[i] = readString();
            }
            return strings;
        }

        @Override
        public void readFromStream(InputStream inputStream) throws IOException {
            lengthDecoder.readFromStream(inputStream);
            characterDecoder.readFromStream(inputStream);
        }
    }

    //Special sort of encoder that is efficient on text with characters that have high unicode values, since it delta / bitpacks the values
    public static class CharAsIntStringEncoder extends BaseEncoder implements StringEncoder {
        private Accessors.FieldAccessor charArrayField;
        private Int32Encoder lengthEncoder = new DeltaBitPacking.DeltaBitPackingInt32Encoder();
        private Int32Encoder charEncoder = new DeltaBitPacking.DeltaBitPackingInt32Encoder();

        public CharAsIntStringEncoder() {
            for (Field field : Fields.getAllFieldsNoPrimitiveFilter(String.class)) {
                if (field.getName().equals("value")) {
                    charArrayField = Accessors.newAccessor(field);
                    break;
                }
            }
        }

        public void writeString(String s) {
            char[] arr = (char[]) charArrayField.get(s);
            lengthEncoder.writeInt(s.length());
            for (char c : arr) {
                charEncoder.writeInt(c);
            }
        }

        @Override
        public void writeStrings(String[] s) {
            for (String str : s) {
                writeString(str);
            }
        }

        @Override
        public void reset() {
            lengthEncoder.reset();
            charEncoder.reset();
        }

        @Override
        public void writeToStream(OutputStream outputStream) throws IOException {
            lengthEncoder.writeToStream(outputStream);
            charEncoder.writeToStream(outputStream);
        }
    }
}
