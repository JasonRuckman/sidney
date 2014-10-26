package org.sidney.encoding.int32;

import org.sidney.bitpacking.Int32BytePacker;
import org.sidney.bitpacking.Packers;
import org.sidney.encoding.AbstractDecoder;
import org.sidney.encoding.Bytes;
import org.sidney.encoding.Encoding;

import java.io.IOException;
import java.io.InputStream;

//fastpfor is better than this impl, but its relatively competitive if i want to remove the dep
public class BitPackingInt32Decoder extends AbstractDecoder implements Int32Decoder {
    private int[] currentMiniBlock;
    private int currentIndex;

    @Override
    public int nextInt() {
        if (currentIndex == currentMiniBlock.length) {
            loadNextMiniBlock();
        }

        return currentMiniBlock[currentIndex++];
    }

    @Override
    public int[] nextInts(int num) {
        int[] result = new int[num];
        for (int i = 0; i < num; i++) {
            result[i] = nextInt();
        }
        return result;
    }

    @Override
    public String supportedEncoding() {
        return Encoding.BITPACKED.name();
    }

    @Override
    public void readFromStream(InputStream inputStream) throws IOException {
        super.readFromStream(inputStream);

        loadNextMiniBlock();
    }

    private void loadNextMiniBlock() {
        currentIndex = 0;
        currentMiniBlock = new int[128];

        int numValuesToRead = readIntLE();
        int bitWidth = readIntLE();
        if (numValuesToRead > 0) {
            int strideSize = Bytes.sizeInBytes(8, bitWidth);
            Int32BytePacker packer = Packers.LITTLE_ENDIAN.packer32(bitWidth);
            for (int i = 0; i < numValuesToRead; i += 8) {
                packer.unpack8Values(getBuffer(), getPosition(), currentMiniBlock, i);
                incrementPosition(strideSize);
            }
        }
    }
}
