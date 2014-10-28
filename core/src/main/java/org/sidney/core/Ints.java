package org.sidney.core;

public class Ints {
    public static int zigzagEncode(int n) {
        return (n << 1) ^ (n >> 31);
    }

    public static int zigzagDecode(int n) {
        return (n >>> 1) ^ -(n & 1);
    }
}