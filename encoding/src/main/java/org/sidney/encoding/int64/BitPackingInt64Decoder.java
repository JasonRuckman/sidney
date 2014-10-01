package org.sidney.encoding.int64;

import parquet.bytes.LittleEndianDataInputStream;
import parquet.column.values.bitpacking.LongBytePacker;
import parquet.column.values.bitpacking.LongPacker;

import java.io.IOException;
import java.io.InputStream;

public class BitPackingInt64Decoder implements Int64Decoder {
    private long[] buffer = new long[256];
    private int index = 0;

    @Override
    public long nextLong() {
        return buffer[index++];
    }

    @Override
    public long[] nextLongs(int num) {
        long[] results = new long[num];
        for(int i = 0; i < num; i++) {
            results[i] = nextLong();
        }
        return results;
    }

    @Override
    public void readFromStream(InputStream inputStream) throws IOException {
        LittleEndianDataInputStream dis = new LittleEndianDataInputStream(inputStream);
        int count = dis.readInt();
        ensureCapacity(count);
        int readIndex = 0;
        for(int i = 0; i < count; i += BitPackingInt64Encoder.BLOCK_SIZE) {
            int bitwidth = dis.readInt();
            byte[] buf = new byte[bitwidth];
            LongBytePacker packer = LongPacker.LITTLE_ENDIAN.newPacker(bitwidth);
            for(int j = 0; j < BitPackingInt64Encoder.BLOCK_SIZE; j += 8) {
                dis.read(buf);
                packer.unpack8Values(buf, 0, buffer, readIndex);
                readIndex += 8;
            }
        }
    }

    private void ensureCapacity(int size) {
        if(size >= buffer.length) {
            long[] newBuf = new long[buffer.length * 2];
            System.arraycopy(buffer, 0, newBuf, 0, buffer.length);
            buffer = newBuf;
            ensureCapacity(size);
        }
    }
}
