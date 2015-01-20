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
package org.sidney.core.serde.column;

import org.sidney.core.io.Decoder;
import org.sidney.core.io.Encoder;
import org.sidney.core.io.float64.Float64Decoder;
import org.sidney.core.io.float64.Float64Encoder;

import java.util.Arrays;
import java.util.List;

public class DoubleColumnIO extends ColumnIO {
    private final Float64Encoder encoder;
    private final Float64Decoder decoder;

    public DoubleColumnIO(Float64Encoder encoder, Float64Decoder decoder) {
        this.encoder = encoder;
        this.decoder = decoder;
    }

    @Override
    public void writeDouble(double value) {
        encoder.writeDouble(value);
    }

    @Override
    public double readDouble() {
        return decoder.nextDouble();
    }

    @Override
    public List<Encoder> getEncoders() {
        return Arrays.asList((Encoder) encoder);
    }

    @Override
    public List<Decoder> getDecoders() {
        return Arrays.asList((Decoder) decoder);
    }
}
