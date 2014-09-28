package org.sidney.encoding.int32;

import parquet.bytes.LittleEndianDataInputStream;
import parquet.column.values.bitpacking.BytePacker;
import parquet.column.values.bitpacking.Packer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class DeltaInt32Decoder implements Int32Decoder {
    private static final int PACK_SIZE = 8;
    private int[] intBuffer = new int[2048];
    private int currentIndex = 0;
    private int currentReadIndex = 0;

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
        currentIndex = 0;
        currentReadIndex = 0;

        LittleEndianDataInputStream dis = new LittleEndianDataInputStream(inputStream);

        int totalValueCount = dis.readInt();
        int numMiniBlocks = dis.readInt();
        int firstValue = dis.readInt();

        int[] minDeltas = new int[numMiniBlocks];
        int[] bitwidths = new int[numMiniBlocks];

        intBuffer[currentReadIndex++] = firstValue;

        for (int i = 0; i < minDeltas.length; i++) {
            minDeltas[i] = dis.readInt();
        }

        for (int i = 0; i < minDeltas.length; i++) {
            bitwidths[i] = dis.readInt();
        }

        totalValueCount--;

        for (int i = 0; i < numMiniBlocks; i++) {
            totalValueCount = unpackMiniBlock(
                minDeltas[i], firstValue, bitwidths[i], totalValueCount, dis
            );
        }
    }

    //optimize this, its very slow
    private int unpackMiniBlock(
        int minDelta, int firstValue, int bitWidth, int numValuesLeft,
        LittleEndianDataInputStream dis
    ) throws IOException {
        int numToUnpack = Math.min(numValuesLeft, 128);
        int numCounter = numToUnpack;

        ensureCapacity(currentReadIndex + 128);

        BytePacker packer = Packer.LITTLE_ENDIAN.newBytePacker(bitWidth);

        byte[] buf = new byte[bitWidth];

        int miniBlockStartIndex = currentReadIndex;
        while (numToUnpack > 0) {
            dis.read(buf);
            packer.unpack8Values(buf, 0, intBuffer, miniBlockStartIndex);
            numToUnpack -= PACK_SIZE;
            miniBlockStartIndex += PACK_SIZE;
        }

        //go adjust min-deltas
        //TODO: try and see if I can use yeppp and SIMD ops to speed the min delta calculations up
        for (int i = 0; i < numCounter; i++) {
            int idx = i + currentReadIndex;
            intBuffer[idx] = intBuffer[idx] + minDelta;
        }

        for (int i = 0; i < numCounter; i++) {
            int idx = i + currentReadIndex;
            if (i == 0) {
                intBuffer[idx] += firstValue;
                continue;
            }

            intBuffer[idx] += intBuffer[idx - 1];
        }

        currentReadIndex += numCounter;
        return numValuesLeft - numCounter;
    }

    private void ensureCapacity(int size) {
        if(size >= intBuffer.length) {
            int[] buf = new int[size * 2];
            System.arraycopy(intBuffer, 0, buf, 0, intBuffer.length);
            intBuffer = buf;
            ensureCapacity(size);
        }
    }
}