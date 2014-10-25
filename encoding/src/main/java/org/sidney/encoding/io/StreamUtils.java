package org.sidney.encoding.io;

import java.io.IOException;
import java.io.OutputStream;

public class StreamUtils {
    public static void writeUnchecked(int value, OutputStream outputStream) {
        try {
            outputStream.write(value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeUnchecked(byte[] src, int off, int len, OutputStream outputStream) {
        try {
            outputStream.write(src, off, len);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
