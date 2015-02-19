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

import com.github.jasonruckman.sidney.core.Bytes;
import com.github.jasonruckman.sidney.core.bitpacking.Int32BytePacker;
import com.github.jasonruckman.sidney.core.bitpacking.Packers;
import com.github.jasonruckman.sidney.core.io.BaseDecoder;
import com.github.jasonruckman.sidney.core.io.BaseEncoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BitPacking {
  public static class BitPackingInt32Decoder extends BaseDecoder implements Int32Decoder {
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
    public void readFromStream(InputStream inputStream) throws IOException {
      super.readFromStream(inputStream);

      loadNextMiniBlock();
    }

    private void loadNextMiniBlock() {
      currentIndex = 0;
      currentMiniBlock = new int[128];

      int numValuesToRead = readIntFromBuffer();
      int bitWidth = readIntFromBuffer();
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

  public static class BitPackingInt32Encoder extends BaseEncoder implements Int32Encoder {
    public static final int DEFAULT_BLOCK_SIZE = 128;
    private final int miniBlockSize;
    private int[] currentMiniBlock;
    private int currentIndex = 0;
    private int currentMaxBitwidth = 0;

    public BitPackingInt32Encoder() {
      this(DEFAULT_BLOCK_SIZE);
    }

    public BitPackingInt32Encoder(int miniBlockSize) {
      this.miniBlockSize = miniBlockSize;
      this.currentMiniBlock = new int[miniBlockSize];
    }

    @Override
    public void writeInt(int value) {
      currentMiniBlock[currentIndex++] = value;
      currentMaxBitwidth = Math.max(currentMaxBitwidth, 32 - Integer.numberOfLeadingZeros(value));

      if (currentIndex == miniBlockSize) {
        flushMiniBlock();
      }
    }

    @Override
    public void writeInts(int[] values) {
      for (int value : values) {
        writeInt(value);
      }
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
      flushMiniBlock();

      super.writeToStream(outputStream);
    }

    private void flushMiniBlock() {
      int numToWrite = Math.max(currentIndex, 8);
      writeIntToBuffer(currentIndex);
      writeIntToBuffer(currentMaxBitwidth);
      if (currentIndex > 0) {
        int strideSize = Bytes.sizeInBytes(8, currentMaxBitwidth);
        Int32BytePacker packer = Packers.LITTLE_ENDIAN.packer32(currentMaxBitwidth);
        ensureCapacity(Bytes.sizeInBytes(numToWrite, currentMaxBitwidth));
        for (int i = 0; i < numToWrite; i += 8) {
          packer.pack8Values(currentMiniBlock, i, getBuffer(), getPosition());
          incrementPosition(strideSize);
        }
      }
      currentMiniBlock = new int[miniBlockSize];
      currentMaxBitwidth = 0;
      currentIndex = 0;
    }
  }
}
