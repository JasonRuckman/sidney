package org.sidney.encoding;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZip implements Encoder, Decoder, Compressing {
    private Encoder delegateEncoder;
    private Decoder delegateDecoder;

    public GZip(Encoder delegateEncoder, Decoder delegateDecoder) {
        this.delegateEncoder = delegateEncoder;
        this.delegateDecoder = delegateDecoder;
    }

    @Override
    public void reset() {
        delegateEncoder.reset();
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        GZIPOutputStream gos = new GZIPOutputStream(outputStream);
        delegateEncoder.writeToStream(gos);
        gos.finish();
    }

    @Override
    public void readFromStream(InputStream inputStream) throws IOException {
        GZIPInputStream gis = new GZIPInputStream(inputStream);
        delegateDecoder.readFromStream(gis);
    }

    @Override
    public String supportedEncoding() {
        return String.format("GZIP-%s", delegateEncoder.supportedEncoding());
    }

    @Override
    public Algorithm algorithm() {
        return Algorithm.GZIP;
    }
}
