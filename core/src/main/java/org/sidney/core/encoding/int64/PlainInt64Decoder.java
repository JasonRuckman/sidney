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
package org.sidney.core.encoding.int64;

import org.sidney.core.encoding.AbstractDecoder;
import org.sidney.core.encoding.Encoding;

public class PlainInt64Decoder extends AbstractDecoder implements Int64Decoder {
    @Override
    public long nextLong() {
        return readLongLE();
    }

    @Override
    public long[] nextLongs(int num) {
        long[] longs = new long[num];
        for (int i = 0; i < num; i++) {
            longs[i] = readLongLE();
        }
        return longs;
    }

    @Override
    public String supportedEncoding() {
        return Encoding.PLAIN.name();
    }
}
