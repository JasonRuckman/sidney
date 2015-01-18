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
import org.sidney.core.encoding.BaseDecoder;
import org.sidney.core.encoding.Encoding;

import java.io.IOException;
import java.io.InputStream;

//fastpfor is better than this impl, but its relatively competitive if i want to remove the dep
public class BitPackingInt32Decoder extends BaseDecoder implements Int32Decoder {
    private int[] currentMiniBlock;
    private int currentIndex;

    @Override
    public int nextInt() {
        if (currentIndex == currentMiniBlock.length) {
            loadNextMiniBlock();
        }

        return currentMiniBlock[currentIndex++];
    }

    @Override
    public int[] nextInts(int num) {
        int[] result = new int[num];
        for (int i = 0; i < num; i++) {
            result[i] = nextInt();
        }
        return result;
    }

    @Override
    public String supportedEncoding() {
        return Encoding.BITPACKED.name();
    }

    @Override
    public void readFromStream(InputStream inputStream) throws IOException {
        super.readFromStream(inputStream);

        loadNextMiniBlock();
    }

    private void loadNextMiniBlock() {
        currentIndex = 0;
        currentMiniBlock = new int[128];

        int numValuesToRead = readIntInternal();
        int bitWidth = readIntInternal();
        if (numValuesToRead > 0) {
            int strideSize = Bytes.sizeInBytes(8, bitWidth);
            Int32BytePacker packer = Packers.LITTLE_ENDIAN.packer32(bitWidth);
            for (int i = 0; i < numValuesToRead; i += 8) {
                packer.unpack8Values(getBuffer(), getPosition(), currentMiniBlock, i);
                incrementPosition(strideSize);
            }
        }
    }
}
