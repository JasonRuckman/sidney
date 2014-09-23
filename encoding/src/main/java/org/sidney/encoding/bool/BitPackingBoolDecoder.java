package org.sidney.encoding.bool;

import com.esotericsoftware.kryo.io.Input;
import org.sidney.core.Bytes;

import java.io.InputStream;

/**
 * Unpacks bools created by {@link BitPackingBoolEncoder}
 */
public class BitPackingBoolDecoder implements BoolDecoder {
    private int currentLength;
    private int currentIndex;
    private Input currentInput;
    private byte currentByte;

    @Override
    public boolean nextBool() {
        if (currentIndex == 8) {
            currentIndex = 0;
            currentByte = currentInput.readByte();
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
    public void readFromBuffer(byte[] buffer) {
        currentInput = new Input(buffer);
        init();
    }

    @Override
    public void readFromStream(InputStream inputStream) {
        currentInput = new Input(inputStream);
        init();
    }

    private void init() {
        currentLength = currentInput.readInt();
        currentByte = currentInput.readByte();
    }
}