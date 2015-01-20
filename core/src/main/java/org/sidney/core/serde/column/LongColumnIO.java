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
import org.sidney.core.io.int64.Int64Decoder;
import org.sidney.core.io.int64.Int64Encoder;

import java.util.Arrays;
import java.util.List;

public class LongColumnIO extends ColumnIO {
    private final Int64Encoder encoder;
    private final Int64Decoder decoder;

    public LongColumnIO(Int64Encoder encoder, Int64Decoder decoder) {
        this.encoder = encoder;
        this.decoder = decoder;
    }

    @Override
    public void writeLong(long value) {
        encoder.writeLong(value);
    }

    @Override
    public long readLong() {
        return decoder.nextLong();
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