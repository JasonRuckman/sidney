package org.sidney.core.encoding.int64;

import org.sidney.core.encoding.Encoding;
import org.sidney.core.encoding.int32.DeltaBitPackingInt32Encoder;
import org.sidney.core.encoding.int32.Int32Encoder;

import java.io.IOException;
import java.io.OutputStream;

public class RLEInt64Encoder implements Int64Encoder {
    private Int64Encoder valueEncoder = Encoding.GROUPVARINT.newInt64Encoder();
    private Int32Encoder runSizeEncoder = Encoding.DELTABITPACKINGHYBRID.newInt32Encoder();

    private long currentRun = 0;
    private int runSize = 0;
    private boolean isNewRun = true;

    @Override
    public void writeLong(long value) {
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
    public void writeLongs(long[] longs) {
        for (long v : longs) {
            writeLong(v);
        }
    }

    @Override
    public String supportedEncoding() {
        return Encoding.RLE.name();
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

    private void flush() {
        valueEncoder.writeLong(currentRun);
        runSizeEncoder.writeInt(runSize);

        currentRun = 0;
        runSize = 0;
    }
}