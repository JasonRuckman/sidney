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
package org.sidney.core.encoding.float32;

import org.sidney.core.encoding.BaseEncoder;
import org.sidney.core.encoding.Encoding;
import org.sidney.core.encoding.int32.Int32Encoder;
import org.sidney.core.encoding.int32.PlainInt32Encoder;

import java.io.IOException;
import java.io.OutputStream;

public class PlainFloat32Encoder extends BaseEncoder implements Float32Encoder {
    private final Int32Encoder encoder = new PlainInt32Encoder();

    @Override
    public void writeFloat(float value) {
        encoder.writeInt(Float.floatToIntBits(value));
    }

    @Override
    public void writeFloats(float[] floats) {
        for (float v : floats) {
            writeFloat(v);
        }
    }

    @Override
    public String supportedEncoding() {
        return Encoding.PLAIN.name();
    }

    @Override
    public void reset() {
        encoder.reset();
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        encoder.writeToStream(outputStream);
    }
}