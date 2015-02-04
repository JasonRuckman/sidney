package com.github.jasonruckman.sidney.bitpacking.packers.le.int32;

import com.github.jasonruckman.sidney.bitpacking.Int32BytePacker;

public final class Packer2 extends Int32BytePacker {

  public Packer2() {
    super(2);
  }

  public final void pack8Values(final int[] in, final int inPos, final byte[] out, final int outPos) {
    out[0 + outPos] = (byte) ((
        //            [______10]
        //                  [10]
        ((in[0 + inPos] & 3))
            | //            [____32__]
            //                [10]
            ((in[1 + inPos] & 3) << 2)
            | //            [__54____]
            //              [10]
            ((in[2 + inPos] & 3) << 4)
            | //            [76______]
            //            [10]
            ((in[3 + inPos] & 3) << 6)) & 255);
    out[1 + outPos] = (byte) ((
        //            [______10]
        //                  [10]
        ((in[4 + inPos] & 3))
            | //            [____32__]
            //                [10]
            ((in[5 + inPos] & 3) << 2)
            | //            [__54____]
            //              [10]
            ((in[6 + inPos] & 3) << 4)
            | //            [76______]
            //            [10]
            ((in[7 + inPos] & 3) << 6)) & 255);
  }

  public final void pack32Values(final int[] in, final int inPos, final byte[] out, final int outPos) {
    out[0 + outPos] = (byte) ((
        //            [______10]
        //                  [10]
        ((in[0 + inPos] & 3))
            | //            [____32__]
            //                [10]
            ((in[1 + inPos] & 3) << 2)
            | //            [__54____]
            //              [10]
            ((in[2 + inPos] & 3) << 4)
            | //            [76______]
            //            [10]
            ((in[3 + inPos] & 3) << 6)) & 255);
    out[1 + outPos] = (byte) ((
        //            [______10]
        //                  [10]
        ((in[4 + inPos] & 3))
            | //            [____32__]
            //                [10]
            ((in[5 + inPos] & 3) << 2)
            | //            [__54____]
            //              [10]
            ((in[6 + inPos] & 3) << 4)
            | //            [76______]
            //            [10]
            ((in[7 + inPos] & 3) << 6)) & 255);
    out[2 + outPos] = (byte) ((
        //            [______10]
        //                  [10]
        ((in[8 + inPos] & 3))
            | //            [____32__]
            //                [10]
            ((in[9 + inPos] & 3) << 2)
            | //            [__54____]
            //              [10]
            ((in[10 + inPos] & 3) << 4)
            | //            [76______]
            //            [10]
            ((in[11 + inPos] & 3) << 6)) & 255);
    out[3 + outPos] = (byte) ((
        //            [______10]
        //                  [10]
        ((in[12 + inPos] & 3))
            | //            [____32__]
            //                [10]
            ((in[13 + inPos] & 3) << 2)
            | //            [__54____]
            //              [10]
            ((in[14 + inPos] & 3) << 4)
            | //            [76______]
            //            [10]
            ((in[15 + inPos] & 3) << 6)) & 255);
    out[4 + outPos] = (byte) ((
        //            [______10]
        //                  [10]
        ((in[16 + inPos] & 3))
            | //            [____32__]
            //                [10]
            ((in[17 + inPos] & 3) << 2)
            | //            [__54____]
            //              [10]
            ((in[18 + inPos] & 3) << 4)
            | //            [76______]
            //            [10]
            ((in[19 + inPos] & 3) << 6)) & 255);
    out[5 + outPos] = (byte) ((
        //            [______10]
        //                  [10]
        ((in[20 + inPos] & 3))
            | //            [____32__]
            //                [10]
            ((in[21 + inPos] & 3) << 2)
            | //            [__54____]
            //              [10]
            ((in[22 + inPos] & 3) << 4)
            | //            [76______]
            //            [10]
            ((in[23 + inPos] & 3) << 6)) & 255);
    out[6 + outPos] = (byte) ((
        //            [______10]
        //                  [10]
        ((in[24 + inPos] & 3))
            | //            [____32__]
            //                [10]
            ((in[25 + inPos] & 3) << 2)
            | //            [__54____]
            //              [10]
            ((in[26 + inPos] & 3) << 4)
            | //            [76______]
            //            [10]
            ((in[27 + inPos] & 3) << 6)) & 255);
    out[7 + outPos] = (byte) ((
        //            [______10]
        //                  [10]
        ((in[28 + inPos] & 3))
            | //            [____32__]
            //                [10]
            ((in[29 + inPos] & 3) << 2)
            | //            [__54____]
            //              [10]
            ((in[30 + inPos] & 3) << 4)
            | //            [76______]
            //            [10]
            ((in[31 + inPos] & 3) << 6)) & 255);
  }

