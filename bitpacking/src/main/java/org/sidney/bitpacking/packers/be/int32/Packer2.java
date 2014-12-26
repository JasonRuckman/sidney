package org.sidney.bitpacking.packers.be.int32;

import org.sidney.bitpacking.Int32BytePacker;

public final class Packer2 extends Int32BytePacker {

    public Packer2() {
        super(2);
    }

    public final void pack8Values(final int[] in, final int inPos, final byte[] out, final int outPos) {
        out[0 + outPos] = (byte) ((
                //            [76______]
                //            [10]
                ((in[0 + inPos] & 3) << 6)
                        | //            [__54____]
                        //              [10]
                        ((in[1 + inPos] & 3) << 4)
                        | //            [____32__]
                        //                [10]
                        ((in[2 + inPos] & 3) << 2)
                        | //            [______10]
                        //                  [10]
                        ((in[3 + inPos] & 3))) & 255);
        out[1 + outPos] = (byte) ((
                //            [76______]
                //            [10]
                ((in[4 + inPos] & 3) << 6)
                        | //            [__54____]
                        //              [10]
                        ((in[5 + inPos] & 3) << 4)
                        | //            [____32__]
                        //                [10]
                        ((in[6 + inPos] & 3) << 2)
                        | //            [______10]
                        //                  [10]
                        ((in[7 + inPos] & 3))) & 255);
    }

    public final void pack32Values(final int[] in, final int inPos, final byte[] out, final int outPos) {
        out[0 + outPos] = (byte) ((
                //            [76______]
                //            [10]
                ((in[0 + inPos] & 3) << 6)
                        | //            [__54____]
                        //              [10]
                        ((in[1 + inPos] & 3) << 4)
                        | //            [____32__]
                        //                [10]
                        ((in[2 + inPos] & 3) << 2)
                        | //            [______10]
                        //                  [10]
                        ((in[3 + inPos] & 3))) & 255);
        out[1 + outPos] = (byte) ((
                //            [76______]
                //            [10]
                ((in[4 + inPos] & 3) << 6)
                        | //            [__54____]
                        //              [10]
                        ((in[5 + inPos] & 3) << 4)
                        | //            [____32__]
                        //                [10]
                        ((in[6 + inPos] & 3) << 2)
                        | //            [______10]
                        //                  [10]
                        ((in[7 + inPos] & 3))) & 255);
        out[2 + outPos] = (byte) ((
                //            [76______]
                //            [10]
                ((in[8 + inPos] & 3) << 6)
                        | //            [__54____]
                        //              [10]
                        ((in[9 + inPos] & 3) << 4)
                        | //            [____32__]
                        //                [10]
                        ((in[10 + inPos] & 3) << 2)
                        | //            [______10]
                        //                  [10]
                        ((in[11 + inPos] & 3))) & 255);
        out[3 + outPos] = (byte) ((
                //            [76______]
                //            [10]
                ((in[12 + inPos] & 3) << 6)
                        | //            [__54____]
                        //              [10]
                        ((in[13 + inPos] & 3) << 4)
                        | //            [____32__]
                        //                [10]
                        ((in[14 + inPos] & 3) << 2)
                        | //            [______10]
                        //                  [10]
                        ((in[15 + inPos] & 3))) & 255);
        out[4 + outPos] = (byte) ((
                //            [76______]
                //            [10]
                ((in[16 + inPos] & 3) << 6)
                        | //            [__54____]
                        //              [10]
                        ((in[17 + inPos] & 3) << 4)
                        | //            [____32__]
                        //                [10]
                        ((in[18 + inPos] & 3) << 2)
                        | //            [______10]
                        //                  [10]
                        ((in[19 + inPos] & 3))) & 255);
        out[5 + outPos] = (byte) ((
                //            [76______]
                //            [10]
                ((in[20 + inPos] & 3) << 6)
                        | //            [__54____]
                        //              [10]
                        ((in[21 + inPos] & 3) << 4)
                        | //            [____32__]
                        //                [10]
                        ((in[22 + inPos] & 3) << 2)
                        | //            [______10]
                        //                  [10]
                        ((in[23 + inPos] & 3))) & 255);
        out[6 + outPos] = (byte) ((
                //            [76______]
                //            [10]
                ((in[24 + inPos] & 3) << 6)
                        | //            [__54____]
                        //              [10]
                        ((in[25 + inPos] & 3) << 4)
                        | //            [____32__]
                        //                [10]
                        ((in[26 + inPos] & 3) << 2)
                        | //            [______10]
                        //                  [10]
                        ((in[27 + inPos] & 3))) & 255);
        out[7 + outPos] = (byte) ((
                //            [76______]
                //            [10]
                ((in[28 + inPos] & 3) << 6)
                        | //            [__54____]
                        //              [10]
                        ((in[29 + inPos] & 3) << 4)
                        | //            [____32__]
                        //                [10]
                        ((in[30 + inPos] & 3) << 2)
                        | //            [______10]
                        //                  [10]
                        ((in[31 + inPos] & 3))) & 255);
    }

