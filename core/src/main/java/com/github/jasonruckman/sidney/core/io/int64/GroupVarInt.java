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
package com.github.jasonruckman.sidney.core.io.int64;

import com.github.jasonruckman.sidney.core.io.IndirectDecoder;
import com.github.jasonruckman.sidney.core.io.IndirectEncoder;
import com.github.jasonruckman.sidney.core.io.input.Input;
import com.github.jasonruckman.sidney.core.io.output.Output;
import com.github.jasonruckman.sidney.core.util.Bytes;
import com.github.jasonruckman.sidney.core.util.Longs;

public class GroupVarInt {
  public static class GroupVarInt64Decoder extends IndirectDecoder implements Int64Decoder {
    private long[] buffer = new long[4];
    private int currentIndex = 0;

    @Override
    public long nextLong() {
      if (currentIndex == buffer.length) {
        currentIndex = 0;
        loadNextBlock();
      }
      return buffer[currentIndex++];
    }

    @Override
    public long[] nextLongs(int num) {
      long[] longs = new long[num];
      for (int i = 0; i < num; i++) {
        longs[i] = nextLong();
      }
      return longs;
    }

    private void loadNextBlock() {
      buffer = new long[4];

      byte prefixOne = input.readByte();
      byte prefixTwo = input.readByte();

      int lengthOne = prefixOne & 15;
      int lengthTwo = (prefixOne >>> 4) & 15;
      int lengthThree = prefixTwo & 15;
      int lengthFour = (prefixTwo >>> 4) & 15;

      byte[] buf = input.readBytes(lengthOne + lengthTwo + lengthThree + lengthFour);

      buffer[0] = readLongOfLength(lengthOne, buf, 0);
      buffer[1] = readLongOfLength(lengthTwo, buf, lengthOne);
      buffer[2] = readLongOfLength(lengthThree, buf, lengthOne + lengthTwo);
      buffer[3] = readLongOfLength(lengthFour, buf, lengthOne + lengthTwo + lengthThree);
    }

    private long readLongOfLength(int length, byte[] buffer, int offset) {
      switch (length) {
        case 0: {
          return 0L;
        }
        case 1: {
          return Longs.zigzagDecode(Bytes.readLongOnOneByte(buffer, offset));
        }
        case 2: {
          return Longs.zigzagDecode(Bytes.readLongOnTwoBytes(buffer, offset));
        }
        case 3: {
          return Longs.zigzagDecode(Bytes.readLongOnThreeBytes(buffer, offset));
        }
        case 4: {
          return Longs.zigzagDecode(Bytes.readLongOnFourBytes(buffer, offset));
        }
        case 5: {
          return Longs.zigzagDecode(Bytes.readLongOnFiveBytes(buffer, offset));
        }
        case 6: {
          return Longs.zigzagDecode(Bytes.readLongOnSixBytes(buffer, offset));
        }
        case 7: {
          return Longs.zigzagDecode(Bytes.readLongOnSevenBytes(buffer, offset));
        }
        case 8: {
          return Longs.zigzagDecode(Bytes.readLong(buffer, offset));
        }
        default: {
          throw new IllegalStateException("Shouldn't get here");
        }
      }
    }

    @Override
    public void load() {
      currentIndex = 4;
    }
  }

  /**
   * Zigzag encodes integers, then variable length encodes them
   */
  public static class GroupVarInt64Encoder extends IndirectEncoder implements Int64Encoder {
    private byte[] buf = new byte[34];
    private int count = 0;
    private int offset = 2;

    @Override
    public void writeLong(long value) {
      ++count;
      write(value);
      if (count == 4) {
        flushBlock(output);
      }
    }

    @Override
    public void writeLongs(long[] values) {
      for (long value : values) {
        writeLong(value);
      }
    }

    private void write(long value) {
      long zigzag = Longs.zigzagEncode(value);
      int bytes = (int) Math.ceil((64D - Long.numberOfLeadingZeros(zigzag)) / 8D);
      switch (count) {
        case 1: {
          buf[0] = (byte) (bytes & 15);
          break;
        }
        case 2: {
          buf[0] = (byte) (buf[0] | (bytes & 15) << 4);
          break;
        }
        case 3: {
          buf[1] |= (byte) (bytes & 15);
          break;
        }
        case 4: {
          buf[1] = (byte) (buf[1] | (bytes & 15) << 4);
          break;
        }
      }

      switch (bytes) {
        case 1: {
          Bytes.writeLongOnOneByte(zigzag, buf, offset);
          offset += 1;
          break;
        }
        case 2: {
          Bytes.writeLongOnTwoBytes(zigzag, buf, offset);
          offset += 2;
          break;
        }
        case 3: {
          Bytes.writeLongOnThreeBytes(zigzag, buf, offset);
          offset += 3;
          break;
        }
        case 4: {
          Bytes.writeLongOnFourBytes(zigzag, buf, offset);
          offset += 4;
          break;
        }
        case 5: {
          Bytes.writeLongOnFiveBytes(zigzag, buf, offset);
          offset += 5;
          break;
        }
        case 6: {
          Bytes.writeLongOnSixBytes(zigzag, buf, offset);
          offset += 6;
          break;
        }
        case 7: {
          Bytes.writeLongOnSevenBytes(zigzag, buf, offset);
          offset += 7;
          break;
        }
        case 8: {
          Bytes.writeLong(zigzag, buf, offset);
          offset += 8;
          break;
        }
      }
    }

    private void flushBlock(Output output) {
      output.writeBytes(buf, 0, offset);
      reset();
    }

    @Override
    public void reset() {
      count = 0;
      offset = 2;
      buf = new byte[34];
    }

    @Override
    public void flush() {
      flushBlock(output);
    }
  }
}