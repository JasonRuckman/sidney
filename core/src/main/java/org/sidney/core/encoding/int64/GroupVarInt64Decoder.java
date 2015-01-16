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
package org.sidney.core.encoding.int64;

import org.sidney.core.Longs;
import org.sidney.core.encoding.AbstractDecoder;
import org.sidney.core.encoding.Encoding;

import java.io.IOException;
import java.io.InputStream;

public class GroupVarInt64Decoder extends AbstractDecoder implements Int64Decoder {
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
    public void populateBufferFromStream(InputStream inputStream) throws IOException {
        super.populateBufferFromStream(inputStream);
        currentIndex = 4;
    }
}