package org.sidney.encoding.int64;

import parquet.bytes.LittleEndianDataOutputStream;
import parquet.column.values.bitpacking.LongBytePacker;
import parquet.column.values.bitpacking.LongPacker;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Does simple bitpacking of longs in runs of 128, packing them on their own bitwidth
 */
public class BitPackingInt64Encoder implements Int64Encoder {
    public static final int BLOCK_SIZE = 128;
    private long[] buffer = new long[256];
    private int index = 0;

    @Override
    public void writeLong(long value) {
        ensureCapacity(index + 1);
        buffer[index++] = value;
    }

    @Override
    public void writeLongs(long[] values) {
        for(long value : values) {
            writeLong(value);
        }
    }

    @Override
    public void reset() {
        index = 0;
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        LittleEndianDataOutputStream dos = new LittleEndianDataOutputStream(outputStream);
        int count = index + 1;
        dos.writeInt(count);

        for(int i = 0; i < count; i += BLOCK_SIZE) {
            int bitwidth = getBitwidthForBlock(i, i + BLOCK_SIZE);
            //now pack them
            LongBytePacker packer = LongPacker.LITTLE_ENDIAN.newPacker(bitwidth);
            dos.writeInt(bitwidth);
            byte[] buf = new byte[bitwidth];
            for(int j = 0; j < BLOCK_SIZE; j += 8) {
                packer.pack8Values(buffer, i + j, buf, 0);
                dos.write(buf);
            }
        }
    }

    private int getBitwidthForBlock(int startIndex, int endIndex) {
        int maxWidth = Integer.MIN_VALUE;
        for(int i = startIndex; i < endIndex; i++) {
            maxWidth = Math.max(64 - Long.numberOfLeadingZeros(buffer[i]), maxWidth);
        }
        return maxWidth;
    }

    private void ensureCapacity(int size) {
        if(size >= buffer.length) {
            long[] newBuf = new long[buffer.length * 2];
            System.arraycopy(buffer, 0, newBuf, 0, index);
            buffer = newBuf;
            ensureCapacity(size);
        }
    }
}