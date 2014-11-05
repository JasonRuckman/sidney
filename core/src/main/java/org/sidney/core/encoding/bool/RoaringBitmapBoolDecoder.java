package org.sidney.core.encoding.bool;

import org.roaringbitmap.IntIterator;
import org.roaringbitmap.RoaringBitmap;
import org.sidney.core.encoding.AbstractDecoder;
import org.sidney.core.encoding.Encoding;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class RoaringBitmapBoolDecoder extends AbstractDecoder implements BoolDecoder {
    private RoaringBitmap bitmap;
    private int index = 0;
    private int nextTrueBit;
    private IntIterator intIterator;

    @Override
    public boolean nextBool() {
        if (index == nextTrueBit) {
            index++;
            if(intIterator.hasNext()) {
                nextTrueBit = intIterator.next();
            }
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
    public void populateBufferFromStream(InputStream inputStream) throws IOException {
        inputStream = inputStreamWrapIfNecessary(inputStream);

        index = 0;
        nextTrueBit = -1;
        bitmap = new RoaringBitmap();
        bitmap.deserialize(new DataInputStream(inputStream));
        intIterator = bitmap.getIntIterator();
        if(intIterator.hasNext()) {
            nextTrueBit = intIterator.next();
        }
    }

    @Override
    public String supportedEncoding() {
        return Encoding.EWAH.name();
    }
}