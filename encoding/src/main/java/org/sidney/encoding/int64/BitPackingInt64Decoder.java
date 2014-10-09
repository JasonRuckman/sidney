package org.sidney.encoding.int64;

import org.sidney.encoding.AbstractDecoder;
import org.sidney.encoding.Encoding;
import org.sidney.encoding.IntPacker;
import org.sidney.encoding.IntPackerFactory;

import java.io.IOException;
import java.io.InputStream;

public class BitPackingInt64Decoder extends AbstractDecoder implements Int64Decoder {
    private long[] miniBlock;
    private int indexInMiniBlock = 0;

    @Override
    public long nextLong() {
        if(indexInMiniBlock == miniBlock.length) {
            readMiniBlockFromBuffer();
        }

        return miniBlock[indexInMiniBlock++];
    }

    @Override
    public long[] nextLongs(int num) {
        long[] longs = new long[num];
        for(int i = 0; i < num; i++) {
            longs[i] = nextLong();
        }
        return longs;
    }

    @Override
    public String supportedEncoding() {
        return Encoding.BITPACKED.name();
    }

    @Override
    public void readFromStream(InputStream inputStream) throws IOException {
        super.readFromStream(inputStream);

        if(getNumValues() > 0) {
            readMiniBlockFromBuffer();
        }
    }

    private void readMiniBlockFromBuffer() {
        indexInMiniBlock = 0;

        int numLongs = readIntLE();
        int bitwidth = readIntLE();
        int bytes = readIntLE();

        if(numLongs == 0) {
            miniBlock = new long[0];
            return;
        }

        IntPacker packer = IntPackerFactory.packer(bitwidth);
        miniBlock = new long[numLongs];
        packer.decode(getBuffer(), getPosition(), miniBlock, 0, numLongs);
        incrementPosition(bytes);
    }
}