package org.sidney.bitpacking.packers.be.int32;

import org.sidney.bitpacking.Int32BytePacker;

public final class Packer7 extends Int32BytePacker {

    public Packer7() {
        super(7);
    }

    public final void pack8Values(final int[] in, final int inPos, final byte[] out, final int outPos) {
        out[0 + outPos] = (byte) ((
                //                 [7654321_]
                //                 [6543210]
                ((in[0 + inPos] & 127) << 1)
                        | //                 [_______0]
                        //                        [6______]
                        ((in[1 + inPos] & 127) >>> 6)) & 255);
        out[1 + outPos] = (byte) ((
                //                 [765432__]
                //                [_543210]
                ((in[1 + inPos] & 127) << 2)
                        | //                 [______10]
                        //                       [65_____]
                        ((in[2 + inPos] & 127) >>> 5)) & 255);
        out[2 + outPos] = (byte) ((
                //                 [76543___]
                //               [__43210]
                ((in[2 + inPos] & 127) << 3)
                        | //                 [_____210]
                        //                      [654____]
                        ((in[3 + inPos] & 127) >>> 4)) & 255);
        out[3 + outPos] = (byte) ((
                //                 [7654____]
                //              [___3210]
                ((in[3 + inPos] & 127) << 4)
                        | //                 [____3210]
                        //                     [6543___]
                        ((in[4 + inPos] & 127) >>> 3)) & 255);
        out[4 + outPos] = (byte) ((
                //                 [765_____]
                //             [____210]
                ((in[4 + inPos] & 127) << 5)
                        | //                 [___43210]
                        //                    [65432__]
                        ((in[5 + inPos] & 127) >>> 2)) & 255);
        out[5 + outPos] = (byte) ((
                //                 [76______]
                //            [_____10]
                ((in[5 + inPos] & 127) << 6)
                        | //                 [__543210]
                        //                   [654321_]
                        ((in[6 + inPos] & 127) >>> 1)) & 255);
        out[6 + outPos] = (byte) ((
                //                 [7_______]
                //           [______0]
                ((in[6 + inPos] & 127) << 7)
                        | //                 [_6543210]
                        //                  [6543210]
                        ((in[7 + inPos] & 127))) & 255);
    }

