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
import org.sidney.core.io.Encoding;
import org.sidney.core.io.bytes.RawBytesDecoder;
import org.sidney.core.io.int32.DeltaBitPackingInt32Decoder;
import org.sidney.core.io.int32.Int32Decoder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class DeltaLengthStringDecoder extends BaseDecoder implements StringDecoder {
    private final Int32Decoder lengthDecoder = new DeltaBitPackingInt32Decoder();
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
