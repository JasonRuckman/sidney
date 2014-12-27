package org.sidney.core.encoding.unsafe;

import sun.misc.Unsafe;

public class UnsafeUtil {
    public static final long byteArrayBaseOffset;
    public static final long floatArrayBaseOffset;
    public static final long doubleArrayBaseOffset;
    public static final long intArrayBaseOffset;
    public static final long longArrayBaseOffset;
    public static final long shortArrayBaseOffset;
    public static final long charArrayBaseOffset;
    private static final Unsafe unsafe;

    static {
        java.lang.reflect.Field field = null;
        try {
            field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
            byteArrayBaseOffset = unsafe.arrayBaseOffset(byte[].class);
            charArrayBaseOffset = unsafe.arrayBaseOffset(char[].class);
            shortArrayBaseOffset = unsafe.arrayBaseOffset(short[].class);
            intArrayBaseOffset = unsafe.arrayBaseOffset(int[].class);
            floatArrayBaseOffset = unsafe.arrayBaseOffset(float[].class);
            longArrayBaseOffset = unsafe.arrayBaseOffset(long[].class);
            doubleArrayBaseOffset = unsafe.arrayBaseOffset(double[].class);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Unsafe unsafe() {
        return unsafe;
    }
}
