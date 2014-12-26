package org.sidney.bitpacking.packers.be.int32;

import org.sidney.bitpacking.Int32BytePacker;

public final class Packer1 extends Int32BytePacker {

    public Packer1() {
        super(1);
    }

    public final void pack8Values(final int[] in, final int inPos, final byte[] out, final int outPos) {
        out[0 + outPos] = (byte) ((
                //           [7_______]
                //           [0]
                ((in[0 + inPos] & 1) << 7)
                        | //           [_6______]
                        //            [0]
                        ((in[1 + inPos] & 1) << 6)
                        | //           [__5_____]
                        //             [0]
                        ((in[2 + inPos] & 1) << 5)
                        | //           [___4____]
                        //              [0]
                        ((in[3 + inPos] & 1) << 4)
                        | //           [____3___]
                        //               [0]
                        ((in[4 + inPos] & 1) << 3)
                        | //           [_____2__]
                        //                [0]
                        ((in[5 + inPos] & 1) << 2)
                        | //           [______1_]
                        //                 [0]
                        ((in[6 + inPos] & 1) << 1)
                        | //           [_______0]
                        //                  [0]
                        ((in[7 + inPos] & 1))) & 255);
    }

    public final void pack32Values(final int[] in, final int inPos, final byte[] out, final int outPos) {
        out[0 + outPos] = (byte) ((
                //           [7_______]
                //           [0]
                ((in[0 + inPos] & 1) << 7)
                        | //           [_6______]
                        //            [0]
                        ((in[1 + inPos] & 1) << 6)
                        | //           [__5_____]
                        //             [0]
                        ((in[2 + inPos] & 1) << 5)
                        | //           [___4____]
                        //              [0]
                        ((in[3 + inPos] & 1) << 4)
                        | //           [____3___]
                        //               [0]
                        ((in[4 + inPos] & 1) << 3)
                        | //           [_____2__]
                        //                [0]
                        ((in[5 + inPos] & 1) << 2)
                        | //           [______1_]
                        //                 [0]
                        ((in[6 + inPos] & 1) << 1)
                        | //           [_______0]
                        //                  [0]
                        ((in[7 + inPos] & 1))) & 255);
        out[1 + outPos] = (byte) ((
                //           [7_______]
                //           [0]
                ((in[8 + inPos] & 1) << 7)
                        | //           [_6______]
                        //            [0]
                        ((in[9 + inPos] & 1) << 6)
                        | //           [__5_____]
                        //             [0]
                        ((in[10 + inPos] & 1) << 5)
                        | //           [___4____]
                        //              [0]
                        ((in[11 + inPos] & 1) << 4)
                        | //           [____3___]
                        //               [0]
                        ((in[12 + inPos] & 1) << 3)
                        | //           [_____2__]
                        //                [0]
                        ((in[13 + inPos] & 1) << 2)
                        | //           [______1_]
                        //                 [0]
                        ((in[14 + inPos] & 1) << 1)
                        | //           [_______0]
                        //                  [0]
                        ((in[15 + inPos] & 1))) & 255);
        out[2 + outPos] = (byte) ((
                //           [7_______]
                //           [0]
                ((in[16 + inPos] & 1) << 7)
                        | //           [_6______]
                        //            [0]
                        ((in[17 + inPos] & 1) << 6)
                        | //           [__5_____]
                        //             [0]
                        ((in[18 + inPos] & 1) << 5)
                        | //           [___4____]
                        //              [0]
                        ((in[19 + inPos] & 1) << 4)
                        | //           [____3___]
                        //               [0]
                        ((in[20 + inPos] & 1) << 3)
                        | //           [_____2__]
                        //                [0]
                        ((in[21 + inPos] & 1) << 2)
                        | //           [______1_]
                        //                 [0]
                        ((in[22 + inPos] & 1) << 1)
                        | //           [_______0]
                        //                  [0]
                        ((in[23 + inPos] & 1))) & 255);
        out[3 + outPos] = (byte) ((
                //           [7_______]
                //           [0]
                ((in[24 + inPos] & 1) << 7)
                        | //           [_6______]
                        //            [0]
                        ((in[25 + inPos] & 1) << 6)
                        | //           [__5_____]
                        //             [0]
                        ((in[26 + inPos] & 1) << 5)
                        | //           [___4____]
                        //              [0]
                        ((in[27 + inPos] & 1) << 4)
                        | //           [____3___]
                        //               [0]
                        ((in[28 + inPos] & 1) << 3)
                        | //           [_____2__]
                        //                [0]
                        ((in[29 + inPos] & 1) << 2)
                        | //           [______1_]
                        //                 [0]
                        ((in[30 + inPos] & 1) << 1)
                        | //           [_______0]
                        //                  [0]
                        ((in[31 + inPos] & 1))) & 255);
    }

    public final void unpack8Values(final byte[] in, final int inPos, final int[] out, final int outPos) {
        out[0 + outPos] =
                //           [7_______]
                //           [0]
                (((((int) in[0 + inPos]) & 255) >>> 7) & 1);
        out[1 + outPos] =
                //           [_6______]
                //            [0]
                (((((int) in[0 + inPos]) & 255) >>> 6) & 1);
        out[2 + outPos] =
                //           [__5_____]
                //             [0]
                (((((int) in[0 + inPos]) & 255) >>> 5) & 1);
        out[3 + outPos] =
                //           [___4____]
                //              [0]
                (((((int) in[0 + inPos]) & 255) >>> 4) & 1);
        out[4 + outPos] =
                //           [____3___]
                //               [0]
                (((((int) in[0 + inPos]) & 255) >>> 3) & 1);
        out[5 + outPos] =
                //           [_____2__]
                //                [0]
                (((((int) in[0 + inPos]) & 255) >>> 2) & 1);
        out[6 + outPos] =
                //           [______1_]
                //                 [0]
                (((((int) in[0 + inPos]) & 255) >>> 1) & 1);
        out[7 + outPos] =
                //           [_______0]
                //                  [0]
                (((((int) in[0 + inPos]) & 255)) & 1);
    }

