/*
package org.sidney.encoding.int32;

import com.google.common.io.LittleEndianDataInputStream;
import me.lemire.integercompression.Composition;
import me.lemire.integercompression.FastPFOR;
import me.lemire.integercompression.IntWrapper;
import me.lemire.integercompression.IntegerCODEC;
import me.lemire.integercompression.VariableByte;
import org.sidney.encoding.AbstractDecoder;
import org.sidney.encoding.Encoding;
import org.sidney.encoding.unsafe.UnsafeBytes;

import java.io.IOException;
import java.io.InputStream;

*/
/**
 * Decoder for FastPfor encoded ints, generally much faster on medium size to larger data
 *//*

public class FastBitPackInt32Decoder extends AbstractDecoder implements Int32Decoder {
    private final IntegerCODEC codec = new Composition(new FastPFOR(), new VariableByte());
    private final IntWrapper sourceWrapper = new IntWrapper();
    private final IntWrapper destinationWrapper = new IntWrapper();
    private int[] sourceBuffer;
    private int[] destinationBuffer;
    private byte[] sourceByteBuffer;
    private int index = 0;

    public FastBitPackInt32Decoder() {
        sourceBuffer = new int[256];
        destinationBuffer = new int[2048];
        sourceByteBuffer = new byte[sourceBuffer.length * 4];
    }

    @Override
    public int nextInt() {
        return destinationBuffer[index++];
    }

    @Override
    public int[] nextInts(int num) {
        int[] arr = new int[num];
        for (int i = 0; i < num; i++) {
            arr[i] = nextInt();
        }
        return arr;
    }

    @Override
    public void readFromStream(InputStream inputStream) throws IOException {
        LittleEndianDataInputStream dis = dataInputStreamWrapIfNecessary(inputStream);

        int numInts = dis.readInt();
        int compressedSize = dis.readInt();

        requireBytes(compressedSize * 4);
        require(Math.max(numInts, compressedSize * 4));

        dis.read(sourceByteBuffer, 0, compressedSize * 4);

        unpack(numInts, compressedSize);
    }

    @Override
    public String supportedEncoding() {
        return Encoding.BITPACKED.name();
    }

    private void unpack(int numInts, int compressedSize) {
        require(Math.max(numInts, compressedSize * 4));
        reset();

        UnsafeBytes.copyBytesToInts(sourceByteBuffer, 0, sourceBuffer, 0, compressedSize * 4);

        codec.uncompress(sourceBuffer, sourceWrapper, compressedSize, destinationBuffer, destinationWrapper);
    }

    private void reset() {
        destinationWrapper.set(0);
        sourceWrapper.set(0);
        index = 0;
    }

    private void requireBytes(int size) {
        if (size > sourceByteBuffer.length) {
            sourceByteBuffer = new byte[size * 2];
        }
    }

    private void require(int size) {
        if (size > sourceBuffer.length) {
            sourceBuffer = new int[size * 2];
            destinationBuffer = new int[size * 2];
        }
    }
}*/
