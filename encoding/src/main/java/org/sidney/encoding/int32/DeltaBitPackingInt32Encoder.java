package org.sidney.encoding.int32;

import org.sidney.encoding.Encoding;
import parquet.bytes.LittleEndianDataOutputStream;
import parquet.column.values.bitpacking.BytePacker;
import parquet.column.values.bitpacking.Packer;

import java.io.IOException;
import java.io.OutputStream;

public class DeltaBitPackingInt32Encoder implements Int32Encoder {
    public static final int DEFAULT_BLOCK_SIZE = 128;
    private int[][] miniBlocks;
    private int firstValue = 0;
    private int prevValue = 0;
    private int currentMiniBlockIndex = 0;
    private int numDeltasInMiniBlock = 0;
    private int miniBlockSize;
    private int deltasIndex = 0;
    private int totalValueCount = 0;
    private int numMiniBlocks = 0;

    public DeltaBitPackingInt32Encoder() {
        this(DEFAULT_BLOCK_SIZE);
    }

    public DeltaBitPackingInt32Encoder(int miniBlockSize) {
        this.miniBlockSize = miniBlockSize;
        this.miniBlocks = new int[256][];

        for (int i = 0; i < miniBlocks.length; i++) {
            miniBlocks[i] = new int[miniBlockSize];
        }
    }

    public void writeInt(int value) {
        totalValueCount++;

        if (totalValueCount == 1) {
            firstValue = value;
            prevValue = firstValue;
            return;
        }

        if (numDeltasInMiniBlock == miniBlockSize) {
            //expand our miniblock buffer if necessary
            ensureMiniBlockCapacity();

            currentMiniBlockIndex++;
            numDeltasInMiniBlock = 0;
            prevValue = 0;
            deltasIndex = 0;
        }

        if(numDeltasInMiniBlock == 0) {
            numMiniBlocks++;
            prevValue = firstValue;
        }

        int delta = value - prevValue;
        prevValue = value;
        //check if we need to roll our mini blocks

        numDeltasInMiniBlock++;
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
        currentMiniBlockIndex = 0;
        numDeltasInMiniBlock = 0;
        deltasIndex = 0;
        totalValueCount = 0;
        numMiniBlocks = 0;
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        LittleEndianDataOutputStream dos = new LittleEndianDataOutputStream(outputStream);

        dos.writeInt(totalValueCount);
        dos.writeInt(numMiniBlocks);
        dos.writeInt(firstValue);

        int[] minDeltas = calculateMinDeltas();
        for(int minDelta : minDeltas) {
            dos.writeInt(minDelta);
        }

        int[] bitwidths = calculateBitwidths(minDeltas);
        for(int bitwidth : bitwidths) {
            dos.writeInt(bitwidth);
        }

        for(int i = 0; i < minDeltas.length; i++) {
            packAndWriteMiniBlock(i, bitwidths[i], dos);
        }
    }

    @Override
    public String supportedEncoding() {
        return Encoding.DELTABITPACKINGHYBRID.name();
    }

    private void packAndWriteMiniBlock(int index, int bitWidth, LittleEndianDataOutputStream dos) throws IOException {
        int[] miniBlock = miniBlocks[index];
        int length = (index == currentMiniBlockIndex) ? numDeltasInMiniBlock : miniBlock.length;

        byte[] buf = new byte[bitWidth];

        BytePacker packer = Packer.LITTLE_ENDIAN.newBytePacker(bitWidth);

        for(int i = 0; i < length; i += 8) {
            packer.pack8Values(miniBlock, i, buf, 0);
            dos.write(buf);
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
        }
    }

    private int getBitWidth(int value) {
        return 32 - Integer.numberOfLeadingZeros(value);
    }

    private int[] calculateMinDeltas() {
        if(numMiniBlocks == 0) {
            return new int[0];
        }
        int[] minDeltas = new int[numMiniBlocks];

        for(int i = 0; i <= currentMiniBlockIndex; i++) {
            int[] miniBlock = miniBlocks[i];
            int length = (i == currentMiniBlockIndex) ? numDeltasInMiniBlock : miniBlock.length;

            minDeltas[i] = minDeltaForBlock(miniBlock, length);
        }

        return minDeltas;
    }

    private int[] calculateBitwidths(int[] minDeltas) {
        int[] bitwidths = new int[minDeltas.length];

        for(int i = 0; i < minDeltas.length; i++) {
            int bitwidth = Integer.MIN_VALUE;
            int[] miniBlock = miniBlocks[i];
            int minDelta = minDeltas[i];
            int length = (i == currentMiniBlockIndex) ? numDeltasInMiniBlock : miniBlock.length;

            for(int j = 0; j < length; j++) {
                miniBlock[j] = miniBlock[j] - minDelta;
                bitwidth = Math.max(bitwidth, getBitWidth(miniBlock[j]));
            }

            bitwidths[i] = bitwidth;
        }

        return bitwidths;
    }

    private int minDeltaForBlock(int[] miniBlock, int length) {
        int minDelta = Integer.MAX_VALUE;
        for(int i = 0; i < length; i++) {
            minDelta = Math.min(minDelta, miniBlock[i]);
        }
        return minDelta;
    }
}