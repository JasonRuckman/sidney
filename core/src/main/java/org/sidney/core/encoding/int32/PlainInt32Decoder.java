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
package org.sidney.core.encoding.int32;

import org.sidney.core.encoding.AbstractDecoder;
import org.sidney.core.encoding.Encoding;

public class PlainInt32Decoder extends AbstractDecoder implements Int32Decoder {
    @Override
    public String supportedEncoding() {
        return Encoding.PLAIN.name();
    }

    @Override
    public int nextInt() {
        return readIntLE();
    }

    @Override
    public int[] nextInts(int num) {
        int[] results = new int[num];
        for (int i = 0; i < num; i++) {
            results[i] = nextInt();
        }
        return results;
    }
}
