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
package org.sidney.core.io.int64;

import org.sidney.core.io.BaseEncoder;
import org.sidney.core.io.Encoding;

public class PlainInt64Encoder extends BaseEncoder implements Int64Encoder {
    @Override
    public void writeLong(long value) {
        writeLongInternal(value);
    }

    @Override
    public void writeLongs(long[] values) {
        for (long l : values) {
            writeLong(l);
        }
    }

    @Override
    public String supportedEncoding() {
        return Encoding.PLAIN.name();
    }
}