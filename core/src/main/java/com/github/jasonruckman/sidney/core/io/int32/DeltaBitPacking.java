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
package com.github.jasonruckman.sidney.core.io.int32;

import com.github.jasonruckman.sidney.bitpacking.Int32BytePacker;
import com.github.jasonruckman.sidney.bitpacking.Packers;
import com.github.jasonruckman.sidney.core.Bytes;
import com.github.jasonruckman.sidney.core.io.BaseDecoder;
import com.github.jasonruckman.sidney.core.io.BaseEncoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.github.jasonruckman.sidney.core.Bytes.readIntFromStream;
import static com.github.jasonruckman.sidney.core.Bytes.writeIntToStream;

public class DeltaBitPacking {
    public static class DeltaBitPackingInt32Decoder extends BaseDecoder implements Int32Decoder {
        private int[] intBuffer = new int[0];
        private int currentIndex = 0;
        private int blockSize;
        private int totalValueCount;
        private int firstValue;
        private boolean isFirstValue = true;

        @Override
        public int nextInt() {
            if (isFirstValue) {
                isFirstValue = false;
                return firstValue;
            }

            if (currentIndex == intBuffer.length) {
                loadNextMiniBlock();
            }

            return intBuffer[currentIndex++];
        }

        @Override
        public int[] nextInts(int num) {
            int[] ints = new int[num];
            for (int i = 0; i < num; i++) {
                ints[i] = nextInt();
            }
            return ints;
        }

        private void reset() {
            currentIndex = 0;
            totalValueCount = 0;
            blockSize = 0;
            isFirstValue = true;
            setPosition(0);
        }

        @Override
        public void readFromStream(InputStream inputStream) throws IOException {
            reset();

            totalValueCount = readIntFromStream(inputStream);
            firstValue = readIntFromStream(inputStream);
            blockSize = readIntFromStream(inputStream);
            intBuffer = new int[blockSize];
            if (totalValueCount <= 1) {
                return;
            }
            int bufferSize = readIntFromStream(inputStream);
            setBuffer(new byte[bufferSize]);
            Bytes.readFully(getBuffer(), inputStream);
            loadNextMiniBlock();
        }

        private void loadNextMiniBlock() {
            currentIndex = 0;
            int bitWidth = readIntFromBuffer();
            int minDelta = readIntFromBuffer();
            int numValuesToRead = readIntFromBuffer();
            totalValueCount -= numValuesToRead;
            int strideSize = Bytes.sizeInBytes(8, bitWidth);
            Int32BytePacker packer = Packers.LITTLE_ENDIAN.packer32(bitWidth);

            for (int i = 0; i < numValuesToRead; i += 8) {
                packer.unpack8Values(getBuffer(), getPosition(), intBuffer, i);
                incrementPosition(strideSize);
            }
            for (int i = 0; i < numValuesToRead; i++) {
                intBuffer[i] += ((i == 0) ? firstValue : intBuffer[i - 1]) + minDelta;
            }
        }
    }

    /**
     * Inspired by parquet's delta packer.  Packs ints into blocks of 128, bitpacked on the max bitwidth of the block
     */
    public static class DeltaBitPackingInt32Encoder extends BaseEncoder implements Int32Encoder {
        public static final int DEFAULT_BLOCK_SIZE = 128;

        private int firstValue = 0;
        private int prevValue = 0;
        private int totalValueCount = 0;
        private int[] currentMiniBlock;
        private int currentIndex = 0;
        private int currentMinDelta = Integer.MAX_VALUE;
        private int miniBlockSize;

        public DeltaBitPackingInt32Encoder() {
            this(DEFAULT_BLOCK_SIZE);
        }

        public DeltaBitPackingInt32Encoder(int miniBlockSize) {
            this.miniBlockSize = miniBlockSize;
            this.currentMiniBlock = new int[miniBlockSize];
        }

        public void writeInt(int value) {
            if (++totalValueCount == 1) {
                firstValue = value;
                prevValue = value;
                return;
            }
            int delta = value - prevValue;
            prevValue = value;
            currentMiniBlock[currentIndex++] = delta;
            currentMinDelta = Math.min(delta, currentMinDelta);
            if (currentIndex == miniBlockSize) {
                flushMiniBlock();
            }
        }

        @Override
        public void writeInts(int[] values) {
            for (int v : values) {
                writeInt(v);
            }
        }

        @Override
        public void reset() {
            currentIndex = 0;
            currentMinDelta = 0;
            prevValue = 0;
            totalValueCount = 0;
            firstValue = 0;
            setPosition(0);
        }

        @Override
        public void writeToStream(OutputStream outputStream) throws IOException {
            flushMiniBlock();
            writeIntToStream(totalValueCount, outputStream);
            writeIntToStream(firstValue, outputStream);
            writeIntToStream(miniBlockSize, outputStream);
            if (totalValueCount <= 1) {
                return;
            }
            writeIntToStream(getPosition(), outputStream);
            outputStream.write(getBuffer(), 0, getPosition());
        }

        private void flushMiniBlock() {
            if (currentIndex == 0 || totalValueCount == 1) {
                return;
            }

            int currentMaxBitwidth = 1;
            for (int i = 0; i < currentIndex; i++) {
                int num = currentMiniBlock[i] - currentMinDelta;
                currentMiniBlock[i] = num;
                currentMaxBitwidth = Math.max(currentMaxBitwidth, (32 - Integer.numberOfLeadingZeros(num)));
            }

            int numToWrite = Math.max(currentIndex, 8);
            writeIntToBuffer(currentMaxBitwidth);
            writeIntToBuffer(currentMinDelta);
            writeIntToBuffer(currentIndex);

            int strideSize = Bytes.sizeInBytes(8, currentMaxBitwidth);
            Int32BytePacker packer = Packers.LITTLE_ENDIAN.packer32(currentMaxBitwidth);
            ensureCapacity(Bytes.sizeInBytes(numToWrite, currentMaxBitwidth));
            for (int i = 0; i < numToWrite; i += 8) {
                packer.pack8Values(currentMiniBlock, i, getBuffer(), getPosition());
                incrementPosition(strideSize);
            }
            currentIndex = 0;
            currentMinDelta = Integer.MAX_VALUE;
            prevValue = firstValue;
        }
    }
}
