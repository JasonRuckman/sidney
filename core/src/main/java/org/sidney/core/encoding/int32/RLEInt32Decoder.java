package org.sidney.core.encoding.int32;

import org.sidney.core.encoding.AbstractDecoder;
import org.sidney.core.encoding.Encoding;

import java.io.IOException;
import java.io.InputStream;

public class RLEInt32Decoder extends AbstractDecoder implements Int32Decoder {
    private final Int32Decoder valueDecoder = Encoding.DELTABITPACKINGHYBRID.newInt32Decoder();
    private final Int32Decoder runSizeDecoder = Encoding.BITPACKED.newInt32Decoder();
    private int runSize = 0;
    private int currentRun = 0;

    public int nextInt() {
        if (runSize-- == 0) {
            loadNextRun();
            runSize--;
        }

        return currentRun;
    }

    @Override
    public int[] nextInts(int num) {
        int[] ints = new int[num];
        for (int i = 0; i < num; i++) {
            ints[i] = nextInt();
        }
        return ints;
    }

    @Override
    public String supportedEncoding() {
        return Encoding.RLE.name();
    }

    @Override
    public void populateBufferFromStream(InputStream inputStream) throws IOException {
        runSize = 0;
        currentRun = 0;

        valueDecoder.populateBufferFromStream(inputStream);
        runSizeDecoder.populateBufferFromStream(inputStream);
    }

    private void loadNextRun() {
        currentRun = valueDecoder.nextInt();
        runSize = runSizeDecoder.nextInt();
    }
}