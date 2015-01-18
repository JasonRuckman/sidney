/**
 * Copyright 2014 Jason Ruckman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sidney.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class Bytes {
    public static int sizeInBytes(int num, int bitwidth) {
        return num * (int) (Math.ceil(bitwidth / 8D));
    }

    public static void writeIntOn4Bytes(int value, byte[] bytes, int offset) {
        bytes[offset + 3] = (byte) (value >>> 24);
        bytes[offset + 2] = (byte) (value >>> 16);
        bytes[offset + 1] = (byte) (value >>> 8);
        bytes[offset] = (byte) value;
    }

    public static void writeLongOn8Bytes(long value, byte[] bytes, int offset) {
        bytes[offset + 7] = (byte) (value >>> 56);
        bytes[offset + 6] = (byte) (value >>> 48);
        bytes[offset + 5] = (byte) (value >>> 40);
        bytes[offset + 4] = (byte) (value >>> 32);
        bytes[offset + 3] = (byte) (value >>> 24);
        bytes[offset + 2] = (byte) (value >>> 16);
        bytes[offset + 1] = (byte) (value >>> 8);
        bytes[offset] = (byte) value;
    }

    public static int bytesToInt(byte[] bytes, int offset) {
        return ((bytes[offset + 3] & 255) << 24) +
                ((bytes[offset + 2] & 255) << 16) +
                ((bytes[offset + 1] & 255) << 8) +
                ((bytes[offset] & 255));
    }

    public static ByteArrayInputStream wrapInStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }

    public static int readIntFromStream(InputStream inputStream) throws IOException {
        byte[] arr = new byte[4];
        inputStream.read(arr);
        return bytesToInt(arr, 0);
    }

    public static void writeIntToStream(int value, OutputStream outputStream) throws IOException {
        byte[] arr = new byte[4];
        writeIntOn4Bytes(value, arr, 0);
        outputStream.write(arr);
    }

    public static void writeStringToStream(String value, OutputStream outputStream) throws IOException {
        byte[] bytes = value.getBytes(Charset.forName("UTF-8"));
        writeIntToStream(bytes.length, outputStream);
        outputStream.write(bytes);
    }

    public static String readStringFromStream(InputStream inputStream) throws IOException {
        int length = readIntFromStream(inputStream);
        byte[] bytes = new byte[length];
        inputStream.read(bytes);
        return new String(bytes, Charset.forName("UTF-8"));
    }
}