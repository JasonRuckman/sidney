package com.github.jasonruckman.sidney.bitpacking.packers.le.int32;

import com.github.jasonruckman.sidney.bitpacking.Int32BytePacker;

public final class Packer7 extends Int32BytePacker {

    public Packer7() {
        super(7);
    }

    public final void pack8Values(final int[] in, final int inPos, final byte[] out, final int outPos) {
        out[0 + outPos] = (byte) ((
                //                 [_6543210]
                //                  [6543210]
                ((in[0 + inPos] & 127))
                        | //                 [7_______]
                        //           [______0]
                        ((in[1 + inPos] & 127) << 7)) & 255);
        out[1 + outPos] = (byte) ((
                //                 [__543210]
                //                   [654321_]
                ((in[1 + inPos] & 127) >>> 1)
                        | //                 [76______]
                        //            [_____10]
                        ((in[2 + inPos] & 127) << 6)) & 255);
        out[2 + outPos] = (byte) ((
                //                 [___43210]
                //                    [65432__]
                ((in[2 + inPos] & 127) >>> 2)
                        | //                 [765_____]
                        //             [____210]
                        ((in[3 + inPos] & 127) << 5)) & 255);
        out[3 + outPos] = (byte) ((
                //                 [____3210]
                //                     [6543___]
                ((in[3 + inPos] & 127) >>> 3)
                        | //                 [7654____]
                        //              [___3210]
                        ((in[4 + inPos] & 127) << 4)) & 255);
        out[4 + outPos] = (byte) ((
                //                 [_____210]
                //                      [654____]
                ((in[4 + inPos] & 127) >>> 4)
                        | //                 [76543___]
                        //               [__43210]
                        ((in[5 + inPos] & 127) << 3)) & 255);
        out[5 + outPos] = (byte) ((
                //                 [______10]
                //                       [65_____]
                ((in[5 + inPos] & 127) >>> 5)
                        | //                 [765432__]
                        //                [_543210]
                        ((in[6 + inPos] & 127) << 2)) & 255);
        out[6 + outPos] = (byte) ((
                //                 [_______0]
                //                        [6______]
                ((in[6 + inPos] & 127) >>> 6)
                        | //                 [7654321_]
                        //                 [6543210]
                        ((in[7 + inPos] & 127) << 1)) & 255);
    }

