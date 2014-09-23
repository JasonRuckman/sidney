package org.sidney.encoding.int32;

import me.lemire.integercompression.Composition;
import me.lemire.integercompression.FastPFOR;
import me.lemire.integercompression.IntWrapper;
import me.lemire.integercompression.IntegerCODEC;
import me.lemire.integercompression.VariableByte;
import org.sidney.core.Bytes;
import org.sidney.core.unsafe.UnsafeInts;

import java.io.IOException;
import java.io.OutputStream;

//TODO: Fix this to work with more than 65536 ints at a time

/**
 * Uses https://github.com/lemire/JavaFastPFOR to encode blocks of ints.
 * Slower than kryo for smaller data, faster for larger data (up to a point).  Generates a generally smaller binary footprint
 */
public class FastPFORInt32Encoder implements Int32Encoder {
    private final IntWrapper sourceWrapper = new IntWrapper();
    private final IntWrapper destinationWrapper = new IntWrapper();
    private final IntegerCODEC codec = new Composition(new FastPFOR(), new VariableByte());
    private int[] sourceBuffer;
    private int[] destinationBuffer;
    private byte[] destinationByteBuffer;
    private int currentIndex = 0;

    public FastPFORInt32Encoder() {
        sourceBuffer = new int[256];
        destinationBuffer = new int[512];
        destinationByteBuffer = new byte[destinationBuffer.length * 4];

        reset();
    }

    @Override
    public void reset() {
        currentIndex = 0;
        sourceWrapper.set(0);
        destinationWrapper.set(0);
    }

    @Override
    public int writeToBuffer(byte[] buffer, int offset) {
        compressIntoDestinationBuffer();

        Bytes.writeIntOn4Bytes(currentIndex, buffer, offset);
        Bytes.writeIntOn4Bytes(destinationWrapper.get(), buffer, 4);
        copyToBuffer(buffer, 0, offset + 8, destinationWrapper.get());

        return offset + 8;
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        compressIntoDestinationBuffer();

        outputStream.write(currentIndex);
        outputStream.write(destinationWrapper.get());

        final int sizeInBytes = destinationWrapper.get() * 4;
        ensureDestinationByteBufferCapacity(sizeInBytes);

        //two copies, but faster than looping through the int array and shifting out the values
        copyToBuffer(destinationByteBuffer, 0, 0, destinationWrapper.get());

        outputStream.write(destinationByteBuffer, 0, sizeInBytes);
    }

    private void copyToBuffer(byte[] buffer, int sourceOffset, int destinationOffset, int numInts) {
        UnsafeInts.copyIntsToBytes(destinationBuffer, sourceOffset, buffer, destinationOffset, numInts * 4);
    }

    @Override
    public void writeInt(int value) {
        ensureSourceCapacity(currentIndex + 1);
        sourceBuffer[currentIndex++] = value;
    }

    @Override
    public void writeInts(int[] values) {
        for (int k : values) {
            writeInt(k);
        }
    }

    private void compressIntoDestinationBuffer() {
        ensureDestinationCapacity(sourceBuffer.length * 4);
        codec.compress(sourceBuffer, sourceWrapper, currentIndex, destinationBuffer, destinationWrapper);
    }

    private void ensureSourceCapacity(int size) {
        if (size >= sourceBuffer.length) {
            int[] newBuf = new int[size * 2];
            System.arraycopy(sourceBuffer, 0, newBuf, 0, sourceBuffer.length);
            sourceBuffer = newBuf;
            ensureSourceCapacity(size);
        }
    }

    private void ensureDestinationCapacity(int size) {
        if (size >= destinationBuffer.length) {
            destinationBuffer = new int[destinationBuffer.length * 2];
            ensureDestinationCapacity(size);
        }
    }

    private void ensureDestinationByteBufferCapacity(int size) {
        if (size >= destinationByteBuffer.length) {
            destinationByteBuffer = new byte[destinationByteBuffer.length * 2];
            ensureDestinationCapacity(size);
        }
    }
}