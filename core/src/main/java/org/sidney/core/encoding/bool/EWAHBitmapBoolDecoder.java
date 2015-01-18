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
import com.googlecode.javaewah.IntIterator;
import org.sidney.core.encoding.AbstractDecoder;
import org.sidney.core.encoding.Encoding;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class EWAHBitmapBoolDecoder extends AbstractDecoder implements BoolDecoder {
    private EWAHCompressedBitmap bitmap;
    private int index = 0;
    private int nextTrueBit;
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
        index = 0;
        nextTrueBit = -1;
        bitmap = new EWAHCompressedBitmap();
        bitmap.deserialize(new DataInputStream(inputStream));
        intIterator = bitmap.intIterator();
        if (intIterator.hasNext()) {
            nextTrueBit = intIterator.next();
        }
    }

    @Override
    public String supportedEncoding() {
        return Encoding.BITMAP.name();
    }
}