    public final void pack32Values(final int[] in, final int inPos, final byte[] out, final int outPos) {
        out[0 + outPos] = (byte) ((
                //                 [_6543210]
                //                  [6543210]
                ((in[0 + inPos] & 127))
                        | //                 [7_______]
                        //           [______0]
                        ((in[1 + inPos] & 127) << 7)) & 255);
        out[1 + outPos] = (byte) ((
                //                 [__543210]
                //                   [654321_]
                ((in[1 + inPos] & 127) >>> 1)
                        | //                 [76______]
                        //            [_____10]
                        ((in[2 + inPos] & 127) << 6)) & 255);
        out[2 + outPos] = (byte) ((
                //                 [___43210]
                //                    [65432__]
                ((in[2 + inPos] & 127) >>> 2)
                        | //                 [765_____]
                        //             [____210]
                        ((in[3 + inPos] & 127) << 5)) & 255);
        out[3 + outPos] = (byte) ((
                //                 [____3210]
                //                     [6543___]
                ((in[3 + inPos] & 127) >>> 3)
                        | //                 [7654____]
                        //              [___3210]
                        ((in[4 + inPos] & 127) << 4)) & 255);
        out[4 + outPos] = (byte) ((
                //                 [_____210]
                //                      [654____]
                ((in[4 + inPos] & 127) >>> 4)
                        | //                 [76543___]
                        //               [__43210]
                        ((in[5 + inPos] & 127) << 3)) & 255);
        out[5 + outPos] = (byte) ((
                //                 [______10]
                //                       [65_____]
                ((in[5 + inPos] & 127) >>> 5)
                        | //                 [765432__]
                        //                [_543210]
                        ((in[6 + inPos] & 127) << 2)) & 255);
        out[6 + outPos] = (byte) ((
                //                 [_______0]
                //                        [6______]
                ((in[6 + inPos] & 127) >>> 6)
                        | //                 [7654321_]
                        //                 [6543210]
                        ((in[7 + inPos] & 127) << 1)) & 255);
        out[7 + outPos] = (byte) ((
                //                 [_6543210]
                //                  [6543210]
                ((in[8 + inPos] & 127))
                        | //                 [7_______]
                        //           [______0]
                        ((in[9 + inPos] & 127) << 7)) & 255);
        out[8 + outPos] = (byte) ((
                //                 [__543210]
                //                   [654321_]
                ((in[9 + inPos] & 127) >>> 1)
                        | //                 [76______]
                        //            [_____10]
                        ((in[10 + inPos] & 127) << 6)) & 255);
        out[9 + outPos] = (byte) ((
                //                 [___43210]
                //                    [65432__]
                ((in[10 + inPos] & 127) >>> 2)
                        | //                 [765_____]
                        //             [____210]
                        ((in[11 + inPos] & 127) << 5)) & 255);
        out[10 + outPos] = (byte) ((
                //                 [____3210]
                //                     [6543___]
                ((in[11 + inPos] & 127) >>> 3)
                        | //                 [7654____]
                        //              [___3210]
                        ((in[12 + inPos] & 127) << 4)) & 255);
        out[11 + outPos] = (byte) ((
                //                 [_____210]
                //                      [654____]
                ((in[12 + inPos] & 127) >>> 4)
                        | //                 [76543___]
                        //               [__43210]
                        ((in[13 + inPos] & 127) << 3)) & 255);
        out[12 + outPos] = (byte) ((
                //                 [______10]
                //                       [65_____]
                ((in[13 + inPos] & 127) >>> 5)
                        | //                 [765432__]
                        //                [_543210]
                        ((in[14 + inPos] & 127) << 2)) & 255);
        out[13 + outPos] = (byte) ((
                //                 [_______0]
                //                        [6______]
                ((in[14 + inPos] & 127) >>> 6)
                        | //                 [7654321_]
                        //                 [6543210]
                        ((in[15 + inPos] & 127) << 1)) & 255);
        out[14 + outPos] = (byte) ((
                //                 [_6543210]
                //                  [6543210]
                ((in[16 + inPos] & 127))
                        | //                 [7_______]
                        //           [______0]
                        ((in[17 + inPos] & 127) << 7)) & 255);
        out[15 + outPos] = (byte) ((
                //                 [__543210]
                //                   [654321_]
                ((in[17 + inPos] & 127) >>> 1)
                        | //                 [76______]
                        //            [_____10]
                        ((in[18 + inPos] & 127) << 6)) & 255);
        out[16 + outPos] = (byte) ((
                //                 [___43210]
                //                    [65432__]
                ((in[18 + inPos] & 127) >>> 2)
                        | //                 [765_____]
                        //             [____210]
                        ((in[19 + inPos] & 127) << 5)) & 255);
        out[17 + outPos] = (byte) ((
                //                 [____3210]
                //                     [6543___]
                ((in[19 + inPos] & 127) >>> 3)
                        | //                 [7654____]
                        //              [___3210]
                        ((in[20 + inPos] & 127) << 4)) & 255);
        out[18 + outPos] = (byte) ((
                //                 [_____210]
                //                      [654____]
                ((in[20 + inPos] & 127) >>> 4)
                        | //                 [76543___]
                        //               [__43210]
                        ((in[21 + inPos] & 127) << 3)) & 255);
        out[19 + outPos] = (byte) ((
                //                 [______10]
                //                       [65_____]
                ((in[21 + inPos] & 127) >>> 5)
                        | //                 [765432__]
                        //                [_543210]
                        ((in[22 + inPos] & 127) << 2)) & 255);
        out[20 + outPos] = (byte) ((
                //                 [_______0]
                //                        [6______]
                ((in[22 + inPos] & 127) >>> 6)
                        | //                 [7654321_]
                        //                 [6543210]
                        ((in[23 + inPos] & 127) << 1)) & 255);
        out[21 + outPos] = (byte) ((
                //                 [_6543210]
                //                  [6543210]
                ((in[24 + inPos] & 127))
                        | //                 [7_______]
                        //           [______0]
                        ((in[25 + inPos] & 127) << 7)) & 255);
        out[22 + outPos] = (byte) ((
                //                 [__543210]
                //                   [654321_]
                ((in[25 + inPos] & 127) >>> 1)
                        | //                 [76______]
                        //            [_____10]
                        ((in[26 + inPos] & 127) << 6)) & 255);
        out[23 + outPos] = (byte) ((
                //                 [___43210]
                //                    [65432__]
                ((in[26 + inPos] & 127) >>> 2)
                        | //                 [765_____]
                        //             [____210]
                        ((in[27 + inPos] & 127) << 5)) & 255);
        out[24 + outPos] = (byte) ((
                //                 [____3210]
                //                     [6543___]
                ((in[27 + inPos] & 127) >>> 3)
                        | //                 [7654____]
                        //              [___3210]
                        ((in[28 + inPos] & 127) << 4)) & 255);
        out[25 + outPos] = (byte) ((
                //                 [_____210]
                //                      [654____]
                ((in[28 + inPos] & 127) >>> 4)
                        | //                 [76543___]
                        //               [__43210]
                        ((in[29 + inPos] & 127) << 3)) & 255);
        out[26 + outPos] = (byte) ((
                //                 [______10]
                //                       [65_____]
                ((in[29 + inPos] & 127) >>> 5)
                        | //                 [765432__]
                        //                [_543210]
                        ((in[30 + inPos] & 127) << 2)) & 255);
        out[27 + outPos] = (byte) ((
                //                 [_______0]
                //                        [6______]
                ((in[30 + inPos] & 127) >>> 6)
                        | //                 [7654321_]
                        //                 [6543210]
                        ((in[31 + inPos] & 127) << 1)) & 255);
    }

