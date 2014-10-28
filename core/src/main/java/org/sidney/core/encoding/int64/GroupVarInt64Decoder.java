package org.sidney.core.encoding.int64;

import org.sidney.core.encoding.AbstractDecoder;
import org.sidney.core.encoding.Encoding;

import java.io.IOException;
import java.io.InputStream;

public class GroupVarInt64Decoder extends AbstractDecoder implements Int64Decoder {
    private long[] buffer = new long[4];
    private int currentIndex = 0;
    private byte[] decodingBuffer = new byte[32];

    @Override
    public long nextLong() {
        return 0;
    }

    @Override
    public long[] nextLongs(int num) {
        return new long[0];
    }

    @Override
    public String supportedEncoding() {
        return Encoding.GROUPVARINT.name();
    }

    private void loadNextBlock() {
        byte prefixOne = readByte();
        byte prefixTwo = readByte();
        int numBytesFirst = prefixOne & 15;
        int numBytesSecond = (prefixOne >>> 4) & 15;
        int numBytesThird = prefixTwo & 15;
        int numBytesFourth = (prefixTwo >>> 4) & 15;
        byte[] buf = readBytesInternal(numBytesFirst + numBytesSecond + numBytesThird + numBytesFourth);
        int offset = 0;
        for(int i = 0; i < buffer.length; i++) {

        }

    }

    private long readOnOneByte(byte[] buf, int offset) {
        return ((long) buf[offset]) & 255;
    }

    private long readOnTwoBytes(byte[] buf, int offset) {
        return ((long)((buf[offset] & 255) << 4) | ((long)(buf[offset + 1] & 255)) & 65535);
    }

    @Override
    public void readFromStream(InputStream inputStream) throws IOException {
        super.readFromStream(inputStream);
        this.currentIndex = 0;
        loadNextBlock();
    }
}