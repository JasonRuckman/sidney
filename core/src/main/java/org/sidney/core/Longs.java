package org.sidney.core;

public class Longs {
    public static long zigzagEncode(long n) {
        return (n << 1) ^ (n >> 63);
    }

    public static long zigzagDecode(long n) {
        return (n >>> 1) ^ -(n & 1);
    }
}