    public final void unpack8Values(final byte[] in, final int inPos, final int[] out, final int outPos) {
        out[0 + outPos] =
                //                 [_6543210]
                //                  [6543210]
                (((((int) in[0 + inPos]) & 255)) & 127);
        out[1 + outPos] =
                //                 [7_______]
                //           [______0]
                (((((int) in[0 + inPos]) & 255) >>> 7) & 127)
                        | //                 [__543210]
                        //                   [654321_]
                        (((((int) in[1 + inPos]) & 255) << 1) & 127);
        out[2 + outPos] =
                //                 [76______]
                //            [_____10]
                (((((int) in[1 + inPos]) & 255) >>> 6) & 127)
                        | //                 [___43210]
                        //                    [65432__]
                        (((((int) in[2 + inPos]) & 255) << 2) & 127);
        out[3 + outPos] =
                //                 [765_____]
                //             [____210]
                (((((int) in[2 + inPos]) & 255) >>> 5) & 127)
                        | //                 [____3210]
                        //                     [6543___]
                        (((((int) in[3 + inPos]) & 255) << 3) & 127);
        out[4 + outPos] =
                //                 [7654____]
                //              [___3210]
                (((((int) in[3 + inPos]) & 255) >>> 4) & 127)
                        | //                 [_____210]
                        //                      [654____]
                        (((((int) in[4 + inPos]) & 255) << 4) & 127);
        out[5 + outPos] =
                //                 [76543___]
                //               [__43210]
                (((((int) in[4 + inPos]) & 255) >>> 3) & 127)
                        | //                 [______10]
                        //                       [65_____]
                        (((((int) in[5 + inPos]) & 255) << 5) & 127);
        out[6 + outPos] =
                //                 [765432__]
                //                [_543210]
                (((((int) in[5 + inPos]) & 255) >>> 2) & 127)
                        | //                 [_______0]
                        //                        [6______]
                        (((((int) in[6 + inPos]) & 255) << 6) & 127);
        out[7 + outPos] =
                //                 [7654321_]
                //                 [6543210]
                (((((int) in[6 + inPos]) & 255) >>> 1) & 127);
    }

