package org.sidney.encoding.int64;

import org.sidney.core.unsafe.UnsafeLongs;
import org.sidney.encoding.Encoding;
import parquet.bytes.LittleEndianDataOutputStream;

import java.io.IOException;
import java.io.OutputStream;

//does delta coding, does not bitpack, to reduce binary size, use with an lz4 encoder, or use secondary compression on the entire stream
public class DeltaInt64Encoder implements Int64Encoder {
    public static final int DEFAULT_BLOCK_SIZE = 128;
    private long[][] miniBlocks;
    private long firstValue = 0;
    private long prevValue = 0;
    private int currentMiniBlockIndex = 0;
    private int numDeltasInMiniBlock = 0;
    private int miniBlockSize;
    private int deltasIndex = 0;
    private int totalValueCount = 0;
    private int numMiniBlocks = 0;

    public DeltaInt64Encoder() {
        this(DEFAULT_BLOCK_SIZE);
    }

    public DeltaInt64Encoder(int miniBlockSize) {
        this.miniBlockSize = miniBlockSize;
        this.miniBlocks = new long[256][];

        for (int i = 0; i < miniBlocks.length; i++) {
            miniBlocks[i] = new long[miniBlockSize];
        }
    }

    @Override
    public void writeLong(long value) {
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

        if (numDeltasInMiniBlock == 0) {
            numMiniBlocks++;
            prevValue = firstValue;
        }

        long delta = value - prevValue;
        prevValue = value;
        //check if we need to roll our mini blocks

        numDeltasInMiniBlock++;
        miniBlocks[currentMiniBlockIndex][deltasIndex++] = delta;
    }

    @Override
    public void writeLongs(long[] values) {
        for (long v : values) {
            writeLong(v);
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
        dos.writeLong(firstValue);

        long[] minDeltas = calculateMinDeltas();
        for (long minDelta : minDeltas) {
            dos.writeLong(minDelta);
        }

        writeDeltas(minDeltas);

        for (int i = 0; i < minDeltas.length; i++) {
            writeMiniBlock(i, dos);
        }
    }

    @Override
    public Encoding supportedEncoding() {
        return Encoding.DELTA;
    }

    private void writeMiniBlock(int index, LittleEndianDataOutputStream dos) throws IOException {
        long[] miniBlock = miniBlocks[index];
        byte[] buf = new byte[miniBlockSize * 8];

        UnsafeLongs.copyLongsToBytes(miniBlock, 0, buf, 0, 128 * 8);

        dos.write(buf);
    }

    private void ensureMiniBlockCapacity() {
        if (currentMiniBlockIndex == miniBlocks.length - 1) {
            long[][] newBuf = new long[miniBlocks.length * 2][];
            System.arraycopy(miniBlocks, 0, newBuf, 0, miniBlocks.length);
            for (int i = miniBlocks.length; i < newBuf.length; i++) {
                newBuf[i] = new long[miniBlockSize];
            }
            miniBlocks = newBuf;
        }
    }

    private long[] calculateMinDeltas() {
        long[] minDeltas = new long[numMiniBlocks];

        for (int i = 0; i <= currentMiniBlockIndex; i++) {
            long[] miniBlock = miniBlocks[i];
            int length = (i == currentMiniBlockIndex) ? numDeltasInMiniBlock : miniBlock.length;

            minDeltas[i] = minDeltaForBlock(miniBlock, length);
        }

        return minDeltas;
    }

    //todo take alot of the branching out of these calls
    private void writeDeltas(long[] minDeltas) {
        for (int i = 0; i < minDeltas.length; i++) {
            long[] miniBlock = miniBlocks[i];
            long minDelta = minDeltas[i];
            int length = (i == currentMiniBlockIndex) ? numDeltasInMiniBlock : miniBlock.length;

            for (int j = 0; j < length; j++) {
                miniBlock[j] = miniBlock[j] - minDelta;
            }
        }
    }

    private long minDeltaForBlock(long[] miniBlock, int length) {
        long minDelta = Long.MAX_VALUE;
        for (int i = 0; i < length; i++) {
            minDelta = Math.min(minDelta, miniBlock[i]);
        }
        return minDelta;
    }
}
