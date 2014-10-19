package org.sidney.bitpacking;

import org.sidney.bitpacking.packers.be.int32.*;

/**
 * Packs from the Most Significant Bit first
 * 
 * @author automatically generated
 * @see Int32ByteBasedBitPackingGenerator
 *
 */
public abstract class ByteBitPacking32BitBE {

  private static final Int32BytePacker[] packers = new Int32BytePacker[33];
  static {
    packers[0] = new Packer0();
    packers[1] = new Packer1();
    packers[2] = new Packer2();
    packers[3] = new Packer3();
    packers[4] = new Packer4();
    packers[5] = new Packer5();
    packers[6] = new Packer6();
    packers[7] = new Packer7();
    packers[8] = new Packer8();
    packers[9] = new Packer9();
    packers[10] = new Packer10();
    packers[11] = new Packer11();
    packers[12] = new Packer12();
    packers[13] = new Packer13();
    packers[14] = new Packer14();
    packers[15] = new Packer15();
    packers[16] = new Packer16();
    packers[17] = new Packer17();
    packers[18] = new Packer18();
    packers[19] = new Packer19();
    packers[20] = new Packer20();
    packers[21] = new Packer21();
    packers[22] = new Packer22();
    packers[23] = new Packer23();
    packers[24] = new Packer24();
    packers[25] = new Packer25();
    packers[26] = new Packer26();
    packers[27] = new Packer27();
    packers[28] = new Packer28();
    packers[29] = new Packer29();
    packers[30] = new Packer30();
    packers[31] = new Packer31();
    packers[32] = new Packer32();
  }

  public static final Int32BytePackerFactory factory = new Int32BytePackerFactory() {
    public Int32BytePacker newBytePacker(int bitWidth) {
      return packers[bitWidth];
    }
  };

}
