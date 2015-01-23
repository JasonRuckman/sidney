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
import org.sidney.core.io.Encoding;
import org.sidney.core.io.bytes.RawBytesDecoder;
import org.sidney.core.io.bytes.RawBytesEncoder;
import org.sidney.core.io.int32.DeltaBitPacking;
import org.sidney.core.io.int32.Int32Decoder;
import org.sidney.core.io.int32.Int32Encoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class DeltaLength {
    private static final Encoding LENGTH_ENCODING = Encoding.DELTABITPACKINGHYBRID;

    public static class DeltaLengthStringDecoder extends BaseDecoder implements StringDecoder {
        private final Int32Decoder lengthDecoder = LENGTH_ENCODING.newInt32Decoder();
        private final RawBytesDecoder bytesDecoder = new RawBytesDecoder();
        private final Charset charset = Charset.forName("UTF-8");

        public String readString() {
            int length = lengthDecoder.nextInt();
            byte[] bytes = bytesDecoder.readBytes(length);

            return charset.decode(ByteBuffer.wrap(bytes)).toString();
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
            bytesDecoder.readFromStream(inputStream);
        }

        @Override
        public String supportedEncoding() {
            return Encoding.DELTALENGTH.name();
        }
    }

    public static class DeltaLengthStringEncoder implements StringEncoder {
        private final Int32Encoder lengthEncoder = LENGTH_ENCODING.newInt32Encoder();
        private final RawBytesEncoder bytesEncoder = new RawBytesEncoder();
        private final Charset charset = Charset.forName("UTF-8");
        private Accessors.FieldAccessor charArrayField;

        public DeltaLengthStringEncoder() {
            for (Field field : Fields.getAllFieldsNoPrimitiveFilter(String.class)) {
                if (field.getName().equals("value")) {
                    charArrayField = Accessors.newAccessor(field);
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
}
