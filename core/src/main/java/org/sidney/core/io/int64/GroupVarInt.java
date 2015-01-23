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
package org.sidney.core.io.int64;

import org.sidney.core.Longs;
import org.sidney.core.io.BaseDecoder;
import org.sidney.core.io.BaseEncoder;
import org.sidney.core.io.Encoding;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class GroupVarInt {
    public static class GroupVarInt64Decoder extends BaseDecoder implements Int64Decoder {
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

        @Override
        public String supportedEncoding() {
            return Encoding.GROUPVARINT.name();
        }

        private void loadNextBlock() {
            int offset = 0;
            byte prefixOne = readByte();
            byte prefixTwo = readByte();

            int[] lengths = new int[4];
            buffer = new long[4];
            lengths[0] = (prefixOne >>> 4) & 15;
            lengths[1] = prefixOne & 15;
            lengths[2] = (prefixTwo >>> 4) & 15;
            lengths[3] = prefixTwo & 15;

            byte[] buf = readBytesInternal(lengths[0] + lengths[1] + lengths[2] + lengths[3]);
            for (int i = 0; i < buffer.length; i++) {
                switch (lengths[i]) {
                    case 1: {
                        buffer[i] = Longs.zigzagDecode(readOnOneByte(buf, offset));
                        break;
                    }
                    case 2: {
                        buffer[i] = Longs.zigzagDecode(readOnTwoBytes(buf, offset));
                        break;
                    }
                    case 3: {
                        buffer[i] = Longs.zigzagDecode(readOnThreeBytes(buf, offset));
                        break;
                    }
                    case 4: {
                        buffer[i] = Longs.zigzagDecode(readOnFourBytes(buf, offset));
                        break;
                    }
                    case 5: {
                        buffer[i] = Longs.zigzagDecode(readOnFiveBytes(buf, offset));
                        break;
                    }
                    case 6: {
                        buffer[i] = Longs.zigzagDecode(readOnSixBytes(buf, offset));
                        break;
                    }
                    case 7: {
                        buffer[i] = Longs.zigzagDecode(readOnSevenBytes(buf, offset));
                        break;
                    }
                    case 8: {
                        buffer[i] = Longs.zigzagDecode(readOnEightBytes(buf, offset));
                        break;
                    }
                }
                offset += lengths[i];
            }
        }

        private long readOnOneByte(byte[] buf, int offset) {
            return ((long) buf[offset]) & 255L;
        }

        private long readOnTwoBytes(byte[] buf, int offset) {
            return (((buf[offset + 1] & 255L) << 8)
                    | ((buf[offset] & 255L)));
        }

        private long readOnThreeBytes(byte[] buf, int offset) {
            return (((buf[offset + 2] & 255L) << 16)
                    | ((buf[offset + 1] & 255L) << 8)
                    | (buf[offset] & 255L));
        }

        private long readOnFourBytes(byte[] buf, int offset) {
            return (((buf[offset + 3] & 255L) << 24)
                    | ((buf[offset + 2] & 255L) << 16)
                    | ((buf[offset + 1] & 255L) << 8)
                    | (buf[offset] & 255L));
        }

        private long readOnFiveBytes(byte[] buf, int offset) {
            return (((buf[offset + 4] & 255L) << 32)
                    | ((buf[offset + 3] & 255L) << 24)
                    | ((buf[offset + 2] & 255L) << 16)
                    | ((buf[offset + 1] & 255L) << 8)
                    | (buf[offset] & 255L));
        }

        private long readOnSixBytes(byte[] buf, int offset) {
            return (((buf[offset + 5] & 255L) << 40)
                    | ((buf[offset + 4] & 255L) << 32)
                    | ((buf[offset + 3] & 255L) << 24)
                    | ((buf[offset + 2] & 255L) << 16)
                    | ((buf[offset + 1] & 255L) << 8)
                    | (buf[offset] & 255L));
        }

        private long readOnSevenBytes(byte[] buf, int offset) {
            return (((buf[offset + 6] & 255L) << 48)
                    | ((buf[offset + 5] & 255L) << 40)
                    | ((buf[offset + 4] & 255L) << 32)
                    | ((buf[offset + 3] & 255L) << 24)
                    | ((buf[offset + 2] & 255L) << 16)
                    | ((buf[offset + 1] & 255L) << 8)
                    | (buf[offset] & 255L));
        }

        private long readOnEightBytes(byte[] buf, int offset) {
            return (((buf[offset + 7] & 255L) << 56)
                    | ((buf[offset + 6] & 255L) << 48)
                    | ((buf[offset + 5] & 255L) << 40)
                    | ((buf[offset + 4] & 255L) << 32)
                    | ((buf[offset + 3] & 255L) << 24)
                    | ((buf[offset + 2] & 255L) << 16)
                    | ((buf[offset + 1] & 255L) << 8)
                    | (buf[offset] & 255L));
        }

        @Override
        public void readFromStream(InputStream inputStream) throws IOException {
            super.readFromStream(inputStream);
            currentIndex = 4;
        }
    }

    /**
     * Zigzag encodes integers, then variable length encodes them
     */
    public static class GroupVarInt64Encoder extends BaseEncoder implements Int64Encoder {
        private final long[] block = new long[4];
        private int currentIndex = 0;
        private int num = 0;

        @Override
        public void writeLong(long value) {
            block[currentIndex++] = value;
            num++;
            if (currentIndex == 4) {
                flushBlock();
                num = 0;
            }
        }

        @Override
        public void writeLongs(long[] values) {
            for (long value : values) {
                writeLong(value);
            }
        }

        @Override
        public void writeToStream(OutputStream outputStream) throws IOException {
            if (num > 0) {
                flushBlock();
            }
            super.writeToStream(outputStream);
        }

        @Override
        public String supportedEncoding() {
            return Encoding.GROUPVARINT.name();
        }

        private void writeOnOneByte(long value, byte[] buf, int offset) {
            buf[offset] = (byte) value;
        }

        private void writeOnTwoBytes(long value, byte[] buf, int offset) {
            buf[offset] = (byte) value;
            buf[offset + 1] = (byte) (value >>> 8);
        }

        private void writeOnThreeBytes(long value, byte[] buf, int offset) {
            buf[offset] = (byte) value;
            buf[offset + 1] = (byte) (value >>> 8);
            buf[offset + 2] = (byte) (value >>> 16);
        }

        private void writeOnFourBytes(long value, byte[] buf, int offset) {
            buf[offset] = (byte) value;
            buf[offset + 1] = (byte) (value >>> 8);
            buf[offset + 2] = (byte) (value >>> 16);
            buf[offset + 3] = (byte) (value >>> 24);
        }

        private void writeOnFiveBytes(long value, byte[] buf, int offset) {
            buf[offset] = (byte) value;
            buf[offset + 1] = (byte) (value >>> 8);
            buf[offset + 2] = (byte) (value >>> 16);
            buf[offset + 3] = (byte) (value >>> 24);
            buf[offset + 4] = (byte) (value >>> 32);
        }

        private void writeOnSixBytes(long value, byte[] buf, int offset) {
            buf[offset] = (byte) value;
            buf[offset + 1] = (byte) (value >>> 8);
            buf[offset + 2] = (byte) (value >>> 16);
            buf[offset + 3] = (byte) (value >>> 24);
            buf[offset + 4] = (byte) (value >>> 32);
            buf[offset + 5] = (byte) (value >>> 40);
        }

        private void writeOnSevenBytes(long value, byte[] buf, int offset) {
            buf[offset] = (byte) value;
            buf[offset + 1] = (byte) (value >>> 8);
            buf[offset + 2] = (byte) (value >>> 16);
            buf[offset + 3] = (byte) (value >>> 24);
            buf[offset + 4] = (byte) (value >>> 32);
            buf[offset + 5] = (byte) (value >>> 40);
            buf[offset + 6] = (byte) (value >>> 48);
        }

        private void writeOnEightBytes(long value, byte[] buf, int offset) {
            buf[offset] = (byte) value;
            buf[offset + 1] = (byte) (value >>> 8);
            buf[offset + 2] = (byte) (value >>> 16);
            buf[offset + 3] = (byte) (value >>> 24);
            buf[offset + 4] = (byte) (value >>> 32);
            buf[offset + 5] = (byte) (value >>> 40);
            buf[offset + 6] = (byte) (value >>> 48);
            buf[offset + 7] = (byte) (value >>> 56);
        }

        private void flushBlock() {
            //zigzag encode and write prefix
            byte[] buffer = new byte[34];
            int offset = 2;
            for (int i = 0; i < block.length; i++) {
                long value = Longs.zigzagEncode(block[i]);
                int bytes = (int) Math.ceil((64D - Long.numberOfLeadingZeros(value)) / 8D);
                bytes = (bytes == 0) ? 1 : bytes;
                switch (i) {
                    case 0: {
                        buffer[0] = (byte) (bytes & 15);
                        break;
                    }
                    case 1: {
                        buffer[0] = (byte) ((buffer[0] << 4) | (bytes & 15));
                        break;
                    }
                    case 2: {
                        buffer[1] |= (byte) (bytes & 15);
                        break;
                    }
                    case 3: {
                        buffer[1] = (byte) ((buffer[1] << 4) | (bytes & 15));
                        break;
                    }
                }

                switch (bytes) {
                    case 1: {
                        writeOnOneByte(value, buffer, offset);
                        offset += 1;
                        break;
                    }
                    case 2: {
                        writeOnTwoBytes(value, buffer, offset);
                        offset += 2;
                        break;
                    }
                    case 3: {
                        writeOnThreeBytes(value, buffer, offset);
                        offset += 3;
                        break;
                    }
                    case 4: {
                        writeOnFourBytes(value, buffer, offset);
                        offset += 4;
                        break;
                    }
                    case 5: {
                        writeOnFiveBytes(value, buffer, offset);
                        offset += 5;
                        break;
                    }
                    case 6: {
                        writeOnSixBytes(value, buffer, offset);
                        offset += 6;
                        break;
                    }
                    case 7: {
                        writeOnSevenBytes(value, buffer, offset);
                        offset += 7;
                        break;
                    }
                    case 8: {
                        writeOnEightBytes(value, buffer, offset);
                        offset += 8;
                        break;
                    }
                }
                block[i] = value;
            }
            currentIndex = 0;
            writeBytesInternal(buffer, 0, offset);
        }
    }
}
