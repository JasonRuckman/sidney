package com.github.jasonruckman.sidney.bitpacking.packers.le.int32;

import com.github.jasonruckman.sidney.bitpacking.Int32BytePacker;

public final class Packer4 extends Int32BytePacker {

  public Packer4() {
    super(4);
  }

  public final void pack8Values(final int[] in, final int inPos, final byte[] out, final int outPos) {
    out[0 + outPos] = (byte) ((
        //              [____3210]
        //                  [3210]
        ((in[0 + inPos] & 15))
            | //              [7654____]
            //              [3210]
            ((in[1 + inPos] & 15) << 4)) & 255);
    out[1 + outPos] = (byte) ((
        //              [____3210]
        //                  [3210]
        ((in[2 + inPos] & 15))
            | //              [7654____]
            //              [3210]
            ((in[3 + inPos] & 15) << 4)) & 255);
    out[2 + outPos] = (byte) ((
        //              [____3210]
        //                  [3210]
        ((in[4 + inPos] & 15))
            | //              [7654____]
            //              [3210]
            ((in[5 + inPos] & 15) << 4)) & 255);
    out[3 + outPos] = (byte) ((
        //              [____3210]
        //                  [3210]
        ((in[6 + inPos] & 15))
            | //              [7654____]
            //              [3210]
            ((in[7 + inPos] & 15) << 4)) & 255);
  }

  public final void pack32Values(final int[] in, final int inPos, final byte[] out, final int outPos) {
    out[0 + outPos] = (byte) ((
        //              [____3210]
        //                  [3210]
        ((in[0 + inPos] & 15))
            | //              [7654____]
            //              [3210]
            ((in[1 + inPos] & 15) << 4)) & 255);
    out[1 + outPos] = (byte) ((
        //              [____3210]
        //                  [3210]
        ((in[2 + inPos] & 15))
            | //              [7654____]
            //              [3210]
            ((in[3 + inPos] & 15) << 4)) & 255);
    out[2 + outPos] = (byte) ((
        //              [____3210]
        //                  [3210]
        ((in[4 + inPos] & 15))
            | //              [7654____]
            //              [3210]
            ((in[5 + inPos] & 15) << 4)) & 255);
    out[3 + outPos] = (byte) ((
        //              [____3210]
        //                  [3210]
        ((in[6 + inPos] & 15))
            | //              [7654____]
            //              [3210]
            ((in[7 + inPos] & 15) << 4)) & 255);
    out[4 + outPos] = (byte) ((
        //              [____3210]
        //                  [3210]
        ((in[8 + inPos] & 15))
            | //              [7654____]
            //              [3210]
            ((in[9 + inPos] & 15) << 4)) & 255);
    out[5 + outPos] = (byte) ((
        //              [____3210]
        //                  [3210]
        ((in[10 + inPos] & 15))
            | //              [7654____]
            //              [3210]
            ((in[11 + inPos] & 15) << 4)) & 255);
    out[6 + outPos] = (byte) ((
        //              [____3210]
        //                  [3210]
        ((in[12 + inPos] & 15))
            | //              [7654____]
            //              [3210]
            ((in[13 + inPos] & 15) << 4)) & 255);
    out[7 + outPos] = (byte) ((
        //              [____3210]
        //                  [3210]
        ((in[14 + inPos] & 15))
            | //              [7654____]
            //              [3210]
            ((in[15 + inPos] & 15) << 4)) & 255);
    out[8 + outPos] = (byte) ((
        //              [____3210]
        //                  [3210]
        ((in[16 + inPos] & 15))
            | //              [7654____]
            //              [3210]
            ((in[17 + inPos] & 15) << 4)) & 255);
    out[9 + outPos] = (byte) ((
        //              [____3210]
        //                  [3210]
        ((in[18 + inPos] & 15))
            | //              [7654____]
            //              [3210]
            ((in[19 + inPos] & 15) << 4)) & 255);
    out[10 + outPos] = (byte) ((
        //              [____3210]
        //                  [3210]
        ((in[20 + inPos] & 15))
            | //              [7654____]
            //              [3210]
            ((in[21 + inPos] & 15) << 4)) & 255);
    out[11 + outPos] = (byte) ((
        //              [____3210]
        //                  [3210]
        ((in[22 + inPos] & 15))
            | //              [7654____]
            //              [3210]
            ((in[23 + inPos] & 15) << 4)) & 255);
    out[12 + outPos] = (byte) ((
        //              [____3210]
        //                  [3210]
        ((in[24 + inPos] & 15))
            | //              [7654____]
            //              [3210]
            ((in[25 + inPos] & 15) << 4)) & 255);
    out[13 + outPos] = (byte) ((
        //              [____3210]
        //                  [3210]
        ((in[26 + inPos] & 15))
            | //              [7654____]
            //              [3210]
            ((in[27 + inPos] & 15) << 4)) & 255);
    out[14 + outPos] = (byte) ((
        //              [____3210]
        //                  [3210]
        ((in[28 + inPos] & 15))
            | //              [7654____]
            //              [3210]
            ((in[29 + inPos] & 15) << 4)) & 255);
    out[15 + outPos] = (byte) ((
        //              [____3210]
        //                  [3210]
        ((in[30 + inPos] & 15))
            | //              [7654____]
            //              [3210]
            ((in[31 + inPos] & 15) << 4)) & 255);
  }

