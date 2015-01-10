package org.sidney.core;

import java.util.Arrays;

public class AllPrimitiveArrays {
    private boolean[] booleans;
    private short[] shorts;
    private char[] chars;
    private int[] ints;
    private long[] longs;
    private float[] floats;
    private double[] doubles;

    public AllPrimitiveArrays() {

    }

    public AllPrimitiveArrays(boolean[] booleans, short[] shorts, char[] chars, int[] ints, long[] longs, float[] floats, double[] doubles) {
        this.booleans = booleans;
        this.shorts = shorts;
        this.chars = chars;
        this.ints = ints;
        this.longs = longs;
        this.floats = floats;
        this.doubles = doubles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AllPrimitiveArrays arrays = (AllPrimitiveArrays) o;

        if (!Arrays.equals(booleans, arrays.booleans)) return false;
        if (!Arrays.equals(doubles, arrays.doubles)) return false;
        if (!Arrays.equals(floats, arrays.floats)) return false;
        if (!Arrays.equals(ints, arrays.ints)) return false;
        if (!Arrays.equals(longs, arrays.longs)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = booleans != null ? Arrays.hashCode(booleans) : 0;
        result = 31 * result + (ints != null ? Arrays.hashCode(ints) : 0);
        result = 31 * result + (longs != null ? Arrays.hashCode(longs) : 0);
        result = 31 * result + (floats != null ? Arrays.hashCode(floats) : 0);
        result = 31 * result + (doubles != null ? Arrays.hashCode(doubles) : 0);
        return result;
    }
}