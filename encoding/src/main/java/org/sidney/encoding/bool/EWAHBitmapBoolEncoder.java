package org.sidney.encoding.bool;

import com.googlecode.javaewah.EWAHCompressedBitmap;
import org.sidney.encoding.Encoding;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Encodes booleans into a compressed bitmap.
 */
public class EWAHBitmapBoolEncoder implements BoolEncoder {
    private EWAHCompressedBitmap currentBitmap;
    private int currentIndex = 0;

    public EWAHBitmapBoolEncoder() {
        currentBitmap = new EWAHCompressedBitmap();
    }

    @Override
    public void writeBool(boolean value) {
        if (value) {
            currentBitmap.set(currentIndex);
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
        currentBitmap = new EWAHCompressedBitmap();
        currentIndex = 0;
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        DataOutputStream dos = new DataOutputStream(outputStream);
        currentBitmap.serialize(dos);
        dos.flush();
    }

    @Override
    public String supportedEncoding() {
        return Encoding.EWAH.name();
    }
}