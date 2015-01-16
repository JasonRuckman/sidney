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
import org.sidney.core.encoding.int32.Int32Decoder;

import java.io.IOException;
import java.io.InputStream;

public class RLEStringDecoder extends AbstractDecoder implements StringDecoder {
    private final StringDecoder valueDecoder = Encoding.PLAIN.newStringDecoder();
    private final Int32Decoder runSizeDecoder = Encoding.BITPACKED.newInt32Decoder();
    private int runSize = 0;
    private String currentRun = null;

    @Override
    public void populateBufferFromStream(InputStream inputStream) throws IOException {
        runSize = 0;
        currentRun = null;

        valueDecoder.populateBufferFromStream(inputStream);
        runSizeDecoder.populateBufferFromStream(inputStream);
    }

    @Override
    public String readString() {
        if (runSize-- == 0) {
            loadNextRun();
            runSize--;
        }
        return currentRun;
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
        return Encoding.RLE.name();
    }

    private void loadNextRun() {
        currentRun = valueDecoder.readString();
        runSize = runSizeDecoder.nextInt();
    }
}
