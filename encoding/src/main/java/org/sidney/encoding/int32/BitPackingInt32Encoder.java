package org.sidney.encoding.int32;

import org.sidney.bitpacking.Int32BytePacker;
import org.sidney.bitpacking.Packers;
import org.sidney.encoding.AbstractEncoder;
import org.sidney.encoding.Encoding;

import java.io.IOException;
import java.io.OutputStream;

public class BitPackingInt32Encoder extends AbstractEncoder implements Int32Encoder {
    public static final int DEFAULT_BLOCK_SIZE = 128;
    private int[] currentMiniBlock;
    private int currentIndex = 0;
    private final int miniBlockSize;
    private int currentMaxBitwidth = Integer.MIN_VALUE;

    public BitPackingInt32Encoder() {
        this(DEFAULT_BLOCK_SIZE);
    }

    public BitPackingInt32Encoder(int miniBlockSize) {
        this.miniBlockSize = miniBlockSize;
        this.currentMiniBlock = new int[miniBlockSize];
    }

    @Override
    public void writeInt(int value) {
        currentMiniBlock[currentIndex++] = value;
        currentMaxBitwidth = Math.max(currentMaxBitwidth, 32 - Integer.numberOfLeadingZeros(value));

        if (currentIndex == miniBlockSize) {
            flushMiniBlock();
        }
    }

    @Override
    public void writeInts(int[] values) {
        for (int value : values) {
            writeInt(value);
        }
    }

    @Override
    public String supportedEncoding() {
        return Encoding.BITPACKED.name();
    }

    private void flushMiniBlock() {
        int numToWrite = Math.max(currentIndex, 8);
        writeIntInternal(currentIndex);
        writeIntInternal(currentMaxBitwidth);

        if (currentIndex > 0) {
            int strideSize = sizeInBytes(8, currentMaxBitwidth);
            Int32BytePacker packer = Packers.LITTLE_ENDIAN.packer32(currentMaxBitwidth);
            ensureCapacity(sizeInBytes(numToWrite, currentMaxBitwidth));
            for (int i = 0; i < numToWrite; i += 8) {
                packer.pack8Values(currentMiniBlock, i, getBuffer(), getPosition());
                incrementPosition(strideSize);
            }
        }
        currentMiniBlock = new int[miniBlockSize];
        currentMaxBitwidth = Integer.MIN_VALUE;
        currentIndex = 0;
    }

    private int sizeInBytes(int num, int bitwidth) {
        double bitwidthD = bitwidth;
        return num * (int) (Math.ceil(bitwidthD / 8D));
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        flushMiniBlock();

        super.writeToStream(outputStream);
    }
}