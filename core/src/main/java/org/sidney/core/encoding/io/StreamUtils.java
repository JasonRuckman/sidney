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
