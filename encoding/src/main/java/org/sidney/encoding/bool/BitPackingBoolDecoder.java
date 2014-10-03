package org.sidney.encoding.bool;

import org.sidney.core.Bytes;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Unpacks bools created by {@link BitPackingBoolEncoder}
 */
public class BitPackingBoolDecoder implements BoolDecoder {
    private int currentLength;
    private int currentIndex;
    private DataInputStream dis;
    private byte currentByte;

    @Override
    public boolean nextBool() {
        if (currentIndex == 8) {
            currentIndex = 0;
            try {
                currentByte = dis.readByte();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return Bytes.bitAt(currentByte, currentIndex++) > 0;
    }

    @Override
    public boolean[] nextBools(int num) {
        boolean[] booleans = new boolean[num];
        for (int i = 0; i < num; i++) {
            booleans[i] = nextBool();
        }
        return booleans;
    }

    @Override
    public void readFromStream(InputStream inputStream) {
        dis = new DataInputStream(inputStream);
        init();
    }

    private void init() {
        try {
            currentLength = dis.readInt();
            currentByte = dis.readByte();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}