    public final void pack32Values(final int[] in, final int inPos, final byte[] out, final int outPos) {
        out[0 + outPos] = (byte) ((
                //                 [7654321_]
                //                 [6543210]
                ((in[0 + inPos] & 127) << 1)
                        | //                 [_______0]
                        //                        [6______]
                        ((in[1 + inPos] & 127) >>> 6)) & 255);
        out[1 + outPos] = (byte) ((
                //                 [765432__]
                //                [_543210]
                ((in[1 + inPos] & 127) << 2)
                        | //                 [______10]
                        //                       [65_____]
                        ((in[2 + inPos] & 127) >>> 5)) & 255);
        out[2 + outPos] = (byte) ((
                //                 [76543___]
                //               [__43210]
                ((in[2 + inPos] & 127) << 3)
                        | //                 [_____210]
                        //                      [654____]
                        ((in[3 + inPos] & 127) >>> 4)) & 255);
        out[3 + outPos] = (byte) ((
                //                 [7654____]
                //              [___3210]
                ((in[3 + inPos] & 127) << 4)
                        | //                 [____3210]
                        //                     [6543___]
                        ((in[4 + inPos] & 127) >>> 3)) & 255);
        out[4 + outPos] = (byte) ((
                //                 [765_____]
                //             [____210]
                ((in[4 + inPos] & 127) << 5)
                        | //                 [___43210]
                        //                    [65432__]
                        ((in[5 + inPos] & 127) >>> 2)) & 255);
        out[5 + outPos] = (byte) ((
                //                 [76______]
                //            [_____10]
                ((in[5 + inPos] & 127) << 6)
                        | //                 [__543210]
                        //                   [654321_]
                        ((in[6 + inPos] & 127) >>> 1)) & 255);
        out[6 + outPos] = (byte) ((
                //                 [7_______]
                //           [______0]
                ((in[6 + inPos] & 127) << 7)
                        | //                 [_6543210]
                        //                  [6543210]
                        ((in[7 + inPos] & 127))) & 255);
        out[7 + outPos] = (byte) ((
                //                 [7654321_]
                //                 [6543210]
                ((in[8 + inPos] & 127) << 1)
                        | //                 [_______0]
                        //                        [6______]
                        ((in[9 + inPos] & 127) >>> 6)) & 255);
        out[8 + outPos] = (byte) ((
                //                 [765432__]
                //                [_543210]
                ((in[9 + inPos] & 127) << 2)
                        | //                 [______10]
                        //                       [65_____]
                        ((in[10 + inPos] & 127) >>> 5)) & 255);
        out[9 + outPos] = (byte) ((
                //                 [76543___]
                //               [__43210]
                ((in[10 + inPos] & 127) << 3)
                        | //                 [_____210]
                        //                      [654____]
                        ((in[11 + inPos] & 127) >>> 4)) & 255);
        out[10 + outPos] = (byte) ((
                //                 [7654____]
                //              [___3210]
                ((in[11 + inPos] & 127) << 4)
                        | //                 [____3210]
                        //                     [6543___]
                        ((in[12 + inPos] & 127) >>> 3)) & 255);
        out[11 + outPos] = (byte) ((
                //                 [765_____]
                //             [____210]
                ((in[12 + inPos] & 127) << 5)
                        | //                 [___43210]
                        //                    [65432__]
                        ((in[13 + inPos] & 127) >>> 2)) & 255);
        out[12 + outPos] = (byte) ((
                //                 [76______]
                //            [_____10]
                ((in[13 + inPos] & 127) << 6)
                        | //                 [__543210]
                        //                   [654321_]
                        ((in[14 + inPos] & 127) >>> 1)) & 255);
        out[13 + outPos] = (byte) ((
                //                 [7_______]
                //           [______0]
                ((in[14 + inPos] & 127) << 7)
                        | //                 [_6543210]
                        //                  [6543210]
                        ((in[15 + inPos] & 127))) & 255);
        out[14 + outPos] = (byte) ((
                //                 [7654321_]
                //                 [6543210]
                ((in[16 + inPos] & 127) << 1)
                        | //                 [_______0]
                        //                        [6______]
                        ((in[17 + inPos] & 127) >>> 6)) & 255);
        out[15 + outPos] = (byte) ((
                //                 [765432__]
                //                [_543210]
                ((in[17 + inPos] & 127) << 2)
                        | //                 [______10]
                        //                       [65_____]
                        ((in[18 + inPos] & 127) >>> 5)) & 255);
        out[16 + outPos] = (byte) ((
                //                 [76543___]
                //               [__43210]
                ((in[18 + inPos] & 127) << 3)
                        | //                 [_____210]
                        //                      [654____]
                        ((in[19 + inPos] & 127) >>> 4)) & 255);
        out[17 + outPos] = (byte) ((
                //                 [7654____]
                //              [___3210]
                ((in[19 + inPos] & 127) << 4)
                        | //                 [____3210]
                        //                     [6543___]
                        ((in[20 + inPos] & 127) >>> 3)) & 255);
        out[18 + outPos] = (byte) ((
                //                 [765_____]
                //             [____210]
                ((in[20 + inPos] & 127) << 5)
                        | //                 [___43210]
                        //                    [65432__]
                        ((in[21 + inPos] & 127) >>> 2)) & 255);
        out[19 + outPos] = (byte) ((
                //                 [76______]
                //            [_____10]
                ((in[21 + inPos] & 127) << 6)
                        | //                 [__543210]
                        //                   [654321_]
                        ((in[22 + inPos] & 127) >>> 1)) & 255);
        out[20 + outPos] = (byte) ((
                //                 [7_______]
                //           [______0]
                ((in[22 + inPos] & 127) << 7)
                        | //                 [_6543210]
                        //                  [6543210]
                        ((in[23 + inPos] & 127))) & 255);
        out[21 + outPos] = (byte) ((
                //                 [7654321_]
                //                 [6543210]
                ((in[24 + inPos] & 127) << 1)
                        | //                 [_______0]
                        //                        [6______]
                        ((in[25 + inPos] & 127) >>> 6)) & 255);
        out[22 + outPos] = (byte) ((
                //                 [765432__]
                //                [_543210]
                ((in[25 + inPos] & 127) << 2)
                        | //                 [______10]
                        //                       [65_____]
                        ((in[26 + inPos] & 127) >>> 5)) & 255);
        out[23 + outPos] = (byte) ((
                //                 [76543___]
                //               [__43210]
                ((in[26 + inPos] & 127) << 3)
                        | //                 [_____210]
                        //                      [654____]
                        ((in[27 + inPos] & 127) >>> 4)) & 255);
        out[24 + outPos] = (byte) ((
                //                 [7654____]
                //              [___3210]
                ((in[27 + inPos] & 127) << 4)
                        | //                 [____3210]
                        //                     [6543___]
                        ((in[28 + inPos] & 127) >>> 3)) & 255);
        out[25 + outPos] = (byte) ((
                //                 [765_____]
                //             [____210]
                ((in[28 + inPos] & 127) << 5)
                        | //                 [___43210]
                        //                    [65432__]
                        ((in[29 + inPos] & 127) >>> 2)) & 255);
        out[26 + outPos] = (byte) ((
                //                 [76______]
                //            [_____10]
                ((in[29 + inPos] & 127) << 6)
                        | //                 [__543210]
                        //                   [654321_]
                        ((in[30 + inPos] & 127) >>> 1)) & 255);
        out[27 + outPos] = (byte) ((
                //                 [7_______]
                //           [______0]
                ((in[30 + inPos] & 127) << 7)
                        | //                 [_6543210]
                        //                  [6543210]
                        ((in[31 + inPos] & 127))) & 255);
    }

