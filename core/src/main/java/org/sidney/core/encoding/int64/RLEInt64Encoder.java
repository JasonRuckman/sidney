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
package org.sidney.core.encoding.int64;

import org.sidney.core.encoding.Encoding;
import org.sidney.core.encoding.int32.DeltaBitPackingInt32Encoder;
import org.sidney.core.encoding.int32.Int32Encoder;

import java.io.IOException;
import java.io.OutputStream;

public class RLEInt64Encoder implements Int64Encoder {
    private Int64Encoder valueEncoder = Encoding.GROUPVARINT.newInt64Encoder();
    private Int32Encoder runSizeEncoder = Encoding.DELTABITPACKINGHYBRID.newInt32Encoder();

    private long currentRun = 0;
    private int runSize = 0;
    private boolean isNewRun = true;

    @Override
    public void writeLong(long value) {
        if (isNewRun) {
            currentRun = value;
            isNewRun = false;
        } else if (currentRun != value) {
            flush();
            currentRun = value;
        }
        ++runSize;
    }

    @Override
    public void writeLongs(long[] longs) {
        for (long v : longs) {
            writeLong(v);
        }
    }

    @Override
    public String supportedEncoding() {
        return Encoding.RLE.name();
    }

    @Override
    public void reset() {
        valueEncoder.reset();
        runSizeEncoder.reset();
        isNewRun = true;
        currentRun = 0;
        runSize = 0;
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        flush();

        valueEncoder.writeToStream(outputStream);
        runSizeEncoder.writeToStream(outputStream);
    }

    private void flush() {
        valueEncoder.writeLong(currentRun);
        runSizeEncoder.writeInt(runSize);

        currentRun = 0;
        runSize = 0;
    }
}