  public final void unpack8Values(final byte[] in, final int inPos, final int[] out, final int outPos) {
    out[0 + outPos] =
        //            [______10]
        //                  [10]
        (((((int) in[0 + inPos]) & 255)) & 3);
    out[1 + outPos] =
        //            [____32__]
        //                [10]
        (((((int) in[0 + inPos]) & 255) >>> 2) & 3);
    out[2 + outPos] =
        //            [__54____]
        //              [10]
        (((((int) in[0 + inPos]) & 255) >>> 4) & 3);
    out[3 + outPos] =
        //            [76______]
        //            [10]
        (((((int) in[0 + inPos]) & 255) >>> 6) & 3);
    out[4 + outPos] =
        //            [______10]
        //                  [10]
        (((((int) in[1 + inPos]) & 255)) & 3);
    out[5 + outPos] =
        //            [____32__]
        //                [10]
        (((((int) in[1 + inPos]) & 255) >>> 2) & 3);
    out[6 + outPos] =
        //            [__54____]
        //              [10]
        (((((int) in[1 + inPos]) & 255) >>> 4) & 3);
    out[7 + outPos] =
        //            [76______]
        //            [10]
        (((((int) in[1 + inPos]) & 255) >>> 6) & 3);
  }

  public final void unpack32Values(final byte[] in, final int inPos, final int[] out, final int outPos) {
    out[0 + outPos] =
        //            [______10]
        //                  [10]
        (((((int) in[0 + inPos]) & 255)) & 3);
    out[1 + outPos] =
        //            [____32__]
        //                [10]
        (((((int) in[0 + inPos]) & 255) >>> 2) & 3);
    out[2 + outPos] =
        //            [__54____]
        //              [10]
        (((((int) in[0 + inPos]) & 255) >>> 4) & 3);
    out[3 + outPos] =
        //            [76______]
        //            [10]
        (((((int) in[0 + inPos]) & 255) >>> 6) & 3);
    out[4 + outPos] =
        //            [______10]
        //                  [10]
        (((((int) in[1 + inPos]) & 255)) & 3);
    out[5 + outPos] =
        //            [____32__]
        //                [10]
        (((((int) in[1 + inPos]) & 255) >>> 2) & 3);
    out[6 + outPos] =
        //            [__54____]
        //              [10]
        (((((int) in[1 + inPos]) & 255) >>> 4) & 3);
    out[7 + outPos] =
        //            [76______]
        //            [10]
        (((((int) in[1 + inPos]) & 255) >>> 6) & 3);
    out[8 + outPos] =
        //            [______10]
        //                  [10]
        (((((int) in[2 + inPos]) & 255)) & 3);
    out[9 + outPos] =
        //            [____32__]
        //                [10]
        (((((int) in[2 + inPos]) & 255) >>> 2) & 3);
    out[10 + outPos] =
        //            [__54____]
        //              [10]
        (((((int) in[2 + inPos]) & 255) >>> 4) & 3);
    out[11 + outPos] =
        //            [76______]
        //            [10]
        (((((int) in[2 + inPos]) & 255) >>> 6) & 3);
    out[12 + outPos] =
        //            [______10]
        //                  [10]
        (((((int) in[3 + inPos]) & 255)) & 3);
    out[13 + outPos] =
        //            [____32__]
        //                [10]
        (((((int) in[3 + inPos]) & 255) >>> 2) & 3);
    out[14 + outPos] =
        //            [__54____]
        //              [10]
        (((((int) in[3 + inPos]) & 255) >>> 4) & 3);
    out[15 + outPos] =
        //            [76______]
        //            [10]
        (((((int) in[3 + inPos]) & 255) >>> 6) & 3);
    out[16 + outPos] =
        //            [______10]
        //                  [10]
        (((((int) in[4 + inPos]) & 255)) & 3);
    out[17 + outPos] =
        //            [____32__]
        //                [10]
        (((((int) in[4 + inPos]) & 255) >>> 2) & 3);
    out[18 + outPos] =
        //            [__54____]
        //              [10]
        (((((int) in[4 + inPos]) & 255) >>> 4) & 3);
    out[19 + outPos] =
        //            [76______]
        //            [10]
        (((((int) in[4 + inPos]) & 255) >>> 6) & 3);
    out[20 + outPos] =
        //            [______10]
        //                  [10]
        (((((int) in[5 + inPos]) & 255)) & 3);
    out[21 + outPos] =
        //            [____32__]
        //                [10]
        (((((int) in[5 + inPos]) & 255) >>> 2) & 3);
    out[22 + outPos] =
        //            [__54____]
        //              [10]
        (((((int) in[5 + inPos]) & 255) >>> 4) & 3);
    out[23 + outPos] =
        //            [76______]
        //            [10]
        (((((int) in[5 + inPos]) & 255) >>> 6) & 3);
    out[24 + outPos] =
        //            [______10]
        //                  [10]
        (((((int) in[6 + inPos]) & 255)) & 3);
    out[25 + outPos] =
        //            [____32__]
        //                [10]
        (((((int) in[6 + inPos]) & 255) >>> 2) & 3);
    out[26 + outPos] =
        //            [__54____]
        //              [10]
        (((((int) in[6 + inPos]) & 255) >>> 4) & 3);
    out[27 + outPos] =
        //            [76______]
        //            [10]
        (((((int) in[6 + inPos]) & 255) >>> 6) & 3);
    out[28 + outPos] =
        //            [______10]
        //                  [10]
        (((((int) in[7 + inPos]) & 255)) & 3);
    out[29 + outPos] =
        //            [____32__]
        //                [10]
        (((((int) in[7 + inPos]) & 255) >>> 2) & 3);
    out[30 + outPos] =
        //            [__54____]
        //              [10]
        (((((int) in[7 + inPos]) & 255) >>> 4) & 3);
    out[31 + outPos] =
        //            [76______]
        //            [10]
        (((((int) in[7 + inPos]) & 255) >>> 6) & 3);
  }
}
