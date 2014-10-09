package org.sidney.encoding;

import org.apache.lucene.util.packed.PackedInts;

public class IntPacker {
    private final PackedInts.Encoder encoder;
    private final PackedInts.Decoder decoder;
    private int bitwidth;

    public IntPacker(int bitwidth) {
        this.bitwidth = bitwidth;
        encoder = PackedInts.getEncoder(PackedInts.Format.PACKED, PackedInts.VERSION_CURRENT, bitwidth);
        decoder = PackedInts.getDecoder(PackedInts.Format.PACKED, PackedInts.VERSION_CURRENT, bitwidth);
    }

    private int iterations(int num) {
        return (int) Math.ceil((float) num / decoder.byteValueCount());
    }

    public void encode(int[] source, int sourceOffset, byte[] destination, int destinationOffset, int num) {
        encoder.encode(source, sourceOffset, destination, destinationOffset, iterations(num));
    }

    public void encode(long[] source, int sourceOffset, byte[] destination, int destinationOffset, int num) {
        encoder.encode(source, sourceOffset, destination, destinationOffset, iterations(num));
    }

    public void decode(byte[] source, int sourceOffset, long[] destination, int destinationOffset, int num) {
        decoder.decode(source, sourceOffset, destination, destinationOffset, iterations(num));
    }

    public void decode(byte[] source, int sourceOffset, int[] destination, int destinationOffset, int num) {
        decoder.decode(source, sourceOffset, destination, destinationOffset, iterations(num));
    }
}