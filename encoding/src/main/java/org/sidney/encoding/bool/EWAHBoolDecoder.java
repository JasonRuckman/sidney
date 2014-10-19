package org.sidney.encoding.bool;

import com.googlecode.javaewah.EWAHCompressedBitmap;
import com.googlecode.javaewah.IntIterator;
import org.sidney.encoding.AbstractDecoder;
import org.sidney.encoding.Encoding;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class EWAHBoolDecoder extends AbstractDecoder implements BoolDecoder {
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
        inputStream = dataInputStreamWrapIfNecessary(inputStream);

        index = 0;
        bitmap = new EWAHCompressedBitmap();
        bitmap.deserialize(new DataInputStream(inputStream));
        intIterator = bitmap.intIterator();
        nextTrueBit = intIterator.next();
    }

    @Override
    public String supportedEncoding() {
        return Encoding.EWAH.name();
    }
}