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
package org.sidney.core.io.bool;

import com.googlecode.javaewah.EWAHCompressedBitmap;
import com.googlecode.javaewah.IntIterator;
import org.sidney.core.Bytes;
import org.sidney.core.io.BaseDecoder;
import org.sidney.core.io.Encoding;

import java.io.*;

public class EWAHBitmap {
    public static class EWAHBitmapBoolDecoder extends BaseDecoder implements BoolDecoder {
        private EWAHCompressedBitmap bitmap = new EWAHCompressedBitmap();

        private int index = 0;
        private int nextTrueBit = -1;
        private IntIterator intIterator;

        @Override
        public boolean nextBool() {
            if (index++ == nextTrueBit) {
                if (intIterator.hasNext()) {
                    nextTrueBit = intIterator.next();
                }
                return true;
            }
            return false;
        }

        @Override
        public boolean[] nextBools(int num) {
            boolean[] booleans = new boolean[num];
            for (int i = 0; i < num; i++) {
                booleans[i] = nextBool();
            }
            return booleans;
        }

        @Override
        public void readFromStream(InputStream inputStream) throws IOException {
            int num = Bytes.readIntFromStream(inputStream);
            if(num > 0) {
                index = 0;
                nextTrueBit = -1;
                bitmap.deserialize(new DataInputStream(inputStream));
                intIterator = bitmap.intIterator();
                if (intIterator.hasNext()) {
                    nextTrueBit = intIterator.next();
                }
            }
        }
    }

    /**
     * Encodes booleans into a compressed bitmap.
     */
    public static class EWAHBitmapBoolEncoder implements BoolEncoder {
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
            currentBitmap.clear();
            currentIndex = 0;
        }

        @Override
        public void writeToStream(OutputStream outputStream) throws IOException {
            Bytes.writeIntToStream(currentIndex, outputStream);
            if(currentIndex > 0) {
                DataOutputStream dos = new DataOutputStream(outputStream);
                currentBitmap.serialize(dos);
            }
        }
    }
}
