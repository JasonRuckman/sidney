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

package com.github.jasonruckman.sidney.core.io.int16;

import com.github.jasonruckman.sidney.core.bitpacking.Int32BytePacker;
import com.github.jasonruckman.sidney.core.bitpacking.Packers;
import com.github.jasonruckman.sidney.core.io.DirectDecoder;
import com.github.jasonruckman.sidney.core.io.DirectEncoder;
import com.github.jasonruckman.sidney.core.util.Bytes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.github.jasonruckman.sidney.core.util.Bytes.readIntFromStream;
import static com.github.jasonruckman.sidney.core.util.Bytes.writeIntToStream;

public class DeltaBitPacking {
  public static class DeltaBitpackingShortEncoder extends DirectEncoder implements ShortEncoder {
    public static final int DEFAULT_BLOCK_SIZE = 128;

    private int firstValue = 0;
    private int prevValue = 0;
    private int totalValueCount = 0;
    private int[] currentMiniBlock = new int[DEFAULT_BLOCK_SIZE];
    private int currentIndex = 0;
    private int currentMinDelta = Integer.MAX_VALUE;
    private int position = 0;
    private byte[] buffer = new byte[64];

    @Override
    public void writeShort(short value) {
      if (++totalValueCount == 1) {
        firstValue = value;
        prevValue = value;
        return;
      }
      int delta = value - prevValue;
      prevValue = value;
      currentMiniBlock[currentIndex++] = delta;
      currentMinDelta = Math.min(delta, currentMinDelta);
      if (currentIndex == DEFAULT_BLOCK_SIZE) {
        flushMiniBlock();
      }
    }

    @Override
    public void writeShorts(short[] values) {
      for (short v : values) {
        writeShort(v);
      }
    }

    @Override
    public void reset() {
      currentIndex = 0;
      currentMinDelta = 0;
      prevValue = 0;
      totalValueCount = 0;
      firstValue = 0;
      position = 0;
    }

    @Override
    public void flush(OutputStream outputStream) {
      flushMiniBlock();
      try {
        writeIntToStream(totalValueCount, outputStream);
        writeIntToStream(firstValue, outputStream);
        if (totalValueCount <= 1) {
          return;
        }
        writeIntToStream(position, outputStream);
        outputStream.write(buffer, 0, position);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
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
      ensureCapacity(12);

      Bytes.writeInt(currentMaxBitwidth, buffer, getPositionAndIncrement(4));
      Bytes.writeInt(currentMinDelta, buffer, getPositionAndIncrement(4));
      Bytes.writeInt(currentIndex, buffer, getPositionAndIncrement(4));

      int strideSize = Bytes.sizeInBytes(8, currentMaxBitwidth);
      Int32BytePacker packer = Packers.LITTLE_ENDIAN.packer32(currentMaxBitwidth);
      ensureCapacity(Bytes.sizeInBytes(numToWrite, currentMaxBitwidth));

      for (int i = 0; i < numToWrite; i += 8) {
        packer.pack8Values(currentMiniBlock, i, buffer, getPositionAndIncrement(strideSize));
      }

      currentIndex = 0;
      currentMinDelta = Integer.MAX_VALUE;
      prevValue = firstValue;
    }

    protected void ensureCapacity(int bytes) {
      if (position + bytes >= buffer.length) {
        int newSize = Math.max(buffer.length * 2, (position + bytes) * 2);
        byte[] newBuffer = new byte[newSize];
        System.arraycopy(buffer, 0, newBuffer, 0, position);
        buffer = newBuffer;
      }
    }

    private int getPositionAndIncrement(int size) {
      int pos = position;
      position += size;
      return pos;
    }
  }

  public static class DeltaBitpackingShortDecoder extends DirectDecoder implements ShortDecoder {
    private int[] intBuffer = new int[128];
    private int currentIndex = 0;
    private int totalValueCount;
    private int firstValue;
    private boolean isFirstValue = true;
    private int position = 0;
    private byte[] buffer = new byte[64];

    @Override
    public short nextShort() {
      if (isFirstValue) {
        isFirstValue = false;
        return (short) firstValue;
      }

      if (currentIndex == intBuffer.length) {
        loadNextMiniBlock();
      }

      return (short) intBuffer[currentIndex++];
    }

    @Override
    public short[] nextShorts(int num) {
      short[] ints = new short[num];
      for (int i = 0; i < num; i++) {
        ints[i] = nextShort();
      }
      return ints;
    }

    @Override
    public void load(InputStream inputStream) {
      try {
        currentIndex = 0;
        isFirstValue = true;
        position = 0;

        totalValueCount = readIntFromStream(inputStream);
        firstValue = readIntFromStream(inputStream);

        if (totalValueCount <= 1) {
          return;
        }
        int size = readIntFromStream(inputStream);
        resizeIfNecessary(size);
        Bytes.readFully(buffer, inputStream, size);
        loadNextMiniBlock();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    public int getAndIncrementPosition(int size) {
      int pos = position;
      position += size;
      return pos;
    }

    private void loadNextMiniBlock() {
      currentIndex = 0;
      int bitWidth = Bytes.readInt(buffer, getAndIncrementPosition(4));
      int minDelta = Bytes.readInt(buffer, getAndIncrementPosition(4));
      int numValuesToRead = Bytes.readInt(buffer, getAndIncrementPosition(4));
      totalValueCount -= numValuesToRead;
      int strideSize = Bytes.sizeInBytes(8, bitWidth);
      Int32BytePacker packer = Packers.LITTLE_ENDIAN.packer32(bitWidth);

      for (int i = 0; i < numValuesToRead; i += 8) {
        packer.unpack8Values(buffer, getAndIncrementPosition(strideSize), intBuffer, i);
      }

      for (int i = 0; i < numValuesToRead; i++) {
        intBuffer[i] += ((i == 0) ? firstValue : intBuffer[i - 1]) + minDelta;
      }
    }

    protected void resizeIfNecessary(int size) {
      if (buffer.length < size) {
        byte[] buf = new byte[size];
        System.arraycopy(buffer, 0, buf, 0, buffer.length);
        buffer = buf;
      }
    }
  }
}