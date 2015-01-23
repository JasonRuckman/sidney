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

import org.sidney.core.io.BaseDecoder;
import org.sidney.core.io.BaseEncoder;
import org.sidney.core.io.Encoding;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class Plain {
    public static class PlainStringDecoder extends BaseDecoder implements StringDecoder {
        private final Charset charset = Charset.forName("UTF-8");

        public String readString() {
            int length = readIntInternal();
            byte[] bytes = readBytesInternal(length);
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
        public String supportedEncoding() {
            return Encoding.PLAIN.name();
        }
    }

    public static class PlainStringEncoder extends BaseEncoder implements StringEncoder {
        private final Charset charset = Charset.forName("UTF-8");

        public void writeString(String s) {
            ByteBuffer bb = charset.encode(s);
            writeIntInternal(bb.limit());
            writeBytesInternal(bb.array(), 0, bb.limit());
        }

        @Override
        public void writeStrings(String[] strings) {
            for (String s : strings) {
                writeString(s);
            }
        }

        @Override
        public void reset() {
            super.reset();
        }

        @Override
        public String supportedEncoding() {
            return null;
        }
    }
}
