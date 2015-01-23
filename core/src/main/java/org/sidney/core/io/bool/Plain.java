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
package org.sidney.core.io.bool;

import org.sidney.core.io.BaseDecoder;
import org.sidney.core.io.BaseEncoder;
import org.sidney.core.io.Encoding;

public class Plain {
    public static class PlainBoolDecoder extends BaseDecoder implements BoolDecoder {
        @Override
        public boolean nextBool() {
            return readBoolean();
        }

        @Override
        public boolean[] nextBools(int num) {
            boolean[] booleans = new boolean[num];
            for (int i = 0; i < num; i++) {
                booleans[i] = readBoolean();
            }
            return booleans;
        }

        @Override
        public String supportedEncoding() {
            return Encoding.PLAIN.name();
        }
    }

    public static class PlainBoolEncoder extends BaseEncoder implements BoolEncoder {
        @Override
        public void writeBool(boolean value) {
            writeBoolean(value);
        }

        @Override
        public void writeBools(boolean[] values) {
            for (boolean value : values) {
                writeBool(value);
            }
        }

        @Override
        public String supportedEncoding() {
            return Encoding.PLAIN.name();
        }
    }
}