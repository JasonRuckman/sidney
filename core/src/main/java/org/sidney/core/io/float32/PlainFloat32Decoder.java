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
package org.sidney.core.io.float32;

import org.sidney.core.io.BaseDecoder;
import org.sidney.core.io.Encoding;
import org.sidney.core.io.int32.Int32Decoder;
import org.sidney.core.io.int32.PlainInt32Decoder;

import java.io.IOException;
import java.io.InputStream;

public class PlainFloat32Decoder extends BaseDecoder implements Float32Decoder {
    private Int32Decoder int32Decoder = new PlainInt32Decoder();

    @Override
    public float nextFloat() {
        return Float.intBitsToFloat(int32Decoder.nextInt());
    }

    @Override
    public float[] nextFloats(int num) {
        float[] floats = new float[num];
        for (int i = 0; i < num; i++) {
            floats[i] = nextFloat();
        }
        return floats;
    }

    @Override
    public String supportedEncoding() {
        return Encoding.PLAIN.name();
    }

    @Override
    public void readFromStream(InputStream inputStream) throws IOException {
        int32Decoder.readFromStream(inputStream);
    }
}