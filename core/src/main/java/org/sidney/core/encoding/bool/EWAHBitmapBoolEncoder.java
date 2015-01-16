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
package org.sidney.core.encoding.bool;

import com.googlecode.javaewah.EWAHCompressedBitmap;
import org.sidney.core.encoding.Encoding;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Encodes booleans into a compressed bitmap.
 */
public class EWAHBitmapBoolEncoder implements BoolEncoder {
    private EWAHCompressedBitmap currentBitmap;
    private int currentIndex = 0;

    public EWAHBitmapBoolEncoder() {
        currentBitmap = new EWAHCompressedBitmap();
    }

    @Override
    public void writeBool(boolean value) {
        if (value) {
            currentBitmap.set(currentIndex);
        }
        ++currentIndex;
    }

    @Override
    public void writeBools(boolean[] values) {
        for (boolean value : values) {
            writeBool(value);
        }
    }

    @Override
    public void reset() {
        currentBitmap = new EWAHCompressedBitmap();
        currentIndex = 0;
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        DataOutputStream dos = new DataOutputStream(outputStream);
        currentBitmap.serialize(dos);
        dos.flush();
    }

    @Override
    public String supportedEncoding() {
        return Encoding.BITMAP.name();
    }
}