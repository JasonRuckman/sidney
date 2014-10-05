package org.sidney.encoding.string;

import com.esotericsoftware.kryo.io.Input;
import org.sidney.encoding.Encoding;

import java.io.IOException;
import java.io.InputStream;

public class KryoStringDecoder implements StringDecoder {
    private Input input;

    @Override
    public String readString() {
        return input.readString();
    }

    @Override
    public String[] readStrings(int num) {
        String[] strings = new String[num];
        for(int i = 0; i < num; i++) {
            strings[i] = readString();
        }
        return strings;
    }

    @Override
    public void readFromStream(InputStream inputStream) throws IOException {
        input = new Input(inputStream);
    }

    @Override
    public String supportedEncoding() {
        return Encoding.KRYO.name();
    }
}