    public final void unpack8Values(final byte[] in, final int inPos, final int[] out, final int outPos) {
        out[0 + outPos] =
                //            [76______]
                //            [10]
                (((((int) in[0 + inPos]) & 255) >>> 6) & 3);
        out[1 + outPos] =
                //            [__54____]
                //              [10]
                (((((int) in[0 + inPos]) & 255) >>> 4) & 3);
        out[2 + outPos] =
                //            [____32__]
                //                [10]
                (((((int) in[0 + inPos]) & 255) >>> 2) & 3);
        out[3 + outPos] =
                //            [______10]
                //                  [10]
                (((((int) in[0 + inPos]) & 255)) & 3);
        out[4 + outPos] =
                //            [76______]
                //            [10]
                (((((int) in[1 + inPos]) & 255) >>> 6) & 3);
        out[5 + outPos] =
                //            [__54____]
                //              [10]
                (((((int) in[1 + inPos]) & 255) >>> 4) & 3);
        out[6 + outPos] =
                //            [____32__]
                //                [10]
                (((((int) in[1 + inPos]) & 255) >>> 2) & 3);
        out[7 + outPos] =
                //            [______10]
                //                  [10]
                (((((int) in[1 + inPos]) & 255)) & 3);
    }

    public final void unpack32Values(final byte[] in, final int inPos, final int[] out, final int outPos) {
        out[0 + outPos] =
                //            [76______]
                //            [10]
                (((((int) in[0 + inPos]) & 255) >>> 6) & 3);
        out[1 + outPos] =
                //            [__54____]
                //              [10]
                (((((int) in[0 + inPos]) & 255) >>> 4) & 3);
        out[2 + outPos] =
                //            [____32__]
                //                [10]
                (((((int) in[0 + inPos]) & 255) >>> 2) & 3);
        out[3 + outPos] =
                //            [______10]
                //                  [10]
                (((((int) in[0 + inPos]) & 255)) & 3);
        out[4 + outPos] =
                //            [76______]
                //            [10]
                (((((int) in[1 + inPos]) & 255) >>> 6) & 3);
        out[5 + outPos] =
                //            [__54____]
                //              [10]
                (((((int) in[1 + inPos]) & 255) >>> 4) & 3);
        out[6 + outPos] =
                //            [____32__]
                //                [10]
                (((((int) in[1 + inPos]) & 255) >>> 2) & 3);
        out[7 + outPos] =
                //            [______10]
                //                  [10]
                (((((int) in[1 + inPos]) & 255)) & 3);
        out[8 + outPos] =
                //            [76______]
                //            [10]
                (((((int) in[2 + inPos]) & 255) >>> 6) & 3);
        out[9 + outPos] =
                //            [__54____]
                //              [10]
                (((((int) in[2 + inPos]) & 255) >>> 4) & 3);
        out[10 + outPos] =
                //            [____32__]
                //                [10]
                (((((int) in[2 + inPos]) & 255) >>> 2) & 3);
        out[11 + outPos] =
                //            [______10]
                //                  [10]
                (((((int) in[2 + inPos]) & 255)) & 3);
        out[12 + outPos] =
                //            [76______]
                //            [10]
                (((((int) in[3 + inPos]) & 255) >>> 6) & 3);
        out[13 + outPos] =
                //            [__54____]
                //              [10]
                (((((int) in[3 + inPos]) & 255) >>> 4) & 3);
        out[14 + outPos] =
                //            [____32__]
                //                [10]
                (((((int) in[3 + inPos]) & 255) >>> 2) & 3);
        out[15 + outPos] =
                //            [______10]
                //                  [10]
                (((((int) in[3 + inPos]) & 255)) & 3);
        out[16 + outPos] =
                //            [76______]
                //            [10]
                (((((int) in[4 + inPos]) & 255) >>> 6) & 3);
        out[17 + outPos] =
                //            [__54____]
                //              [10]
                (((((int) in[4 + inPos]) & 255) >>> 4) & 3);
        out[18 + outPos] =
                //            [____32__]
                //                [10]
                (((((int) in[4 + inPos]) & 255) >>> 2) & 3);
        out[19 + outPos] =
                //            [______10]
                //                  [10]
                (((((int) in[4 + inPos]) & 255)) & 3);
        out[20 + outPos] =
                //            [76______]
                //            [10]
                (((((int) in[5 + inPos]) & 255) >>> 6) & 3);
        out[21 + outPos] =
                //            [__54____]
                //              [10]
                (((((int) in[5 + inPos]) & 255) >>> 4) & 3);
        out[22 + outPos] =
                //            [____32__]
                //                [10]
                (((((int) in[5 + inPos]) & 255) >>> 2) & 3);
        out[23 + outPos] =
                //            [______10]
                //                  [10]
                (((((int) in[5 + inPos]) & 255)) & 3);
        out[24 + outPos] =
                //            [76______]
                //            [10]
                (((((int) in[6 + inPos]) & 255) >>> 6) & 3);
        out[25 + outPos] =
                //            [__54____]
                //              [10]
                (((((int) in[6 + inPos]) & 255) >>> 4) & 3);
        out[26 + outPos] =
                //            [____32__]
                //                [10]
                (((((int) in[6 + inPos]) & 255) >>> 2) & 3);
        out[27 + outPos] =
                //            [______10]
                //                  [10]
                (((((int) in[6 + inPos]) & 255)) & 3);
        out[28 + outPos] =
                //            [76______]
                //            [10]
                (((((int) in[7 + inPos]) & 255) >>> 6) & 3);
        out[29 + outPos] =
                //            [__54____]
                //              [10]
                (((((int) in[7 + inPos]) & 255) >>> 4) & 3);
        out[30 + outPos] =
                //            [____32__]
                //                [10]
                (((((int) in[7 + inPos]) & 255) >>> 2) & 3);
        out[31 + outPos] =
                //            [______10]
                //                  [10]
                (((((int) in[7 + inPos]) & 255)) & 3);
    }
}
