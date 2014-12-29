package org.sidney.core.encoding.string;

import org.sidney.core.encoding.AbstractEncoder;
import org.sidney.core.encoding.Encoding;
import org.sidney.core.encoding.int32.BitPackingInt32Encoder;
import org.sidney.core.encoding.int32.Int32Encoder;

import java.io.IOException;
import java.io.OutputStream;

public class RLEStringEncoder extends AbstractEncoder implements StringEncoder {
    private String currentRun = null;
    private int runSize;
    private boolean isFirstValue = true;
    private Int32Encoder runSizeEncoder = new BitPackingInt32Encoder();
    private StringEncoder valueEncoder = new PlainStringEncoder();

    @Override
    public void writeString(String s) {
        if (isFirstValue) {
            currentRun = s;
            isFirstValue = false;
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

        currentRun = null;
        isFirstValue = true;
        runSize = 0;
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        flush();

        valueEncoder.writeToStream(outputStream);
        runSizeEncoder.writeToStream(outputStream);
    }
}
