package org.sidney.core.encoding.float32;

import org.sidney.core.encoding.AbstractEncoder;
import org.sidney.core.encoding.Encoding;
import org.sidney.core.encoding.int32.BitPackingInt32Encoder;
import org.sidney.core.encoding.int32.DeltaBitPackingInt32Encoder;
import org.sidney.core.encoding.int32.Int32Encoder;

import java.io.IOException;
import java.io.OutputStream;

public class RLEFloat32Encoder extends AbstractEncoder implements Float32Encoder {
    private final Int32Encoder valueEncoder = new DeltaBitPackingInt32Encoder();
    private final Int32Encoder runSizeEncoder = new BitPackingInt32Encoder();
    private float currentRun = 0;
    private int runSize = 0;
    private boolean isNewRun = true;

    @Override
    public void writeFloat(float value) {
        if (isNewRun) {
            currentRun = value;
            isNewRun = false;
        } else if (Float.compare(currentRun, value) != 0) {
            flush();
            currentRun = value;
        }
        ++runSize;
    }

    @Override
    public void writeFloats(float[] floats) {
        for (float v : floats) {
            writeFloat(v);
        }
    }

    @Override
    public String supportedEncoding() {
        return Encoding.RLE.name();
    }

    private void flush() {
        int floatBits = Float.floatToIntBits(currentRun);

        valueEncoder.writeInt(floatBits);
        runSizeEncoder.writeInt(runSize);

        currentRun = 0;
        runSize = 0;
    }

    @Override
    public void reset() {
        valueEncoder.reset();
        runSizeEncoder.reset();
        isNewRun = true;
        currentRun = 0;
        runSize = 0;
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        flush();

        valueEncoder.writeToStream(outputStream);
        runSizeEncoder.writeToStream(outputStream);
    }
}