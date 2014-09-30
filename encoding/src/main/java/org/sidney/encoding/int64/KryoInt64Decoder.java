package org.sidney.encoding.int64;

import com.esotericsoftware.kryo.io.Input;

import java.io.IOException;
import java.io.InputStream;

public class KryoInt64Decoder implements Int64Decoder {
    private Input input;

    @Override
    public long nextLong() {
        return input.readLong();
    }

    @Override
    public long[] nextLongs(int num) {
        return input.readLongs(num);
    }

    @Override
    public void readFromStream(InputStream inputStream) throws IOException {
        input = new Input(inputStream);
    }
}
