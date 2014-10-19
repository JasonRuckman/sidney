package org.sidney.encoding.string;

import com.google.common.base.Charsets;
import org.sidney.encoding.bytes.RawBytesEncoder;
import org.sidney.encoding.int32.DeltaBitPackingInt32Encoder;
import org.sidney.encoding.int32.Int32Encoder;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class DeltaLengthStringEncoder implements StringEncoder {
    private final Int32Encoder lengthEncoder = new DeltaBitPackingInt32Encoder();
    private final RawBytesEncoder bytesEncoder = new RawBytesEncoder();
    private final Charset charset = Charsets.UTF_8;

    public void writeString(String s) {
        ByteBuffer bb = charset.encode(s);

        lengthEncoder.writeInt(bb.limit());
        bytesEncoder.writeBytes(bb.array(), 0, bb.limit());
    }

    @Override
    public void writeStrings(String[] s) {
        for(String str : s) {
            writeString(str);
        }
    }

    @Override
    public void reset() {
        lengthEncoder.reset();
        bytesEncoder.reset();
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        lengthEncoder.writeToStream(outputStream);
        bytesEncoder.writeToStream(outputStream);
    }

    @Override
    public String supportedEncoding() {
        return null;
    }
}
