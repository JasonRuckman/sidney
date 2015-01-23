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
package org.sidney.core.io.int64;

import org.sidney.core.io.Encoding;
import org.sidney.core.io.int32.Int32Decoder;
import org.sidney.core.io.int32.Int32Encoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class RLE {
    public static class RLEInt64Decoder implements Int64Decoder {
        private final Int64Decoder valueDecoder = Encoding.GROUPVARINT.newInt64Decoder();
        private final Int32Decoder runSizeDecoder = Encoding.DELTABITPACKINGHYBRID.newInt32Decoder();
        private int runSize = 0;
        private long currentRun = 0;

        public long nextLong() {
            if (runSize-- == 0) {
                loadNextRun();
                runSize--;
            }

            return currentRun;
        }

        @Override
        public long[] nextLongs(int num) {
            long[] longs = new long[num];
            for (int i = 0; i < num; i++) {
                longs[i] = nextLong();
            }
            return longs;
        }

        @Override
        public String supportedEncoding() {
            return Encoding.RLE.name();
        }

        @Override
        public void readFromStream(InputStream inputStream) throws IOException {
            runSize = 0;
            currentRun = 0;

            valueDecoder.readFromStream(inputStream);
            runSizeDecoder.readFromStream(inputStream);
        }

        private void loadNextRun() {
            currentRun = valueDecoder.nextLong();
            runSize = runSizeDecoder.nextInt();
        }
    }

    public static class RLEInt64Encoder implements Int64Encoder {
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
}
