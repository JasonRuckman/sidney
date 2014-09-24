package org.sidney.encoding.bool;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.KryoDataInput;
import com.googlecode.javaewah.EWAHCompressedBitmap;
import com.googlecode.javaewah.IntIterator;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class EWAHBoolDecoder implements BoolDecoder {
    private EWAHCompressedBitmap bitmap;
    private int index = 0;
    private int nextTrueBit;
    private IntIterator intIterator;

    @Override
    public boolean nextBool() {
        if (index == nextTrueBit) {
            index++;
            nextTrueBit = intIterator.next();
            return true;
        }

        index++;
        return false;
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
    public void readFromStream(InputStream inputStream) throws IOException {
        Input input = new Input(inputStream);
        readBitmap(input);
    }

    private void readBitmap(Input input) throws IOException {
        bitmap = new EWAHCompressedBitmap();
        bitmap.deserialize(
            new KryoDataInput(
                input
            )
        );
        intIterator = bitmap.intIterator();
        nextTrueBit = intIterator.next();
    }
}