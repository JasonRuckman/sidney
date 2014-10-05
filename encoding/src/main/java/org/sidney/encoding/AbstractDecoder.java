package org.sidney.encoding;

import parquet.bytes.LittleEndianDataInputStream;

import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractDecoder implements Decoder {
    protected byte[] buffer;
    protected int position = 0;

    @Override
    public void readFromStream(InputStream inputStream) throws IOException {
        position = 0;

        int bufferSize = new LittleEndianDataInputStream(inputStream).readInt();
        buffer = new byte[bufferSize];
        inputStream.read(buffer);
    }
}