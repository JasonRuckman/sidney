package org.sidney.encoding.int64;

import org.sidney.core.unsafe.UnsafeLongs;
import org.sidney.encoding.Encoding;
import parquet.bytes.LittleEndianDataInputStream;

import java.io.IOException;
import java.io.InputStream;

public class DeltaInt64Decoder implements Int64Decoder {
    private long[] intBuffer = new long[2048];
    private int currentIndex = 0;
    private int currentReadIndex = 0;

    @Override
    public long nextLong() {
        return intBuffer[currentIndex++];
    }

    @Override
    public long[] nextLongs(int num) {
        long[] ints = new long[num];
        for (int i = 0; i < num; i++) {
            ints[i] = nextLong();
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
        long firstValue = dis.readLong();

        long[] minDeltas = new long[numMiniBlocks];

        intBuffer[currentReadIndex++] = firstValue;

        for (int i = 0; i < minDeltas.length; i++) {
            minDeltas[i] = dis.readLong();
        }

        totalValueCount--;

        for (int i = 0; i < numMiniBlocks; i++) {
            totalValueCount = unpackMiniBlock(
                minDeltas[i], totalValueCount, firstValue, dis
            );
        }
    }

    @Override
    public String supportedEncoding() {
        return Encoding.DELTA.name();
    }

    private int unpackMiniBlock(
        long minDelta, int numValuesLeft, long firstValue,
        LittleEndianDataInputStream dis
    ) throws IOException {
        int numCounter = 128;

        ensureCapacity(currentReadIndex + numCounter);

        byte[] buf = new byte[numCounter * 8];
        dis.read(buf);
        UnsafeLongs.copyBytesToLongs(buf, 0, intBuffer, currentReadIndex, numCounter * 8);

        //go adjust min-deltas
        for (int i = 0; i < numCounter; i++) {
            int idx = i + currentReadIndex;
            intBuffer[idx] = intBuffer[idx] + minDelta + ((i == 0) ? firstValue :  intBuffer[idx - 1]);
        }

        currentReadIndex += numCounter;
        return numValuesLeft - numCounter;
    }

    private void ensureCapacity(int size) {
        if (size >= intBuffer.length) {
            long[] buf = new long[size * 2];
            System.arraycopy(intBuffer, 0, buf, 0, intBuffer.length);
            intBuffer = buf;
            ensureCapacity(size);
        }
    }
}
