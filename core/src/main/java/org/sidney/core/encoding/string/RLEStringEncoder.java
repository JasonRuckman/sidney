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

import org.sidney.core.encoding.AbstractEncoder;
import org.sidney.core.encoding.Encoding;
import org.sidney.core.encoding.int32.BitPackingInt32Encoder;
import org.sidney.core.encoding.int32.Int32Encoder;

import java.io.IOException;
import java.io.OutputStream;

public class RLEStringEncoder extends AbstractEncoder implements StringEncoder {
    private String currentRun = "";
    private int runSize;
    private boolean isNewRun = true;
    private Int32Encoder runSizeEncoder = new BitPackingInt32Encoder();
    private StringEncoder valueEncoder = new PlainStringEncoder();

    @Override
    public void writeString(String s) {
        if (isNewRun) {
            currentRun = s;
            isNewRun = false;
        } else if (!s.equals(currentRun)) {
            flush();
            currentRun = s;
        }
        ++runSize;
    }

    @Override
    public void writeStrings(String[] s) {
        for (String str : s) {
            writeString(str);
        }
    }

    @Override
    public String supportedEncoding() {
        return Encoding.RLE.name();
    }

    private void flush() {
        valueEncoder.writeString(currentRun);
        runSizeEncoder.writeInt(runSize);

        currentRun = "";
        runSize = 0;
    }

    @Override
    public void reset() {
        valueEncoder.reset();
        runSizeEncoder.reset();
        currentRun = "";
        runSize = 0;
        isNewRun = true;
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        flush();

        valueEncoder.writeToStream(outputStream);
        runSizeEncoder.writeToStream(outputStream);
    }
}
