package org.sidney.encoding.int32;

import org.sidney.bitpacking.Int32BytePacker;
import org.sidney.bitpacking.Packers;
import org.sidney.encoding.AbstractEncoder;
import org.sidney.encoding.Bytes;
import org.sidney.encoding.Encoding;

import java.io.IOException;
import java.io.OutputStream;

import static org.sidney.encoding.io.StreamUtils.writeIntToStream;

/**
 * Inspired by parquet's delta packer.  Packs ints into blocks of 128, bitpacked on the max bitwidth of the block
 */
public class DeltaBitPackingInt32Encoder extends AbstractEncoder implements Int32Encoder {
    public static final int DEFAULT_BLOCK_SIZE = 128;

    private int firstValue = 0;
    private int prevValue = 0;
    private int totalValueCount = 0;
    private int[] currentMiniBlock;
    private int currentIndex = 0;
    private int currentMinDelta = Integer.MAX_VALUE;
    private int miniBlockSize;

    public DeltaBitPackingInt32Encoder() {
        this(DEFAULT_BLOCK_SIZE);
    }

    public DeltaBitPackingInt32Encoder(int miniBlockSize) {
        this.miniBlockSize = miniBlockSize;
        this.currentMiniBlock = new int[miniBlockSize];
    }

    public void writeInt(int value) {
        if (++totalValueCount == 1) {
            firstValue = value;
            prevValue = value;
            return;
        }
        int delta = value - prevValue;
        prevValue = value;
        currentMiniBlock[currentIndex++] = delta;
        currentMinDelta = Math.min(delta, currentMinDelta);
        if (currentIndex == miniBlockSize) {
            flushMiniBlock();
        }
    }

    @Override
    public void writeInts(int[] values) {
        for (int v : values) {
            writeInt(v);
        }
    }

    @Override
    public void reset() {
        currentIndex = 0;
        currentMinDelta = 0;
        prevValue = 0;
        totalValueCount = 0;
        firstValue = 0;
        setPosition(0);
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        flushMiniBlock();
        writeIntToStream(totalValueCount, outputStream);
        writeIntToStream(firstValue, outputStream);
        writeIntToStream(miniBlockSize, outputStream);
        if (totalValueCount <= 1) {
            return;
        }
        writeIntToStream(getPosition(), outputStream);
        outputStream.write(getBuffer(), 0, getPosition());
    }

    @Override
    public String supportedEncoding() {
        return Encoding.DELTABITPACKINGHYBRID.name();
    }

    private void flushMiniBlock() {
        if (currentIndex == 0 || totalValueCount == 1) {
            return;
        }

        int currentMaxBitwidth = 1;
        for (int i = 0; i < currentIndex; i++) {
            int num = currentMiniBlock[i] - currentMinDelta;
            currentMiniBlock[i] = num;
            currentMaxBitwidth = Math.max(currentMaxBitwidth, (32 - Integer.numberOfLeadingZeros(num)));
        }

        int numToWrite = Math.max(currentIndex, 8);
        writeIntInternal(currentMaxBitwidth);
        writeIntInternal(currentMinDelta);
        writeIntInternal(currentIndex);

        int strideSize = Bytes.sizeInBytes(8, currentMaxBitwidth);
        Int32BytePacker packer = Packers.LITTLE_ENDIAN.packer32(currentMaxBitwidth);
        ensureCapacity(Bytes.sizeInBytes(numToWrite, currentMaxBitwidth));
        for (int i = 0; i < numToWrite; i += 8) {
            packer.pack8Values(currentMiniBlock, i, getBuffer(), getPosition());
            incrementPosition(strideSize);
        }
        currentIndex = 0;
        currentMinDelta = Integer.MAX_VALUE;
        prevValue = firstValue;
    }
}