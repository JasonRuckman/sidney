package org.sidney.encoding.int32;

import org.sidney.core.Bytes;

import java.io.IOException;
import java.io.OutputStream;

public class DeltaInt32Encoder implements Int32Encoder {
    public static final int DEFAULT_BLOCK_SIZE = 128;
    private final byte[] miniBlockBuffer = new byte[256];
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
        numDeltasInMiniBlock = 0;
        currentMiniBlockIndex = 0;
    }

    @Override
    public int writeToBuffer(byte[] buffer, int offset) {
        //write header, then write each of the mini blocks
        int newOffset = writeHeader(buffer, offset);
        return 0;
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        outputStream.write(totalValueCount);
        outputStream.write(numMiniBlocks);
    }

    private int writeHeader(byte[] buffer, int offset) {
        Bytes.writeIntOn4Bytes(totalValueCount, buffer, offset);
        Bytes.writeIntOn4Bytes(numMiniBlocks, buffer, offset + 4);

        return 0;
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
        int numZeros = Integer.numberOfLeadingZeros(value);
        return 32 - numZeros;
    }

    private void flushDeltaBuffer() {
        /*int maxWidth = Integer.MIN_VALUE;

        for (int i = 0; i < numDeltas; i++) {
            int positiveDelta = currentDeltas[i] - minDelta;
            maxWidth = Math.max(maxWidth, getBitWidth(positiveDelta));
            currentDeltas[i] = currentDeltas[i] - minDelta;
        }

        require((numDeltas * 4) + 4);

        Bytes.writeIntOn4Bytes(minDelta, buffer, 0);

        position += 4;

        BytePacker packer = Packer.LITTLE_ENDIAN.newBytePacker(maxWidth);

        for(int i = 0; i < numDeltas; i += 8) {
            packer.pack8Values(currentDeltas, i, miniBlockBuffer, 0);
            System.arraycopy(miniBlockBuffer, 0, buffer, position, maxWidth);
            position += maxWidth;
        }

        numDeltas = 0;
        minDelta = Integer.MAX_VALUE;*/
    }
}