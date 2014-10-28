package org.sidney.core.encoding.int64;

import org.sidney.core.Longs;
import org.sidney.core.encoding.AbstractEncoder;
import org.sidney.core.encoding.Encoding;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Zigzag encodes integers, then variable length encodes them
 */
public class GroupVarInt64Encoder extends AbstractEncoder implements Int64Encoder {
    private int currentIndex = 0;
    private final long[] block = new long[4];

    @Override
    public void writeLong(long value) {
        //each int, var encode, write bits into prefix, on flush write the prefix and bump the position past the last byte
        block[currentIndex++] = value;
        if (currentIndex == 4) {
            flushBlock();
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
        flushBlock();
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
            int bytes = (64 - Long.numberOfLeadingZeros(value)) / 8;
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