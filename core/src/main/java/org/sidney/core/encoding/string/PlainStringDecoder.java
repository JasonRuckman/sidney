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

import org.sidney.core.encoding.AbstractDecoder;
import org.sidney.core.encoding.Encoding;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class PlainStringDecoder extends AbstractDecoder implements StringDecoder {
    private final Charset charset = Charset.forName("UTF-8");

    public String readString() {
        int length = readIntLE();
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
