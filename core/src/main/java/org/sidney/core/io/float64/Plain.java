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
package org.sidney.core.io.float64;

import org.sidney.core.io.Encoding;
import org.sidney.core.io.int64.Int64Decoder;
import org.sidney.core.io.int64.Int64Encoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Plain {
    public static class PlainFloat64Decoder implements Float64Decoder {
        private final Int64Decoder decoder = new org.sidney.core.io.int64.Plain.PlainInt64Decoder();

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
        public void readFromStream(InputStream inputStream) throws IOException {
            decoder.readFromStream(inputStream);
        }
    }

    public static class PlainFloat64Encoder implements Float64Encoder {
        private final Int64Encoder encoder = new org.sidney.core.io.int64.Plain.PlainInt64Encoder();

        @Override
        public void writeDouble(double value) {
            encoder.writeLong(Double.doubleToLongBits(value));
        }

        @Override
        public void writeDoubles(double[] values) {
            for (double value : values) {
                writeDouble(value);
            }
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
}