    public final void unpack8Values(final byte[] in, final int inPos, final int[] out, final int outPos) {
        out[0 + outPos] =
                //                 [7654321_]
                //                 [6543210]
                (((((int) in[0 + inPos]) & 255) >>> 1) & 127);
        out[1 + outPos] =
                //                 [_______0]
                //                        [6______]
                (((((int) in[0 + inPos]) & 255) << 6) & 127)
                        | //                 [765432__]
                        //                [_543210]
                        (((((int) in[1 + inPos]) & 255) >>> 2) & 127);
        out[2 + outPos] =
                //                 [______10]
                //                       [65_____]
                (((((int) in[1 + inPos]) & 255) << 5) & 127)
                        | //                 [76543___]
                        //               [__43210]
                        (((((int) in[2 + inPos]) & 255) >>> 3) & 127);
        out[3 + outPos] =
                //                 [_____210]
                //                      [654____]
                (((((int) in[2 + inPos]) & 255) << 4) & 127)
                        | //                 [7654____]
                        //              [___3210]
                        (((((int) in[3 + inPos]) & 255) >>> 4) & 127);
        out[4 + outPos] =
                //                 [____3210]
                //                     [6543___]
                (((((int) in[3 + inPos]) & 255) << 3) & 127)
                        | //                 [765_____]
                        //             [____210]
                        (((((int) in[4 + inPos]) & 255) >>> 5) & 127);
        out[5 + outPos] =
                //                 [___43210]
                //                    [65432__]
                (((((int) in[4 + inPos]) & 255) << 2) & 127)
                        | //                 [76______]
                        //            [_____10]
                        (((((int) in[5 + inPos]) & 255) >>> 6) & 127);
        out[6 + outPos] =
                //                 [__543210]
                //                   [654321_]
                (((((int) in[5 + inPos]) & 255) << 1) & 127)
                        | //                 [7_______]
                        //           [______0]
                        (((((int) in[6 + inPos]) & 255) >>> 7) & 127);
        out[7 + outPos] =
                //                 [_6543210]
                //                  [6543210]
                (((((int) in[6 + inPos]) & 255)) & 127);
    }

