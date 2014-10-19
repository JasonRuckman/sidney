package org.sidney.encoding.int32;

import com.google.common.io.LittleEndianDataInputStream;
import org.sidney.bitpacking.Int32BytePacker;
import org.sidney.bitpacking.Packers;
import org.sidney.encoding.AbstractDecoder;
import org.sidney.encoding.Encoding;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DeltaBitPackingInt32Decoder extends AbstractDecoder implements Int32Decoder {
    private int[] intBuffer = new int[0];
    private int currentIndex = 0;
    private int blockSize;
    private int totalValueCount;
    private int firstValue;
    private boolean isFirstValue = true;

    @Override
    public int nextInt() {
        if (isFirstValue) {
            isFirstValue = false;
            return firstValue;
        }

        if (currentIndex == intBuffer.length) {
            loadNextMiniBlock();
        }

        return intBuffer[currentIndex++];
    }

    @Override
    public int[] nextInts(int num) {
        int[] ints = new int[num];
        for (int i = 0; i < num; i++) {
            ints[i] = nextInt();
        }
        return ints;
    }

    private void reset() {
        currentIndex = 0;
        totalValueCount = 0;
        blockSize = 0;
        isFirstValue = true;
        setPosition(0);
    }

    @Override
    public void readFromStream(InputStream inputStream) throws IOException {
        reset();
        inputStream = wrapStreamIfNecessary(inputStream);

        LittleEndianDataInputStream dis = new LittleEndianDataInputStream(inputStream);

        totalValueCount = dis.readInt();
        firstValue = dis.readInt();
        blockSize = dis.readInt();

        intBuffer = new int[blockSize];

        if (totalValueCount <= 1) {
            return;
        }

        int bufferSize = dis.readInt();
        ensureSize(bufferSize);
        dis.read(getBuffer());
        loadNextMiniBlock();
    }

    @Override
    public String supportedEncoding() {
        return Encoding.DELTABITPACKINGHYBRID.name();
    }

    private void loadNextMiniBlock() {
        currentIndex = 0;

        int bitWidth = readIntLE();
        int minDelta = readIntLE();
        int numValuesToRead = readIntLE();

        totalValueCount -= numValuesToRead;

        int strideSize = sizeInBytes(8, bitWidth);
        Int32BytePacker packer = Packers.LITTLE_ENDIAN.packer32(bitWidth);
        for (int i = 0; i < numValuesToRead; i += 8) {
            packer.unpack8Values(getBuffer(), getPosition(), intBuffer, i);
            incrementPosition(strideSize);
        }

        for (int i = 0; i < numValuesToRead; i++) {
            intBuffer[i] += ((i == 0) ? firstValue : intBuffer[i - 1]) + minDelta;
        }
    }

    private int sizeInBytes(int num, int bitwidth) {
        double bitwidthD = bitwidth;
        return num * (int) (Math.ceil(bitwidthD / 8D));
    }
}