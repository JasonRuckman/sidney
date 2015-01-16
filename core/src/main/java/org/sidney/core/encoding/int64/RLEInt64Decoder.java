package org.sidney.core.encoding.int64;

import org.sidney.core.encoding.Encoding;
import org.sidney.core.encoding.int32.Int32Decoder;

import java.io.IOException;
import java.io.InputStream;

public class RLEInt64Decoder implements Int64Decoder {
    private final Int64Decoder valueDecoder = Encoding.GROUPVARINT.newInt64Decoder();
    private final Int32Decoder runSizeDecoder = Encoding.DELTABITPACKINGHYBRID.newInt32Decoder();
    private int runSize = 0;
    private long currentRun = 0;

    public long nextLong() {
        if (runSize-- == 0) {
            loadNextRun();
            runSize--;
        }

        return currentRun;
    }

    @Override
    public long[] nextLongs(int num) {
        long[] longs = new long[num];
        for (int i = 0; i < num; i++) {
            longs[i] = nextLong();
        }
        return longs;
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
        currentRun = valueDecoder.nextLong();
        runSize = runSizeDecoder.nextInt();
    }
}