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
package org.sidney.core.encoding.float64;

import org.sidney.core.encoding.AbstractDecoder;
import org.sidney.core.encoding.Encoding;
import org.sidney.core.encoding.int64.Int64Decoder;
import org.sidney.core.encoding.int64.PlainInt64Decoder;

import java.io.IOException;
import java.io.InputStream;

public class PlainFloat64Decoder extends AbstractDecoder implements Float64Decoder {
    private final Int64Decoder decoder = new PlainInt64Decoder();

    @Override
    public double nextDouble() {
        return Double.longBitsToDouble(decoder.nextLong());
    }

    @Override
    public double[] nextDoubles(int num) {
        double[] results = new double[num];
        for (int i = 0; i < num; i++) {
            results[i] = nextDouble();
        }
        return results;
    }

    @Override
    public String supportedEncoding() {
        return Encoding.PLAIN.name();
    }

    @Override
    public void populateBufferFromStream(InputStream inputStream) throws IOException {
        decoder.populateBufferFromStream(inputStream);
    }
}
