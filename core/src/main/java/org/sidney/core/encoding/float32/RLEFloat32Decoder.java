package org.sidney.core.encoding.float32;

import org.sidney.core.encoding.AbstractDecoder;
import org.sidney.core.encoding.Encoding;
import org.sidney.core.encoding.int32.Int32Decoder;

import java.io.IOException;
import java.io.InputStream;

public class RLEFloat32Decoder extends AbstractDecoder implements Float32Decoder {
    private final Int32Decoder valueDecoder = Encoding.DELTABITPACKINGHYBRID.newInt32Decoder();
    private final Int32Decoder runSizeDecoder = Encoding.BITPACKED.newInt32Decoder();
    private int runSize = 0;
    private float currentRun = 0;

    public float nextFloat() {
        if(runSize-- == 0) {
            loadNextRun();
            runSize--;
        }

        return currentRun;
    }

    @Override
    public float[] nextFloats(int num) {
        float[] floats = new float[num];
        for(int i = 0; i < num; i++) {
            floats[i] = nextFloat();
        }
        return floats;
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
        currentRun = Float.intBitsToFloat(valueDecoder.nextInt());
        runSize = runSizeDecoder.nextInt();
    }
}