    public final void unpack32Values(final byte[] in, final int inPos, final int[] out, final int outPos) {
        out[0 + outPos] =
                //           [7_______]
                //           [0]
                (((((int) in[0 + inPos]) & 255) >>> 7) & 1);
        out[1 + outPos] =
                //           [_6______]
                //            [0]
                (((((int) in[0 + inPos]) & 255) >>> 6) & 1);
        out[2 + outPos] =
                //           [__5_____]
                //             [0]
                (((((int) in[0 + inPos]) & 255) >>> 5) & 1);
        out[3 + outPos] =
                //           [___4____]
                //              [0]
                (((((int) in[0 + inPos]) & 255) >>> 4) & 1);
        out[4 + outPos] =
                //           [____3___]
                //               [0]
                (((((int) in[0 + inPos]) & 255) >>> 3) & 1);
        out[5 + outPos] =
                //           [_____2__]
                //                [0]
                (((((int) in[0 + inPos]) & 255) >>> 2) & 1);
        out[6 + outPos] =
                //           [______1_]
                //                 [0]
                (((((int) in[0 + inPos]) & 255) >>> 1) & 1);
        out[7 + outPos] =
                //           [_______0]
                //                  [0]
                (((((int) in[0 + inPos]) & 255)) & 1);
        out[8 + outPos] =
                //           [7_______]
                //           [0]
                (((((int) in[1 + inPos]) & 255) >>> 7) & 1);
        out[9 + outPos] =
                //           [_6______]
                //            [0]
                (((((int) in[1 + inPos]) & 255) >>> 6) & 1);
        out[10 + outPos] =
                //           [__5_____]
                //             [0]
                (((((int) in[1 + inPos]) & 255) >>> 5) & 1);
        out[11 + outPos] =
                //           [___4____]
                //              [0]
                (((((int) in[1 + inPos]) & 255) >>> 4) & 1);
        out[12 + outPos] =
                //           [____3___]
                //               [0]
                (((((int) in[1 + inPos]) & 255) >>> 3) & 1);
        out[13 + outPos] =
                //           [_____2__]
                //                [0]
                (((((int) in[1 + inPos]) & 255) >>> 2) & 1);
        out[14 + outPos] =
                //           [______1_]
                //                 [0]
                (((((int) in[1 + inPos]) & 255) >>> 1) & 1);
        out[15 + outPos] =
                //           [_______0]
                //                  [0]
                (((((int) in[1 + inPos]) & 255)) & 1);
        out[16 + outPos] =
                //           [7_______]
                //           [0]
                (((((int) in[2 + inPos]) & 255) >>> 7) & 1);
        out[17 + outPos] =
                //           [_6______]
                //            [0]
                (((((int) in[2 + inPos]) & 255) >>> 6) & 1);
        out[18 + outPos] =
                //           [__5_____]
                //             [0]
                (((((int) in[2 + inPos]) & 255) >>> 5) & 1);
        out[19 + outPos] =
                //           [___4____]
                //              [0]
                (((((int) in[2 + inPos]) & 255) >>> 4) & 1);
        out[20 + outPos] =
                //           [____3___]
                //               [0]
                (((((int) in[2 + inPos]) & 255) >>> 3) & 1);
        out[21 + outPos] =
                //           [_____2__]
                //                [0]
                (((((int) in[2 + inPos]) & 255) >>> 2) & 1);
        out[22 + outPos] =
                //           [______1_]
                //                 [0]
                (((((int) in[2 + inPos]) & 255) >>> 1) & 1);
        out[23 + outPos] =
                //           [_______0]
                //                  [0]
                (((((int) in[2 + inPos]) & 255)) & 1);
        out[24 + outPos] =
                //           [7_______]
                //           [0]
                (((((int) in[3 + inPos]) & 255) >>> 7) & 1);
        out[25 + outPos] =
                //           [_6______]
                //            [0]
                (((((int) in[3 + inPos]) & 255) >>> 6) & 1);
        out[26 + outPos] =
                //           [__5_____]
                //             [0]
                (((((int) in[3 + inPos]) & 255) >>> 5) & 1);
        out[27 + outPos] =
                //           [___4____]
                //              [0]
                (((((int) in[3 + inPos]) & 255) >>> 4) & 1);
        out[28 + outPos] =
                //           [____3___]
                //               [0]
                (((((int) in[3 + inPos]) & 255) >>> 3) & 1);
        out[29 + outPos] =
                //           [_____2__]
                //                [0]
                (((((int) in[3 + inPos]) & 255) >>> 2) & 1);
        out[30 + outPos] =
                //           [______1_]
                //                 [0]
                (((((int) in[3 + inPos]) & 255) >>> 1) & 1);
        out[31 + outPos] =
                //           [_______0]
                //                  [0]
                (((((int) in[3 + inPos]) & 255)) & 1);
    }
}
