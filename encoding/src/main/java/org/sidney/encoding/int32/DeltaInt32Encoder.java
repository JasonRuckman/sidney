package org.sidney.encoding.int32;

import org.sidney.core.Bytes;
import parquet.bytes.LittleEndianDataOutputStream;
import parquet.column.values.bitpacking.BytePacker;
import parquet.column.values.bitpacking.Packer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DeltaInt32Encoder implements Int32Encoder {
    public static final int DEFAULT_BLOCK_SIZE = 128;
    private final byte[] miniBlockBuffer = new byte[2048];
    private int[][] miniBlocks;
    private int firstValue = 0;
    private int prevValue = 0;
    private int minDelta = Integer.MAX_VALUE;
    private int currentMiniBlockIndex = 0;
    private int numDeltasInMiniBlock = 0;
    private int position = 0;
    private int miniBlockSize;
    private int[] firstValues;
    private int deltasIndex = 0;
    private int totalValueCount = 0;
    private int numMiniBlocks = 0;

    public DeltaInt32Encoder() {
        this(DEFAULT_BLOCK_SIZE);
    }

    public DeltaInt32Encoder(int miniBlockSize) {
        this.miniBlockSize = miniBlockSize;
        this.miniBlocks = new int[256][];

        for (int i = 0; i < miniBlocks.length; i++) {
            miniBlocks[i] = new int[miniBlockSize];
        }

        firstValues = new int[miniBlocks.length];
    }

    public void writeInt(int value) {
        numDeltasInMiniBlock++;
        totalValueCount++;

        if (numDeltasInMiniBlock == 1) {
            numMiniBlocks++;
            firstValue = value;
            prevValue = firstValue;
            return;
        }

        int delta = value - prevValue;
        //check if we need to roll our mini blocks
        if (numDeltasInMiniBlock == miniBlockSize) {
            //expand our miniblock buffer if necessary
            ensureMiniBlockCapacity();

            firstValues[currentMiniBlockIndex] = firstValue;
            currentMiniBlockIndex++;
            numMiniBlocks++;
            numDeltasInMiniBlock = 0;
            firstValue = 0;
            prevValue = 0;
            deltasIndex = 0;
        }

        miniBlocks[currentMiniBlockIndex][deltasIndex++] = delta;
    }

    @Override
    public void writeInts(int[] values) {
        for (int v : values) {
            writeInt(v);
        }
    }

    @Override
    public void reset() {
        firstValue = 0;
        prevValue = 0;
        numDeltasInMiniBlock = 0;
        currentMiniBlockIndex = 0;
        deltasIndex = 0;
        totalValueCount = 0;
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        LittleEndianDataOutputStream dos = new LittleEndianDataOutputStream(outputStream);

        dos.writeInt(totalValueCount);
        dos.writeInt(numMiniBlocks);

        for (int i = 0; i < numMiniBlocks; i++) {
            int length = writeMiniBlock(i);

            dos.write(miniBlockBuffer, 0, length);
        }
    }

    private void ensureMiniBlockCapacity() {
        if (currentMiniBlockIndex == miniBlocks.length - 1) {
            int[][] newBuf = new int[miniBlocks.length * 2][];
            System.arraycopy(miniBlocks, 0, newBuf, 0, miniBlocks.length);
            for (int i = miniBlocks.length; i < newBuf.length; i++) {
                newBuf[i] = new int[miniBlockSize];
            }
            miniBlocks = newBuf;

            //also copy our first values
            int[] newFirstBuf = new int[miniBlocks.length];
            System.arraycopy(firstValues, 0, newFirstBuf, 0, newFirstBuf.length);

            firstValues = newFirstBuf;
        }
    }

    private int getBitWidth(int value) {
        return 32 - Integer.numberOfLeadingZeros(value);
    }

    private int writeMiniBlock(int index) {
        int[] miniBlock = miniBlocks[index];
        int length = miniBlock.length;
        if (index == numMiniBlocks - 1) {
            //we are the last miniblock, so don't use the array length
            length = numDeltasInMiniBlock;
        }

        int maxBitWidth = Integer.MIN_VALUE;
        int minDelta = Integer.MAX_VALUE;
        //go through twice, once to calculate min-delta, then again to align new values to min delta and compute bit-widths
        for (int i = 0; i < length; i++) {
            minDelta = Math.min(minDelta, miniBlock[i]);
        }

        //adjust deltas, calculate bit-widths
        for(int i = 0; i < length; i++) {
            int adjustedDelta = miniBlock[i] - minDelta;

            miniBlock[i] = adjustedDelta;
            maxBitWidth = Math.max(maxBitWidth, getBitWidth(adjustedDelta));
        }

        BytePacker packer = Packer.LITTLE_ENDIAN.newBytePacker(maxBitWidth);

        //write firstvalue|numvalues|mindelta|bitwidth
        Bytes.writeIntOn4Bytes(firstValues[index], miniBlockBuffer, 0);
        Bytes.writeIntOn4Bytes(length, miniBlockBuffer, 4);
        Bytes.writeIntOn4Bytes(minDelta, miniBlockBuffer, 8);
        Bytes.writeIntOn4Bytes(maxBitWidth, miniBlockBuffer, 12);

        int counter = 0;
        //now, walk through the miniblock and back into buffer
        for (int i = 0; i < length; i += 8) {
            packer.pack8Values(miniBlock, i, miniBlockBuffer, (maxBitWidth * counter) + 16);
            counter++;
        }

        return maxBitWidth * counter + 16;
    }
}