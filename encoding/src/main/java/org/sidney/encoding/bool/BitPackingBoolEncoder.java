package org.sidney.encoding.bool;

import org.sidney.encoding.Encoding;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Simple encoder for bitpacking bools, encodes each bool as a 1 or a 0 on one bit.
 * Encodes the number of booleans as an int, then a byte[] containing packed values.
 */
public class BitPackingBoolEncoder implements BoolEncoder {
    protected byte[] buffer;
    protected int index = 0;
    protected int offset = 0;
    protected int numBools = 0;

    public BitPackingBoolEncoder() {
        buffer = new byte[256];
    }

    @Override
    public void writeBool(boolean value) {
        numBools++;
        checkSize(numBools * 8);

        if (index == 8) {
            index = 0;
            offset++;
        }

        if (value) {
            buffer[offset] |= (1 << index);
        } else {
            buffer[offset] &= ~(1 << index);
        }
        index++;
    }

    @Override
    public void writeBools(boolean[] values) {
        for (boolean value : values) {
            writeBool(value);
        }
    }

    @Override
    public void reset() {
        index = 0;
        offset = 0;
        numBools = 0;
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        DataOutputStream dos = new DataOutputStream(outputStream);
        dos.writeInt(numBools);
        dos.write(buffer, 0, sizeInBytes());
    }

    @Override
    public Encoding supportedEncoding() {
        return Encoding.BITPACKED;
    }

    private int sizeInBytes() {
        return numBools * 8;
    }

    private void checkSize(int required) {
        if (required > buffer.length) {
            byte[] newBuffer = new byte[required * 2];
            System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
            buffer = newBuffer;
            checkSize(required);
        }
    }
}