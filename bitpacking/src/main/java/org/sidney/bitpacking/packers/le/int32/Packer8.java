package org.sidney.bitpacking.packers.le.int32;

import org.sidney.bitpacking.Int32BytePacker;
public final class Packer8 extends Int32BytePacker {

    public Packer8() {
      super(8);
    }

    public final void pack8Values(final int[] in, final int inPos, final byte[] out, final int outPos) {
      out[ 0 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[ 0 + inPos] & 255))) & 255);
      out[ 1 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[ 1 + inPos] & 255))) & 255);
      out[ 2 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[ 2 + inPos] & 255))) & 255);
      out[ 3 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[ 3 + inPos] & 255))) & 255);
      out[ 4 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[ 4 + inPos] & 255))) & 255);
      out[ 5 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[ 5 + inPos] & 255))) & 255);
      out[ 6 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[ 6 + inPos] & 255))) & 255);
      out[ 7 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[ 7 + inPos] & 255))) & 255);
    }
    public final void pack32Values(final int[] in, final int inPos, final byte[] out, final int outPos) {
      out[ 0 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[ 0 + inPos] & 255))) & 255);
      out[ 1 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[ 1 + inPos] & 255))) & 255);
      out[ 2 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[ 2 + inPos] & 255))) & 255);
      out[ 3 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[ 3 + inPos] & 255))) & 255);
      out[ 4 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[ 4 + inPos] & 255))) & 255);
      out[ 5 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[ 5 + inPos] & 255))) & 255);
      out[ 6 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[ 6 + inPos] & 255))) & 255);
      out[ 7 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[ 7 + inPos] & 255))) & 255);
      out[ 8 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[ 8 + inPos] & 255))) & 255);
      out[ 9 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[ 9 + inPos] & 255))) & 255);
      out[10 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[10 + inPos] & 255))) & 255);
      out[11 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[11 + inPos] & 255))) & 255);
      out[12 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[12 + inPos] & 255))) & 255);
      out[13 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[13 + inPos] & 255))) & 255);
      out[14 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[14 + inPos] & 255))) & 255);
      out[15 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[15 + inPos] & 255))) & 255);
      out[16 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[16 + inPos] & 255))) & 255);
      out[17 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[17 + inPos] & 255))) & 255);
      out[18 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[18 + inPos] & 255))) & 255);
      out[19 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[19 + inPos] & 255))) & 255);
      out[20 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[20 + inPos] & 255))) & 255);
      out[21 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[21 + inPos] & 255))) & 255);
      out[22 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[22 + inPos] & 255))) & 255);
      out[23 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[23 + inPos] & 255))) & 255);
      out[24 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[24 + inPos] & 255))) & 255);
      out[25 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[25 + inPos] & 255))) & 255);
      out[26 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[26 + inPos] & 255))) & 255);
      out[27 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[27 + inPos] & 255))) & 255);
      out[28 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[28 + inPos] & 255))) & 255);
      out[29 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[29 + inPos] & 255))) & 255);
      out[30 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[30 + inPos] & 255))) & 255);
      out[31 + outPos] = (byte)((
          //                  [76543210]
          //                  [76543210]
           ((in[31 + inPos] & 255))) & 255);
    }
    public final void unpack8Values(final byte[] in, final int inPos, final int[] out, final int outPos) {
      out[ 0 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[ 0 + inPos]) & 255) ) & 255);
      out[ 1 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[ 1 + inPos]) & 255) ) & 255);
      out[ 2 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[ 2 + inPos]) & 255) ) & 255);
      out[ 3 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[ 3 + inPos]) & 255) ) & 255);
      out[ 4 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[ 4 + inPos]) & 255) ) & 255);
      out[ 5 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[ 5 + inPos]) & 255) ) & 255);
      out[ 6 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[ 6 + inPos]) & 255) ) & 255);
      out[ 7 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[ 7 + inPos]) & 255) ) & 255);
    }
    public final void unpack32Values(final byte[] in, final int inPos, final int[] out, final int outPos) {
      out[ 0 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[ 0 + inPos]) & 255) ) & 255);
      out[ 1 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[ 1 + inPos]) & 255) ) & 255);
      out[ 2 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[ 2 + inPos]) & 255) ) & 255);
      out[ 3 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[ 3 + inPos]) & 255) ) & 255);
      out[ 4 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[ 4 + inPos]) & 255) ) & 255);
      out[ 5 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[ 5 + inPos]) & 255) ) & 255);
      out[ 6 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[ 6 + inPos]) & 255) ) & 255);
      out[ 7 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[ 7 + inPos]) & 255) ) & 255);
      out[ 8 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[ 8 + inPos]) & 255) ) & 255);
      out[ 9 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[ 9 + inPos]) & 255) ) & 255);
      out[10 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[10 + inPos]) & 255) ) & 255);
      out[11 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[11 + inPos]) & 255) ) & 255);
      out[12 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[12 + inPos]) & 255) ) & 255);
      out[13 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[13 + inPos]) & 255) ) & 255);
      out[14 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[14 + inPos]) & 255) ) & 255);
      out[15 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[15 + inPos]) & 255) ) & 255);
      out[16 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[16 + inPos]) & 255) ) & 255);
      out[17 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[17 + inPos]) & 255) ) & 255);
      out[18 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[18 + inPos]) & 255) ) & 255);
      out[19 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[19 + inPos]) & 255) ) & 255);
      out[20 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[20 + inPos]) & 255) ) & 255);
      out[21 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[21 + inPos]) & 255) ) & 255);
      out[22 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[22 + inPos]) & 255) ) & 255);
      out[23 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[23 + inPos]) & 255) ) & 255);
      out[24 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[24 + inPos]) & 255) ) & 255);
      out[25 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[25 + inPos]) & 255) ) & 255);
      out[26 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[26 + inPos]) & 255) ) & 255);
      out[27 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[27 + inPos]) & 255) ) & 255);
      out[28 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[28 + inPos]) & 255) ) & 255);
      out[29 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[29 + inPos]) & 255) ) & 255);
      out[30 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[30 + inPos]) & 255) ) & 255);
      out[31 + outPos] =
          //                  [76543210]
          //                  [76543210]
            (((((int)in[31 + inPos]) & 255) ) & 255);
    }
  }
