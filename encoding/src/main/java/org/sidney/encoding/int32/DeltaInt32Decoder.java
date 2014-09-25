package org.sidney.encoding.int32;

import parquet.bytes.LittleEndianDataInputStream;
import parquet.column.values.bitpacking.BytePacker;
import parquet.column.values.bitpacking.Packer;

import java.io.IOException;
import java.io.InputStream;

public class DeltaInt32Decoder implements Int32Decoder {
    private int[] intBuffer;
    private int currentIndex = 0;

    @Override
    public int nextInt() {
        return intBuffer[currentIndex++];
    }

    @Override
    public int[] nextInts(int num) {
        int[] ints = new int[num];
        for (int i = 0; i < num; i++) {
            ints[i] = nextInt();
        }
        return ints;
    }

    @Override
    public void readFromStream(InputStream inputStream) throws IOException {
        LittleEndianDataInputStream dis = new LittleEndianDataInputStream(inputStream);
        int totalValueCount = dis.readInt();
        intBuffer = new int[totalValueCount];
        int numMiniBlocks = dis.readInt();
        unpack(numMiniBlocks, dis);
    }

    private void unpack(int numMiniBlocks, LittleEndianDataInputStream dis) throws IOException {
        byte[] buf = new byte[32 * 128];
        int[] tmp = new int[128];
        int index = 0;

        //read firstvalue|numvalues|mindelta|bitwidth
        for (int i = 0; i < numMiniBlocks; i++) {
            int firstValue = dis.readInt();
            int numValues = dis.readInt();
            int minDelta = dis.readInt();
            int bitwidth = dis.readInt();
        }
    }
}