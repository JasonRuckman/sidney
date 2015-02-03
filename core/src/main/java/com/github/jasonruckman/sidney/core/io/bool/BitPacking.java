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
package com.github.jasonruckman.sidney.core.io.bool;

import com.github.jasonruckman.sidney.core.io.BaseDecoder;
import com.github.jasonruckman.sidney.core.io.BaseEncoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Encodes bools as single bits, packed 64 to a long
 */
public class BitPacking {
    public static class BitPackingBoolDecoder extends BaseDecoder implements BoolDecoder {
        private int currentBitIndex = 0;
        private long word = 0;

        @Override
        public boolean nextBool() {
            boolean result = (word & (1L << currentBitIndex)) > 0;

            if (++currentBitIndex == 32) {
                loadNextWord();
            }

            return result;
        }

        @Override
        public boolean[] nextBools(int num) {
            boolean[] booleans = new boolean[num];
            for (int i = 0; i < num; i++) {
                booleans[i] = nextBool();
            }
            return booleans;
        }

        @Override
        public void readFromStream(InputStream inputStream) throws IOException {
            super.readFromStream(inputStream);

            loadNextWord();
        }

        private void loadNextWord() {
            word = readLongFromBuffer();
            currentBitIndex = 0;
        }
    }

    public static class BitPackingBoolEncoder extends BaseEncoder implements BoolEncoder {
        private int currentBitIndex = 0;
        private long word = 0;

        @Override
        public void writeBool(boolean value) {
            if (value) {
                word |= (1L << currentBitIndex);
            }

            if (++currentBitIndex == 32) {
                flushWord();
            }
        }

        @Override
        public void writeBools(boolean[] values) {
            for (boolean b : values) {
                writeBool(b);
            }
        }

        @Override
        public void reset() {
            super.reset();

            currentBitIndex = 0;
            word = 0;
        }

        @Override
        public void writeToStream(OutputStream outputStream) throws IOException {
            //account for the current byte we are on
            flushWord();
            super.writeToStream(outputStream);
        }

        private void flushWord() {
            writeLongToBuffer(word);

            currentBitIndex = 0;
            word = 0;
        }
    }
}
