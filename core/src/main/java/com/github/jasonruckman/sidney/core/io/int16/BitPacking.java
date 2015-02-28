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
import java.nio.BufferUnderflowException;

public class BitPacking {
  public static class BitPackingShortDecoder extends DirectDecoder implements ShortDecoder {
    private int[] currentMiniBlock = new int[128];
    private int currentIndex;
    private int position;
    private byte[] buffer = new byte[64];

    public int getAndIncrementPosition(int size) {
      int pos = position;
      position += size;
      return pos;
    }

    @Override
    public short nextShort() {
      if (currentIndex == currentMiniBlock.length) {
        loadNextMiniBlock();
      }

      return (short) currentMiniBlock[currentIndex++];
    }

    @Override
    public short[] nextShorts(int num) {
      short[] result = new short[num];
      for (int i = 0; i < num; i++) {
        result[i] = nextShort();
      }
      return result;
    }

    private void loadNextMiniBlock() {
      currentIndex = 0;
      require(8);
      int numValuesToRead = Bytes.readInt(buffer, getAndIncrementPosition(4));
      int bitWidth = Bytes.readInt(buffer, getAndIncrementPosition(4));
      if (numValuesToRead > 0) {
        int strideSize = Bytes.sizeInBytes(8, bitWidth);

        Int32BytePacker packer = Packers.LITTLE_ENDIAN.packer32(bitWidth);
        for (int i = 0; i < numValuesToRead; i += 8) {
          packer.unpack8Values(buffer, getAndIncrementPosition(strideSize), currentMiniBlock, i);
        }
      }
    }

    @Override
    public void load(InputStream inputStream) {
      position = 0;
      currentIndex = 0;

      try {
        int size = Bytes.readIntFromStream(inputStream);
        resizeIfNecessary(size);
        Bytes.readFully(buffer, inputStream, size);
        loadNextMiniBlock();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    protected void resizeIfNecessary(int size) {
      if (buffer.length < size) {
        byte[] buf = new byte[size];
        System.arraycopy(buffer, 0, buf, 0, buffer.length);
        buffer = buf;
      }
    }

    protected void require(int size) {
      if (position + size > buffer.length) {
        throw new BufferUnderflowException();
      }
    }
  }

  public static class BitPackingShortEncoder extends DirectEncoder implements ShortEncoder {
    public static final int DEFAULT_BLOCK_SIZE = 128;
    private final int miniBlockSize;
    private int[] currentMiniBlock;
    private int currentIndex = 0;
    private int currentMaxBitwidth = 0;
    private int position;
    private byte[] buffer = new byte[64];

    public BitPackingShortEncoder() {
      this(DEFAULT_BLOCK_SIZE);
    }

    public BitPackingShortEncoder(int miniBlockSize) {
      this.miniBlockSize = miniBlockSize;
      this.currentMiniBlock = new int[miniBlockSize];
    }

    @Override
    public void writeShort(short value) {
      currentMiniBlock[currentIndex++] = value;
      currentMaxBitwidth = Math.max(currentMaxBitwidth, 32 - Integer.numberOfLeadingZeros(value));

      if (currentIndex == miniBlockSize) {
        flushMiniBlock();
      }
    }

    @Override
    public void writeShorts(short[] values) {
      for (short value : values) {
        writeShort(value);
      }
    }

    private void flushMiniBlock() {
      int numToWrite = Math.max(currentIndex, 8);

      ensureCapacity(8);

      Bytes.writeInt(currentIndex, buffer, getPositionAndIncrement(4));
      Bytes.writeInt(currentMaxBitwidth, buffer, getPositionAndIncrement(4));

      if (currentIndex > 0) {
        int strideSize = Bytes.sizeInBytes(8, currentMaxBitwidth);
        //pad
        int bytesRequired = ((Bytes.sizeInBytes(2, currentMaxBitwidth) * numToWrite));

        ensureCapacity(bytesRequired);
        Int32BytePacker packer = Packers.LITTLE_ENDIAN.packer32(currentMaxBitwidth);

        for (int i = 0; i < numToWrite; i += 8) {
          packer.pack8Values(currentMiniBlock, i, buffer, getPositionAndIncrement(strideSize));
        }
      }
      currentMaxBitwidth = 0;
      currentIndex = 0;
    }

    @Override
    public void reset() {
      currentMaxBitwidth = 0;
      currentIndex = 0;
      position = 0;
    }

    @Override
    public void flush(OutputStream outputStream) {
      flushMiniBlock();

      try {
        Bytes.writeIntToStream(position, outputStream);
        outputStream.write(buffer, 0, position);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
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
}