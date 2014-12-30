package org.sidney.core.encoding.string;

import org.sidney.core.encoding.AbstractEncoder;
import org.sidney.core.encoding.Encoding;
import org.sidney.core.encoding.int32.BitPackingInt32Encoder;
import org.sidney.core.encoding.int32.Int32Encoder;

import java.io.IOException;
import java.io.OutputStream;

public class RLEStringEncoder extends AbstractEncoder implements StringEncoder {
    private String currentRun = "";
    private int runSize;
    private boolean isNewRun = true;
    private Int32Encoder runSizeEncoder = new BitPackingInt32Encoder();
    private StringEncoder valueEncoder = new PlainStringEncoder();

    @Override
    public void writeString(String s) {
        if (isNewRun) {
            currentRun = s;
            isNewRun = false;
        } else if (!s.equals(currentRun)) {
            flush();
            currentRun = s;
        }
        ++runSize;
    }

    @Override
    public void writeStrings(String[] s) {
        for (String str : s) {
            writeString(str);
        }
    }

    @Override
    public String supportedEncoding() {
        return Encoding.RLE.name();
    }

    private void flush() {
        valueEncoder.writeString(currentRun);
        runSizeEncoder.writeInt(runSize);

        currentRun = "";
        runSize = 0;
    }

    @Override
    public void reset() {
        valueEncoder.reset();
        runSizeEncoder.reset();
        currentRun = "";
        runSize = 0;
        isNewRun = true;
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        flush();

        valueEncoder.writeToStream(outputStream);
        runSizeEncoder.writeToStream(outputStream);
    }
}
