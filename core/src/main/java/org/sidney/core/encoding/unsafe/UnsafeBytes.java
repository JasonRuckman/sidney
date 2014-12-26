package org.sidney.core.encoding.unsafe;

import sun.misc.Unsafe;

public class UnsafeBytes {
    private static final Unsafe unsafe = UnsafeUtil.unsafe();

    public static void copyBytesToInts(
            byte[] source, long sourcePos, int[] destination, long destinationPos, long length
    ) {
        unsafe.copyMemory(
                source, UnsafeUtil.byteArrayBaseOffset + sourcePos, destination,
                UnsafeUtil.intArrayBaseOffset + destinationPos, length
        );
    }
}
