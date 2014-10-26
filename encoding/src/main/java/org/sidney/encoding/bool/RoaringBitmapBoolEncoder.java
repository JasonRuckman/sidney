package org.sidney.encoding.bool;

import org.roaringbitmap.RoaringBitmap;
import org.sidney.encoding.Encoding;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Encodes booleans into a compressed bitmap.
 */
public class RoaringBitmapBoolEncoder implements BoolEncoder {
    private RoaringBitmap currentBitmap;
    private int currentIndex = 0;

    public RoaringBitmapBoolEncoder() {
        currentBitmap = new RoaringBitmap();
    }

    @Override
    public void writeBool(boolean value) {
        if (value) {
            currentBitmap.add(currentIndex);
        }
        currentIndex++;
    }

    @Override
    public void writeBools(boolean[] values) {
        for (boolean value : values) {
            writeBool(value);
        }
    }

    @Override
    public void reset() {
        currentBitmap = new RoaringBitmap();
        currentIndex = 0;
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        DataOutputStream dos = new DataOutputStream(outputStream);
        currentBitmap.trim();
        currentBitmap.serialize(dos);
        dos.flush();
    }

    @Override
    public String supportedEncoding() {
        return Encoding.EWAH.name();
    }
}