    public final void unpack32Values(final byte[] in, final int inPos, final int[] out, final int outPos) {
        out[0 + outPos] =
                //                 [7654321_]
                //                 [6543210]
                (((((int) in[0 + inPos]) & 255) >>> 1) & 127);
        out[1 + outPos] =
                //                 [_______0]
                //                        [6______]
                (((((int) in[0 + inPos]) & 255) << 6) & 127)
                        | //                 [765432__]
                        //                [_543210]
                        (((((int) in[1 + inPos]) & 255) >>> 2) & 127);
        out[2 + outPos] =
                //                 [______10]
                //                       [65_____]
                (((((int) in[1 + inPos]) & 255) << 5) & 127)
                        | //                 [76543___]
                        //               [__43210]
                        (((((int) in[2 + inPos]) & 255) >>> 3) & 127);
        out[3 + outPos] =
                //                 [_____210]
                //                      [654____]
                (((((int) in[2 + inPos]) & 255) << 4) & 127)
                        | //                 [7654____]
                        //              [___3210]
                        (((((int) in[3 + inPos]) & 255) >>> 4) & 127);
        out[4 + outPos] =
                //                 [____3210]
                //                     [6543___]
                (((((int) in[3 + inPos]) & 255) << 3) & 127)
                        | //                 [765_____]
                        //             [____210]
                        (((((int) in[4 + inPos]) & 255) >>> 5) & 127);
        out[5 + outPos] =
                //                 [___43210]
                //                    [65432__]
                (((((int) in[4 + inPos]) & 255) << 2) & 127)
                        | //                 [76______]
                        //            [_____10]
                        (((((int) in[5 + inPos]) & 255) >>> 6) & 127);
        out[6 + outPos] =
                //                 [__543210]
                //                   [654321_]
                (((((int) in[5 + inPos]) & 255) << 1) & 127)
                        | //                 [7_______]
                        //           [______0]
                        (((((int) in[6 + inPos]) & 255) >>> 7) & 127);
        out[7 + outPos] =
                //                 [_6543210]
                //                  [6543210]
                (((((int) in[6 + inPos]) & 255)) & 127);
        out[8 + outPos] =
                //                 [7654321_]
                //                 [6543210]
                (((((int) in[7 + inPos]) & 255) >>> 1) & 127);
        out[9 + outPos] =
                //                 [_______0]
                //                        [6______]
                (((((int) in[7 + inPos]) & 255) << 6) & 127)
                        | //                 [765432__]
                        //                [_543210]
                        (((((int) in[8 + inPos]) & 255) >>> 2) & 127);
        out[10 + outPos] =
                //                 [______10]
                //                       [65_____]
                (((((int) in[8 + inPos]) & 255) << 5) & 127)
                        | //                 [76543___]
                        //               [__43210]
                        (((((int) in[9 + inPos]) & 255) >>> 3) & 127);
        out[11 + outPos] =
                //                 [_____210]
                //                      [654____]
                (((((int) in[9 + inPos]) & 255) << 4) & 127)
                        | //                 [7654____]
                        //              [___3210]
                        (((((int) in[10 + inPos]) & 255) >>> 4) & 127);
        out[12 + outPos] =
                //                 [____3210]
                //                     [6543___]
                (((((int) in[10 + inPos]) & 255) << 3) & 127)
                        | //                 [765_____]
                        //             [____210]
                        (((((int) in[11 + inPos]) & 255) >>> 5) & 127);
        out[13 + outPos] =
                //                 [___43210]
                //                    [65432__]
                (((((int) in[11 + inPos]) & 255) << 2) & 127)
                        | //                 [76______]
                        //            [_____10]
                        (((((int) in[12 + inPos]) & 255) >>> 6) & 127);
        out[14 + outPos] =
                //                 [__543210]
                //                   [654321_]
                (((((int) in[12 + inPos]) & 255) << 1) & 127)
                        | //                 [7_______]
                        //           [______0]
                        (((((int) in[13 + inPos]) & 255) >>> 7) & 127);
        out[15 + outPos] =
                //                 [_6543210]
                //                  [6543210]
                (((((int) in[13 + inPos]) & 255)) & 127);
        out[16 + outPos] =
                //                 [7654321_]
                //                 [6543210]
                (((((int) in[14 + inPos]) & 255) >>> 1) & 127);
        out[17 + outPos] =
                //                 [_______0]
                //                        [6______]
                (((((int) in[14 + inPos]) & 255) << 6) & 127)
                        | //                 [765432__]
                        //                [_543210]
                        (((((int) in[15 + inPos]) & 255) >>> 2) & 127);
        out[18 + outPos] =
                //                 [______10]
                //                       [65_____]
                (((((int) in[15 + inPos]) & 255) << 5) & 127)
                        | //                 [76543___]
                        //               [__43210]
                        (((((int) in[16 + inPos]) & 255) >>> 3) & 127);
        out[19 + outPos] =
                //                 [_____210]
                //                      [654____]
                (((((int) in[16 + inPos]) & 255) << 4) & 127)
                        | //                 [7654____]
                        //              [___3210]
                        (((((int) in[17 + inPos]) & 255) >>> 4) & 127);
        out[20 + outPos] =
                //                 [____3210]
                //                     [6543___]
                (((((int) in[17 + inPos]) & 255) << 3) & 127)
                        | //                 [765_____]
                        //             [____210]
                        (((((int) in[18 + inPos]) & 255) >>> 5) & 127);
        out[21 + outPos] =
                //                 [___43210]
                //                    [65432__]
                (((((int) in[18 + inPos]) & 255) << 2) & 127)
                        | //                 [76______]
                        //            [_____10]
                        (((((int) in[19 + inPos]) & 255) >>> 6) & 127);
        out[22 + outPos] =
                //                 [__543210]
                //                   [654321_]
                (((((int) in[19 + inPos]) & 255) << 1) & 127)
                        | //                 [7_______]
                        //           [______0]
                        (((((int) in[20 + inPos]) & 255) >>> 7) & 127);
        out[23 + outPos] =
                //                 [_6543210]
                //                  [6543210]
                (((((int) in[20 + inPos]) & 255)) & 127);
        out[24 + outPos] =
                //                 [7654321_]
                //                 [6543210]
                (((((int) in[21 + inPos]) & 255) >>> 1) & 127);
        out[25 + outPos] =
                //                 [_______0]
                //                        [6______]
                (((((int) in[21 + inPos]) & 255) << 6) & 127)
                        | //                 [765432__]
                        //                [_543210]
                        (((((int) in[22 + inPos]) & 255) >>> 2) & 127);
        out[26 + outPos] =
                //                 [______10]
                //                       [65_____]
                (((((int) in[22 + inPos]) & 255) << 5) & 127)
                        | //                 [76543___]
                        //               [__43210]
                        (((((int) in[23 + inPos]) & 255) >>> 3) & 127);
        out[27 + outPos] =
                //                 [_____210]
                //                      [654____]
                (((((int) in[23 + inPos]) & 255) << 4) & 127)
                        | //                 [7654____]
                        //              [___3210]
                        (((((int) in[24 + inPos]) & 255) >>> 4) & 127);
        out[28 + outPos] =
                //                 [____3210]
                //                     [6543___]
                (((((int) in[24 + inPos]) & 255) << 3) & 127)
                        | //                 [765_____]
                        //             [____210]
                        (((((int) in[25 + inPos]) & 255) >>> 5) & 127);
        out[29 + outPos] =
                //                 [___43210]
                //                    [65432__]
                (((((int) in[25 + inPos]) & 255) << 2) & 127)
                        | //                 [76______]
                        //            [_____10]
                        (((((int) in[26 + inPos]) & 255) >>> 6) & 127);
        out[30 + outPos] =
                //                 [__543210]
                //                   [654321_]
                (((((int) in[26 + inPos]) & 255) << 1) & 127)
                        | //                 [7_______]
                        //           [______0]
                        (((((int) in[27 + inPos]) & 255) >>> 7) & 127);
        out[31 + outPos] =
                //                 [_6543210]
                //                  [6543210]
                (((((int) in[27 + inPos]) & 255)) & 127);
    }
}