  public final void unpack8Values(final byte[] in, final int inPos, final int[] out, final int outPos) {
    out[0 + outPos] =
        //              [____3210]
        //                  [3210]
        (((((int) in[0 + inPos]) & 255)) & 15);
    out[1 + outPos] =
        //              [7654____]
        //              [3210]
        (((((int) in[0 + inPos]) & 255) >>> 4) & 15);
    out[2 + outPos] =
        //              [____3210]
        //                  [3210]
        (((((int) in[1 + inPos]) & 255)) & 15);
    out[3 + outPos] =
        //              [7654____]
        //              [3210]
        (((((int) in[1 + inPos]) & 255) >>> 4) & 15);
    out[4 + outPos] =
        //              [____3210]
        //                  [3210]
        (((((int) in[2 + inPos]) & 255)) & 15);
    out[5 + outPos] =
        //              [7654____]
        //              [3210]
        (((((int) in[2 + inPos]) & 255) >>> 4) & 15);
    out[6 + outPos] =
        //              [____3210]
        //                  [3210]
        (((((int) in[3 + inPos]) & 255)) & 15);
    out[7 + outPos] =
        //              [7654____]
        //              [3210]
        (((((int) in[3 + inPos]) & 255) >>> 4) & 15);
  }

  public final void unpack32Values(final byte[] in, final int inPos, final int[] out, final int outPos) {
    out[0 + outPos] =
        //              [____3210]
        //                  [3210]
        (((((int) in[0 + inPos]) & 255)) & 15);
    out[1 + outPos] =
        //              [7654____]
        //              [3210]
        (((((int) in[0 + inPos]) & 255) >>> 4) & 15);
    out[2 + outPos] =
        //              [____3210]
        //                  [3210]
        (((((int) in[1 + inPos]) & 255)) & 15);
    out[3 + outPos] =
        //              [7654____]
        //              [3210]
        (((((int) in[1 + inPos]) & 255) >>> 4) & 15);
    out[4 + outPos] =
        //              [____3210]
        //                  [3210]
        (((((int) in[2 + inPos]) & 255)) & 15);
    out[5 + outPos] =
        //              [7654____]
        //              [3210]
        (((((int) in[2 + inPos]) & 255) >>> 4) & 15);
    out[6 + outPos] =
        //              [____3210]
        //                  [3210]
        (((((int) in[3 + inPos]) & 255)) & 15);
    out[7 + outPos] =
        //              [7654____]
        //              [3210]
        (((((int) in[3 + inPos]) & 255) >>> 4) & 15);
    out[8 + outPos] =
        //              [____3210]
        //                  [3210]
        (((((int) in[4 + inPos]) & 255)) & 15);
    out[9 + outPos] =
        //              [7654____]
        //              [3210]
        (((((int) in[4 + inPos]) & 255) >>> 4) & 15);
    out[10 + outPos] =
        //              [____3210]
        //                  [3210]
        (((((int) in[5 + inPos]) & 255)) & 15);
    out[11 + outPos] =
        //              [7654____]
        //              [3210]
        (((((int) in[5 + inPos]) & 255) >>> 4) & 15);
    out[12 + outPos] =
        //              [____3210]
        //                  [3210]
        (((((int) in[6 + inPos]) & 255)) & 15);
    out[13 + outPos] =
        //              [7654____]
        //              [3210]
        (((((int) in[6 + inPos]) & 255) >>> 4) & 15);
    out[14 + outPos] =
        //              [____3210]
        //                  [3210]
        (((((int) in[7 + inPos]) & 255)) & 15);
    out[15 + outPos] =
        //              [7654____]
        //              [3210]
        (((((int) in[7 + inPos]) & 255) >>> 4) & 15);
    out[16 + outPos] =
        //              [____3210]
        //                  [3210]
        (((((int) in[8 + inPos]) & 255)) & 15);
    out[17 + outPos] =
        //              [7654____]
        //              [3210]
        (((((int) in[8 + inPos]) & 255) >>> 4) & 15);
    out[18 + outPos] =
        //              [____3210]
        //                  [3210]
        (((((int) in[9 + inPos]) & 255)) & 15);
    out[19 + outPos] =
        //              [7654____]
        //              [3210]
        (((((int) in[9 + inPos]) & 255) >>> 4) & 15);
    out[20 + outPos] =
        //              [____3210]
        //                  [3210]
        (((((int) in[10 + inPos]) & 255)) & 15);
    out[21 + outPos] =
        //              [7654____]
        //              [3210]
        (((((int) in[10 + inPos]) & 255) >>> 4) & 15);
    out[22 + outPos] =
        //              [____3210]
        //                  [3210]
        (((((int) in[11 + inPos]) & 255)) & 15);
    out[23 + outPos] =
        //              [7654____]
        //              [3210]
        (((((int) in[11 + inPos]) & 255) >>> 4) & 15);
    out[24 + outPos] =
        //              [____3210]
        //                  [3210]
        (((((int) in[12 + inPos]) & 255)) & 15);
    out[25 + outPos] =
        //              [7654____]
        //              [3210]
        (((((int) in[12 + inPos]) & 255) >>> 4) & 15);
    out[26 + outPos] =
        //              [____3210]
        //                  [3210]
        (((((int) in[13 + inPos]) & 255)) & 15);
    out[27 + outPos] =
        //              [7654____]
        //              [3210]
        (((((int) in[13 + inPos]) & 255) >>> 4) & 15);
    out[28 + outPos] =
        //              [____3210]
        //                  [3210]
        (((((int) in[14 + inPos]) & 255)) & 15);
    out[29 + outPos] =
        //              [7654____]
        //              [3210]
        (((((int) in[14 + inPos]) & 255) >>> 4) & 15);
    out[30 + outPos] =
        //              [____3210]
        //                  [3210]
        (((((int) in[15 + inPos]) & 255)) & 15);
    out[31 + outPos] =
        //              [7654____]
        //              [3210]
        (((((int) in[15 + inPos]) & 255) >>> 4) & 15);
  }
}
