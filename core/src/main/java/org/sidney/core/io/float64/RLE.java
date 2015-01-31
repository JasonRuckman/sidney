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
package org.sidney.core.io.float64;

import org.sidney.core.io.Encoding;
import org.sidney.core.io.int32.Int32Decoder;
import org.sidney.core.io.int32.Int32Encoder;
import org.sidney.core.io.int64.Int64Decoder;
import org.sidney.core.io.int64.Int64Encoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class RLE {
    private static final Encoding VALUE_ENCODING = Encoding.PLAIN;
    private static final Encoding RUN_SIZE_ENCODING = Encoding.PLAIN;

    public static class RLEFloat64Decoder implements Float64Decoder {
        private final Int64Decoder valueDecoder = VALUE_ENCODING.newInt64Decoder();
        private final Int32Decoder runSizeDecoder = RUN_SIZE_ENCODING.newInt32Decoder();
        private int runSize = 0;
        private double currentRun = 0;

        @Override
        public double nextDouble() {
            if (runSize-- == 0) {
                loadNextRun();
                runSize--;
            }

            return currentRun;
        }

        @Override
        public double[] nextDoubles(int num) {
            double[] floats = new double[num];
            for (int i = 0; i < num; i++) {
                floats[i] = nextDouble();
            }
            return floats;
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
            currentRun = Double.longBitsToDouble(valueDecoder.nextLong());
            runSize = runSizeDecoder.nextInt();
        }
    }

    public static class RLEFloat64Encoder implements Float64Encoder {
        private final Int64Encoder valueEncoder = VALUE_ENCODING.newInt64Encoder();
        private final Int32Encoder runSizeEncoder = RUN_SIZE_ENCODING.newInt32Encoder();
        private double currentRun = 0;
        private int runSize = 0;
        private boolean isNewRun = true;

        @Override
        public void writeDouble(double value) {
            if (isNewRun) {
                currentRun = value;
                isNewRun = false;
            } else if (Double.compare(currentRun, value) != 0) {
                flush();
                currentRun = value;
            }
            ++runSize;
        }

        @Override
        public void writeDoubles(double[] floats) {
            for (double v : floats) {
                writeDouble(v);
            }
        }

        @Override
        public String supportedEncoding() {
            return Encoding.RLE.name();
        }

        private void flush() {
            long floatBits = Double.doubleToLongBits(currentRun);

            valueEncoder.writeLong(floatBits);
            runSizeEncoder.writeInt(runSize);

            currentRun = 0;
            runSize = 0;
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
    }
}