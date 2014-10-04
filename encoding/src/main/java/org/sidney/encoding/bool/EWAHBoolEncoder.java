package org.sidney.encoding.bool;

import com.esotericsoftware.kryo.io.KryoDataOutput;
import com.esotericsoftware.kryo.io.Output;
import com.googlecode.javaewah.EWAHCompressedBitmap;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Encodes booleans into a compressed bitmap.
 */
public class EWAHBoolEncoder implements BoolEncoder {
    private EWAHCompressedBitmap currentBitmap;
    private int currentIndex = 0;

    public EWAHBoolEncoder() {
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
        this.currentBitmap = new EWAHCompressedBitmap();
        this.currentIndex = 0;
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        this.currentBitmap.serialize(new DataOutputStream(outputStream));
    }
}