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

import org.sidney.core.io.BaseEncoder;
import org.sidney.core.io.int32.DeltaBitPackingInt32Encoder;
import org.sidney.core.io.int32.Int32Encoder;
import org.sidney.core.FieldAccessor;
import org.sidney.core.FieldUtils;
import org.sidney.core.UnsafeFieldAccessor;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;

//Special sort of encoder that is efficient on text with characters that have high unicode values, since it delta / bitpacks the values
public class CharAsIntStringEncoder extends BaseEncoder implements StringEncoder {
    private FieldAccessor charArrayField;
    private Int32Encoder lengthEncoder = new DeltaBitPackingInt32Encoder();
    private Int32Encoder charEncoder = new DeltaBitPackingInt32Encoder();

    public CharAsIntStringEncoder() {
        for (Field field : FieldUtils.getAllFieldsNoPrimitiveFilter(String.class)) {
            if (field.getName().equals("value")) {
                charArrayField = new UnsafeFieldAccessor(field);
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
    public String supportedEncoding() {
        return null;
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
