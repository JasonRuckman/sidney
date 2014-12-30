package org.sidney.core.encoding.string;

import org.sidney.core.encoding.AbstractDecoder;
import org.sidney.core.encoding.Encoding;
import org.sidney.core.encoding.bytes.RawBytesDecoder;
import org.sidney.core.encoding.int32.DeltaBitPackingInt32Decoder;
import org.sidney.core.encoding.int32.Int32Decoder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class DeltaLengthStringDecoder extends AbstractDecoder implements StringDecoder {
    private final Int32Decoder lengthDecoder = new DeltaBitPackingInt32Decoder();
    private final RawBytesDecoder bytesDecoder = new RawBytesDecoder();
    private final Charset charset = Charset.forName("UTF-8");

    public String readString() {
        int length = lengthDecoder.nextInt();
        byte[] bytes = bytesDecoder.readBytes(length);

        return charset.decode(ByteBuffer.wrap(bytes)).toString();
    }

    @Override
    public String[] readStrings(int num) {
        String[] strings = new String[num];
        for (int i = 0; i < num; i++) {
            strings[i] = readString();
        }
        return strings;
    }

    @Override
    public void populateBufferFromStream(InputStream inputStream) throws IOException {
        lengthDecoder.populateBufferFromStream(inputStream);
        bytesDecoder.populateBufferFromStream(inputStream);
    }

    @Override
    public String supportedEncoding() {
        return Encoding.DELTALENGTH.name();
    }
}
