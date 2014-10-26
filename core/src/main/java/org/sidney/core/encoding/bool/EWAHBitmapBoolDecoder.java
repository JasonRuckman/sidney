package org.sidney.core.encoding.bool;

import com.googlecode.javaewah.EWAHCompressedBitmap;
import com.googlecode.javaewah.IntIterator;
import org.sidney.core.encoding.AbstractDecoder;
import org.sidney.core.encoding.Encoding;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class EWAHBitmapBoolDecoder extends AbstractDecoder implements BoolDecoder {
    private EWAHCompressedBitmap bitmap;
    private int index = 0;
    private int nextTrueBit;
    private IntIterator intIterator;

    @Override
    public boolean nextBool() {
        if (index == nextTrueBit) {
            if (intIterator.hasNext()) {
                nextTrueBit = intIterator.next();
            }
            index++;
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
        inputStream = inputStreamWrapIfNecessary(inputStream);
        index = 0;
        nextTrueBit = -1;
        bitmap = new EWAHCompressedBitmap();
        bitmap.deserialize(new DataInputStream(inputStream));
        intIterator = bitmap.intIterator();
        if (intIterator.hasNext()) {
            nextTrueBit = intIterator.next();
        }
    }

    @Override
    public String supportedEncoding() {
        return Encoding.EWAH.name();
    }
}