package org.sidney.encoding.int32;

import org.sidney.core.Bytes;

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
        byte[] buf = new byte[4];
        inputStream.read(buf);
        int totalValueCount = Bytes.bytesToInt(buf, 0);
        intBuffer = new int[totalValueCount];
        inputStream.read(buf);
        int numMiniBlocks = Bytes.bytesToInt(buf, 0);
    }

    private void unpack(int numMiniBlocks, InputStream inputStream) {

    }
}