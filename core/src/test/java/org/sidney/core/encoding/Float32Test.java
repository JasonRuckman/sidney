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
package org.sidney.core.encoding;

import org.junit.Assert;
import org.sidney.core.encoding.float32.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Float32Test extends AbstractEncoderTests<Float32Encoder, Float32Decoder, float[]> {
    private final List<EncoderDecoderPair<Float32Encoder, Float32Decoder>> pairs = Arrays.asList(
            new EncoderDecoderPair<Float32Encoder, Float32Decoder>(new PlainFloat32Encoder(), new PlainFloat32Decoder()),
            new EncoderDecoderPair<Float32Encoder, Float32Decoder>(new RLEFloat32Encoder(), new RLEFloat32Decoder())
    );

    protected BiConsumer<Float32Encoder, float[]> encodingFunction() {
        return new BiConsumer<Float32Encoder, float[]>() {
            @Override
            public void accept(Float32Encoder encoder, float[] floats) {
                encoder.writeFloats(floats);
            }
        };
    }

    @Override
    protected IntFunction<float[]> dataSupplier() {
        return new IntFunction<float[]>() {
            @Override
            public float[] apply(int size) {
                float[] floats = new float[size];
                Random random = new Random(11L);
                for (int i = 0; i < size; i++) {
                    floats[i] = random.nextFloat();
                }
                return floats;
            }
        };
    }

    @Override
    protected BiConsumer<Float32Decoder, float[]> consumeAndAssert() {
        return new BiConsumer<Float32Decoder, float[]>() {
            @Override
            public void accept(Float32Decoder decoder, float[] floats) {
                float[] results = decoder.nextFloats(floats.length);
                Assert.assertArrayEquals(floats, results, 0);
            }
        };
    }

    @Override
    protected List<EncoderDecoderPair<Float32Encoder, Float32Decoder>> pairs() {
        return pairs;
    }

    @Override
    protected Class getRunningClass() {
        return this.getClass();
    }
}
