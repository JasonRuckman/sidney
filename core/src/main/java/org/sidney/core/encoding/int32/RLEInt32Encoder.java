package org.sidney.core.encoding.int32;

import org.sidney.core.encoding.AbstractEncoder;
import org.sidney.core.encoding.Encoding;

import java.io.IOException;
import java.io.OutputStream;

public class RLEInt32Encoder extends AbstractEncoder implements Int32Encoder {
    private final Int32Encoder valueEncoder = new DeltaBitPackingInt32Encoder();
    private final Int32Encoder runSizeEncoder = new BitPackingInt32Encoder();
    private int currentRun = 0;
    private int runSize = 0;
    private boolean isNewRun = true;

    @Override
    public void writeInt(int value) {
        if (isNewRun) {
            currentRun = value;
            isNewRun = false;
        } else if (currentRun != value) {
            flush();
            currentRun = value;
        }
        ++runSize;
    }

    @Override
    public void writeInts(int[] ints) {
        for (int v : ints) {
            writeInt(v);
        }
    }

    @Override
    public String supportedEncoding() {
        return Encoding.RLE.name();
    }

    private void flush() {
        valueEncoder.writeInt(currentRun);
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