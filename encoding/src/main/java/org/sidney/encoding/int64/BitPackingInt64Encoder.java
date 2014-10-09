package org.sidney.encoding.int64;

import com.google.common.io.LittleEndianDataOutputStream;
import org.sidney.encoding.AbstractEncoder;
import org.sidney.encoding.Encoding;
import org.sidney.encoding.IntPacker;
import org.sidney.encoding.IntPackerFactory;

import java.io.IOException;
import java.io.OutputStream;

public class BitPackingInt64Encoder extends AbstractEncoder implements Int64Encoder {
    public static final int MINI_BLOCK_SIZE = 128;

    private long[] miniBlock = new long[MINI_BLOCK_SIZE];
    private int maxBitwidth = 0;
    private int index = 0;
    private int numMiniBlocks = 0;

    @Override
    public void writeLong(long value) {
        numValues++;
        if(numValues == 1) {
            numMiniBlocks++;
        }

        int bitwidth = 64 - Long.numberOfLeadingZeros(value);

        maxBitwidth = Math.max(maxBitwidth, bitwidth);
        miniBlock[index++] = value;

        if(index == MINI_BLOCK_SIZE) {
            flushMiniBlock();
            numMiniBlocks++;
        }
    }

    @Override
    public void writeLongs(long[] values) {
        for(long l : values) {
            writeLong(l);
        }
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        LittleEndianDataOutputStream dos = new LittleEndianDataOutputStream(outputStream);
        dos.writeInt(numValues);
        dos.writeInt(getPosition());
        dos.write(getBuffer(), 0, getPosition());
    }

    @Override
    public String supportedEncoding() {
        return Encoding.BITPACKED.name();
    }

    @Override
    public void reset() {
        super.reset();
        resetBlock();
    }


    private void flushMiniBlock() {
        if(index == 0) {
            return;
        }

        int bytesRequired = index * bytesForBitwidth(maxBitwidth);
        IntPacker packer = IntPackerFactory.packer(maxBitwidth);

        writeIntLE(index);
        writeIntLE(maxBitwidth);
        writeIntLE(bytesRequired);

        ensureCapacity(bytesRequired);

        packer.encode(miniBlock, 0, getBuffer(), getPosition(), index);
        incrementPosition(bytesRequired);
        resetBlock();
    }

    private int bytesForBitwidth(int bitWidth) {
        return (int) Math.ceil((double) bitWidth / 8);
    }

    private void resetBlock(){
        index = 0;
        maxBitwidth = 0;
    }
}