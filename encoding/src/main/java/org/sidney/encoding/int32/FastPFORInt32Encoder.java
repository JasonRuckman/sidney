package org.sidney.encoding.int32;

import me.lemire.integercompression.Composition;
import me.lemire.integercompression.FastPFOR;
import me.lemire.integercompression.IntWrapper;
import me.lemire.integercompression.IntegerCODEC;
import me.lemire.integercompression.VariableByte;
import org.sidney.core.unsafe.UnsafeInts;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

//TODO: Fix this to work with more than 65536 ints at a time, basically create pages that get compressed as we go, then flush them all together

/**
 * Uses https://github.com/lemire/JavaFastPFOR to encode blocks of ints.
 * Slower than kryo for smaller data, faster for larger data (up to a point).  Generates a generally smaller binary footprint
 */
public class FastPFORInt32Encoder implements Int32Encoder {
    private final IntWrapper sourceWrapper = new IntWrapper();
    private final IntWrapper destinationWrapper = new IntWrapper();
    private final IntegerCODEC codec = new Composition(new FastPFOR(), new VariableByte());
    private int[] uncompressedInts;
    private int[] compressedInts;
    private byte[] compressedBytes;
    private int currentIndex = 0;

    public FastPFORInt32Encoder() {
        uncompressedInts = new int[256];
        compressedInts = new int[512];
        compressedBytes = new byte[compressedInts.length * 4];

        reset();
    }

    @Override
    public void reset() {
        currentIndex = 0;
        sourceWrapper.set(0);
        destinationWrapper.set(0);
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        DataOutputStream dos = new DataOutputStream(outputStream);
        compressIntoDestinationBuffer();

        dos.writeInt(currentIndex);
        dos.writeInt(destinationWrapper.get());

        final int sizeInBytes = destinationWrapper.get() * 4;
        ensureDestinationByteBufferCapacity(sizeInBytes);

        //two copies, but much faster than looping through the int array and shifting out the values
        UnsafeInts.copyIntsToBytes(compressedInts, 0, compressedBytes, 0, sizeInBytes);

        dos.write(compressedBytes, 0, sizeInBytes);
    }

    @Override
    public void writeInt(int value) {
        ensureSourceCapacity(currentIndex + 1);
        uncompressedInts[currentIndex++] = value;
    }

    @Override
    public void writeInts(int[] values) {
        ensureSourceCapacity(currentIndex + values.length);
        System.arraycopy(values, 0, uncompressedInts, currentIndex, values.length);
        currentIndex += values.length;
    }

    private void compressIntoDestinationBuffer() {
        ensureDestinationCapacity(uncompressedInts.length * 4);
        codec.compress(uncompressedInts, sourceWrapper, currentIndex, compressedInts, destinationWrapper);
    }

    private void ensureSourceCapacity(int size) {
        if (size >= uncompressedInts.length) {
            int[] newBuf = new int[size * 2];
            System.arraycopy(uncompressedInts, 0, newBuf, 0, uncompressedInts.length);
            uncompressedInts = newBuf;
            ensureSourceCapacity(size);
        }
    }

    private void ensureDestinationCapacity(int size) {
        if (size >= compressedInts.length) {
            compressedInts = new int[compressedInts.length * 2];
            ensureDestinationCapacity(size);
        }
    }

    private void ensureDestinationByteBufferCapacity(int size) {
        if (size >= compressedBytes.length) {
            compressedBytes = new byte[compressedBytes.length * 2];
            ensureDestinationByteBufferCapacity(size);
        }
    }
}