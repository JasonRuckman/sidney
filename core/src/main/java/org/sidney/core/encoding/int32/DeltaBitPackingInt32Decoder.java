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
package org.sidney.core.encoding.int32;

import org.sidney.bitpacking.Int32BytePacker;
import org.sidney.bitpacking.Packers;
import org.sidney.core.Bytes;
import org.sidney.core.encoding.AbstractDecoder;
import org.sidney.core.encoding.Encoding;

import java.io.IOException;
import java.io.InputStream;

import static org.sidney.core.encoding.io.StreamUtils.readIntFromStream;

public class DeltaBitPackingInt32Decoder extends AbstractDecoder implements Int32Decoder {
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
    public void populateBufferFromStream(InputStream inputStream) throws IOException {
        reset();
        totalValueCount = readIntFromStream(inputStream);
        firstValue = readIntFromStream(inputStream);
        blockSize = readIntFromStream(inputStream);
        intBuffer = new int[blockSize];
        if (totalValueCount <= 1) {
            return;
        }
        int bufferSize = readIntFromStream(inputStream);
        ensureSize(bufferSize);
        inputStream.read(getBuffer());
        loadNextMiniBlock();
    }

    @Override
    public String supportedEncoding() {
        return Encoding.DELTABITPACKINGHYBRID.name();
    }

    private void loadNextMiniBlock() {
        currentIndex = 0;
        int bitWidth = readIntLE();
        int minDelta = readIntLE();
        int numValuesToRead = readIntLE();
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