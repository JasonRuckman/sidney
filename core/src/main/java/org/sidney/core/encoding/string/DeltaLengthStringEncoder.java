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
package org.sidney.core.encoding.string;

import org.sidney.core.encoding.Encoding;
import org.sidney.core.encoding.bytes.RawBytesEncoder;
import org.sidney.core.encoding.int32.DeltaBitPackingInt32Encoder;
import org.sidney.core.encoding.int32.Int32Encoder;
import org.sidney.core.field.FieldAccessor;
import org.sidney.core.field.FieldUtils;
import org.sidney.core.field.UnsafeFieldAccessor;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class DeltaLengthStringEncoder implements StringEncoder {
    private final Int32Encoder lengthEncoder = new DeltaBitPackingInt32Encoder();
    private final RawBytesEncoder bytesEncoder = new RawBytesEncoder();
    private final Charset charset = Charset.forName("UTF-8");
    private FieldAccessor charArrayField;

    public DeltaLengthStringEncoder() {
        for (Field field : FieldUtils.getAllFieldsNoPrimitiveFilter(String.class)) {
            if (field.getName().equals("value")) {
                charArrayField = new UnsafeFieldAccessor(field);
                break;
            }
        }
    }

    public void writeString(String s) {
        char[] arr = (char[]) charArrayField.get(s);
        ByteBuffer bb = charset.encode(
                CharBuffer.wrap(arr)
        );

        lengthEncoder.writeInt(bb.limit());
        bytesEncoder.writeBytes(bb.array(), 0, bb.limit());
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
        bytesEncoder.reset();
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        lengthEncoder.writeToStream(outputStream);
        bytesEncoder.writeToStream(outputStream);
    }

    @Override
    public String supportedEncoding() {
        return Encoding.DELTALENGTH.name();
    }
}
