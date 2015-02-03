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
package com.github.jasonruckman.sidney.core.io.int32;

import com.github.jasonruckman.sidney.core.io.BaseDecoder;
import com.github.jasonruckman.sidney.core.io.BaseEncoder;

public class Plain {
    public static class PlainInt32Decoder extends BaseDecoder implements Int32Decoder {
        @Override
        public int nextInt() {
            return readIntFromBuffer();
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

    public static class PlainInt32Encoder extends BaseEncoder implements Int32Encoder {
        @Override
        public void writeInt(int value) {
            writeIntToBuffer(value);
        }

        @Override
        public void writeInts(int[] values) {
            for (int value : values) {
                writeInt(value);
            }
        }
    }
}