    public final void unpack32Values(final byte[] in, final int inPos, final int[] out, final int outPos) {
        out[0 + outPos] =
                //                 [_6543210]
                //                  [6543210]
                (((((int) in[0 + inPos]) & 255)) & 127);
        out[1 + outPos] =
                //                 [7_______]
                //           [______0]
                (((((int) in[0 + inPos]) & 255) >>> 7) & 127)
                        | //                 [__543210]
                        //                   [654321_]
                        (((((int) in[1 + inPos]) & 255) << 1) & 127);
        out[2 + outPos] =
                //                 [76______]
                //            [_____10]
                (((((int) in[1 + inPos]) & 255) >>> 6) & 127)
                        | //                 [___43210]
                        //                    [65432__]
                        (((((int) in[2 + inPos]) & 255) << 2) & 127);
        out[3 + outPos] =
                //                 [765_____]
                //             [____210]
                (((((int) in[2 + inPos]) & 255) >>> 5) & 127)
                        | //                 [____3210]
                        //                     [6543___]
                        (((((int) in[3 + inPos]) & 255) << 3) & 127);
        out[4 + outPos] =
                //                 [7654____]
                //              [___3210]
                (((((int) in[3 + inPos]) & 255) >>> 4) & 127)
                        | //                 [_____210]
                        //                      [654____]
                        (((((int) in[4 + inPos]) & 255) << 4) & 127);
        out[5 + outPos] =
                //                 [76543___]
                //               [__43210]
                (((((int) in[4 + inPos]) & 255) >>> 3) & 127)
                        | //                 [______10]
                        //                       [65_____]
                        (((((int) in[5 + inPos]) & 255) << 5) & 127);
        out[6 + outPos] =
                //                 [765432__]
                //                [_543210]
                (((((int) in[5 + inPos]) & 255) >>> 2) & 127)
                        | //                 [_______0]
                        //                        [6______]
                        (((((int) in[6 + inPos]) & 255) << 6) & 127);
        out[7 + outPos] =
                //                 [7654321_]
                //                 [6543210]
                (((((int) in[6 + inPos]) & 255) >>> 1) & 127);
        out[8 + outPos] =
                //                 [_6543210]
                //                  [6543210]
                (((((int) in[7 + inPos]) & 255)) & 127);
        out[9 + outPos] =
                //                 [7_______]
                //           [______0]
                (((((int) in[7 + inPos]) & 255) >>> 7) & 127)
                        | //                 [__543210]
                        //                   [654321_]
                        (((((int) in[8 + inPos]) & 255) << 1) & 127);
        out[10 + outPos] =
                //                 [76______]
                //            [_____10]
                (((((int) in[8 + inPos]) & 255) >>> 6) & 127)
                        | //                 [___43210]
                        //                    [65432__]
                        (((((int) in[9 + inPos]) & 255) << 2) & 127);
        out[11 + outPos] =
                //                 [765_____]
                //             [____210]
                (((((int) in[9 + inPos]) & 255) >>> 5) & 127)
                        | //                 [____3210]
                        //                     [6543___]
                        (((((int) in[10 + inPos]) & 255) << 3) & 127);
        out[12 + outPos] =
                //                 [7654____]
                //              [___3210]
                (((((int) in[10 + inPos]) & 255) >>> 4) & 127)
                        | //                 [_____210]
                        //                      [654____]
                        (((((int) in[11 + inPos]) & 255) << 4) & 127);
        out[13 + outPos] =
                //                 [76543___]
                //               [__43210]
                (((((int) in[11 + inPos]) & 255) >>> 3) & 127)
                        | //                 [______10]
                        //                       [65_____]
                        (((((int) in[12 + inPos]) & 255) << 5) & 127);
        out[14 + outPos] =
                //                 [765432__]
                //                [_543210]
                (((((int) in[12 + inPos]) & 255) >>> 2) & 127)
                        | //                 [_______0]
                        //                        [6______]
                        (((((int) in[13 + inPos]) & 255) << 6) & 127);
        out[15 + outPos] =
                //                 [7654321_]
                //                 [6543210]
                (((((int) in[13 + inPos]) & 255) >>> 1) & 127);
        out[16 + outPos] =
                //                 [_6543210]
                //                  [6543210]
                (((((int) in[14 + inPos]) & 255)) & 127);
        out[17 + outPos] =
                //                 [7_______]
                //           [______0]
                (((((int) in[14 + inPos]) & 255) >>> 7) & 127)
                        | //                 [__543210]
                        //                   [654321_]
                        (((((int) in[15 + inPos]) & 255) << 1) & 127);
        out[18 + outPos] =
                //                 [76______]
                //            [_____10]
                (((((int) in[15 + inPos]) & 255) >>> 6) & 127)
                        | //                 [___43210]
                        //                    [65432__]
                        (((((int) in[16 + inPos]) & 255) << 2) & 127);
        out[19 + outPos] =
                //                 [765_____]
                //             [____210]
                (((((int) in[16 + inPos]) & 255) >>> 5) & 127)
                        | //                 [____3210]
                        //                     [6543___]
                        (((((int) in[17 + inPos]) & 255) << 3) & 127);
        out[20 + outPos] =
                //                 [7654____]
                //              [___3210]
                (((((int) in[17 + inPos]) & 255) >>> 4) & 127)
                        | //                 [_____210]
                        //                      [654____]
                        (((((int) in[18 + inPos]) & 255) << 4) & 127);
        out[21 + outPos] =
                //                 [76543___]
                //               [__43210]
                (((((int) in[18 + inPos]) & 255) >>> 3) & 127)
                        | //                 [______10]
                        //                       [65_____]
                        (((((int) in[19 + inPos]) & 255) << 5) & 127);
        out[22 + outPos] =
                //                 [765432__]
                //                [_543210]
                (((((int) in[19 + inPos]) & 255) >>> 2) & 127)
                        | //                 [_______0]
                        //                        [6______]
                        (((((int) in[20 + inPos]) & 255) << 6) & 127);
        out[23 + outPos] =
                //                 [7654321_]
                //                 [6543210]
                (((((int) in[20 + inPos]) & 255) >>> 1) & 127);
        out[24 + outPos] =
                //                 [_6543210]
                //                  [6543210]
                (((((int) in[21 + inPos]) & 255)) & 127);
        out[25 + outPos] =
                //                 [7_______]
                //           [______0]
                (((((int) in[21 + inPos]) & 255) >>> 7) & 127)
                        | //                 [__543210]
                        //                   [654321_]
                        (((((int) in[22 + inPos]) & 255) << 1) & 127);
        out[26 + outPos] =
                //                 [76______]
                //            [_____10]
                (((((int) in[22 + inPos]) & 255) >>> 6) & 127)
                        | //                 [___43210]
                        //                    [65432__]
                        (((((int) in[23 + inPos]) & 255) << 2) & 127);
        out[27 + outPos] =
                //                 [765_____]
                //             [____210]
                (((((int) in[23 + inPos]) & 255) >>> 5) & 127)
                        | //                 [____3210]
                        //                     [6543___]
                        (((((int) in[24 + inPos]) & 255) << 3) & 127);
        out[28 + outPos] =
                //                 [7654____]
                //              [___3210]
                (((((int) in[24 + inPos]) & 255) >>> 4) & 127)
                        | //                 [_____210]
                        //                      [654____]
                        (((((int) in[25 + inPos]) & 255) << 4) & 127);
        out[29 + outPos] =
                //                 [76543___]
                //               [__43210]
                (((((int) in[25 + inPos]) & 255) >>> 3) & 127)
                        | //                 [______10]
                        //                       [65_____]
                        (((((int) in[26 + inPos]) & 255) << 5) & 127);
        out[30 + outPos] =
                //                 [765432__]
                //                [_543210]
                (((((int) in[26 + inPos]) & 255) >>> 2) & 127)
                        | //                 [_______0]
                        //                        [6______]
                        (((((int) in[27 + inPos]) & 255) << 6) & 127);
        out[31 + outPos] =
                //                 [7654321_]
                //                 [6543210]
                (((((int) in[27 + inPos]) & 255) >>> 1) & 127);
    }
}