package org.sidney.core.encoding.io;

import org.sidney.core.Bytes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtils {
    public static void writeIntToStream(int value, OutputStream outputStream) throws IOException {
        byte[] arr = new byte[4];
        Bytes.writeIntOn4Bytes(value, arr, 0);
        outputStream.write(arr);
    }

    public static int readIntFromStream(InputStream inputStream) throws IOException {
        byte[] arr = new byte[4];
        inputStream.read(arr);
        return Bytes.bytesToInt(arr, 0);
    }
}
