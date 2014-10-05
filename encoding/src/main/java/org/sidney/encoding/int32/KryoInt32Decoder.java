package org.sidney.encoding.int32;

import com.esotericsoftware.kryo.io.Input;
import org.sidney.encoding.Encoding;

import java.io.IOException;
import java.io.InputStream;

public class KryoInt32Decoder implements Int32Decoder {
    private Input input;

    @Override
    public int nextInt() {
        return input.readInt();
    }

    @Override
    public int[] nextInts(int num) {
        return input.readInts(num);
    }

    @Override
    public void readFromStream(InputStream inputStream) throws IOException {
        input = new Input(inputStream);
    }

    @Override
    public Encoding supportedEncoding() {
        return Encoding.KRYO;
    }
}
