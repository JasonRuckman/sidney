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

import org.sidney.core.encoding.BaseEncoder;
import org.sidney.core.encoding.Encoding;

import java.io.IOException;
import java.io.OutputStream;

public class BitPackingBoolEncoder extends BaseEncoder implements BoolEncoder {
    private int currentBitIndex = 0;

    @Override
    public void writeBool(boolean value) {
        ensureCapacity(1);
        if (value) {
            getBuffer()[getPosition()] |= 1 << currentBitIndex;
        }
        if (++currentBitIndex == 8) {
            incrementPosition(1);
            currentBitIndex = 0;
        }
    }

    @Override
    public void writeBools(boolean[] values) {
        //TODO: Optimize for packing 8 at a time
        for (boolean b : values) {
            writeBool(b);
        }
    }

    @Override
    public String supportedEncoding() {
        return Encoding.BITPACKED.name();
    }

    @Override
    public void reset() {
        super.reset();
        currentBitIndex = 0;
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        //account for the current byte we are on
        incrementPosition(1);
        super.writeToStream(outputStream);
    }
}
