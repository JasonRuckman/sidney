package org.sidney.encoding;

public class IntPackerFactory {
    private static final IntPacker[] packers = new IntPacker[65];
    static {
        for(int i = 1; i <= 64; i++) {
            packers[i] = new IntPacker(i);
        }
    }

    public static IntPacker packer(int bitwidth) {
        if(bitwidth == 0) {
            throw new IllegalStateException(String.format("Illegal bitwidth: %s", bitwidth));
        }
        return packers[bitwidth];
    }
}