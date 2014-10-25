package org.sidney.encoding.unsafe;

import com.esotericsoftware.kryo.util.UnsafeUtil;
import sun.misc.Unsafe;

public class UnsafeLongs {
    private static Unsafe unsafe = UnsafeUtil.unsafe();

    public static void copyLongsToBytes(
        long[] sourceArray, long sourcePos, byte[] destinationArray, long destinationPos, long length
    ) {
        unsafe.copyMemory(
            sourceArray, UnsafeUtil.longArrayBaseOffset + sourcePos, destinationArray,
            UnsafeUtil.byteArrayBaseOffset + destinationPos, length
        );
    }

    public static void copyBytesToLongs(
        byte[] sourceArray, long sourcePos, long[] destinationArray, long destinationPos, long length
    ) {
        unsafe.copyMemory(
            sourceArray, UnsafeUtil.byteArrayBaseOffset + sourcePos, destinationArray, UnsafeUtil.longArrayBaseOffset + (destinationPos * 8), length
        );
    }
}