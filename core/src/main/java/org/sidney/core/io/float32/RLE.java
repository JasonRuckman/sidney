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
package org.sidney.core.io.float32;

import org.sidney.core.io.Encoding;
import org.sidney.core.io.int32.BitPacking;
import org.sidney.core.io.int32.DeltaBitPacking;
import org.sidney.core.io.int32.Int32Decoder;
import org.sidney.core.io.int32.Int32Encoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class RLE {
    private static final Encoding VALUE_ENCODING = Encoding.PLAIN;
    private static final Encoding RUN_SIZE_ENCODING = Encoding.PLAIN;

    public static class RLEFloat32Decoder implements Float32Decoder {
        private final Int32Decoder valueDecoder = VALUE_ENCODING.newInt32Decoder();
        private final Int32Decoder runSizeDecoder = RUN_SIZE_ENCODING.newInt32Decoder();
        private int runSize = 0;
        private float currentRun = 0;

        public float nextFloat() {
            if (runSize-- == 0) {
                loadNextRun();
                runSize--;
            }

            return currentRun;
        }

        @Override
        public float[] nextFloats(int num) {
            float[] floats = new float[num];
            for (int i = 0; i < num; i++) {
                floats[i] = nextFloat();
            }
            return floats;
        }

        @Override
        public void readFromStream(InputStream inputStream) throws IOException {
            runSize = 0;
            currentRun = 0;

            valueDecoder.readFromStream(inputStream);
            runSizeDecoder.readFromStream(inputStream);
        }

        private void loadNextRun() {
            currentRun = Float.intBitsToFloat(valueDecoder.nextInt());
            runSize = runSizeDecoder.nextInt();
        }
    }

    public static class RLEFloat32Encoder implements Float32Encoder {
        private final Int32Encoder valueEncoder = VALUE_ENCODING.newInt32Encoder();
        private final Int32Encoder runSizeEncoder = RUN_SIZE_ENCODING.newInt32Encoder();
        private float currentRun = 0;
        private int runSize = 0;
        private boolean isNewRun = true;

        @Override
        public void writeFloat(float value) {
            if (isNewRun) {
                currentRun = value;
                isNewRun = false;
            } else if (Float.compare(currentRun, value) != 0) {
                flush();
                currentRun = value;
            }
            ++runSize;
        }

        @Override
        public void writeFloats(float[] floats) {
            for (float v : floats) {
                writeFloat(v);
            }
        }

        private void flush() {
            int floatBits = Float.floatToIntBits(currentRun);

            valueEncoder.writeInt(floatBits);
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
