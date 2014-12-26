package org.sidney.core.encoding.string;

import org.sidney.core.encoding.AbstractDecoder;
import org.sidney.core.encoding.int32.DeltaBitPackingInt32Decoder;
import org.sidney.core.encoding.int32.Int32Decoder;

import java.io.IOException;
import java.io.InputStream;

public class CharAsIntStringDecoder extends AbstractDecoder implements StringDecoder {
    private final Int32Decoder lengthDecoder = new DeltaBitPackingInt32Decoder();
    private final Int32Decoder characterDecoder = new DeltaBitPackingInt32Decoder();

    @Override
    public String readString() {
        char[] arr = new char[lengthDecoder.nextInt()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (char) characterDecoder.nextInt();
        }
        return new String(arr);
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
    public String supportedEncoding() {
        return null;
    }

    @Override
    public void populateBufferFromStream(InputStream inputStream) throws IOException {
        inputStream = inputStreamWrapIfNecessary(inputStream);

        lengthDecoder.populateBufferFromStream(inputStream);
        characterDecoder.populateBufferFromStream(inputStream);
    }
}