package org.sidney.encoding.string;

import org.sidney.encoding.AbstractDecoder;
import org.sidney.encoding.Encoding;
import org.sidney.encoding.bytes.RawBytesDecoder;
import org.sidney.encoding.int32.DeltaBitPackingInt32Decoder;
import org.sidney.encoding.int32.Int32Decoder;

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
    public void readFromStream(InputStream inputStream) throws IOException {
        inputStream = inputStreamWrapIfNecessary(inputStream);

        lengthDecoder.readFromStream(inputStream);
        bytesDecoder.readFromStream(inputStream);
    }

    @Override
    public String supportedEncoding() {
        return Encoding.DELTALENGTH.name();
    }
}
