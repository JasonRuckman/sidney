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
package com.github.jasonruckman.sidney.benchmarking;

import com.github.jasonruckman.sidney.core.io.Decoder;
import com.github.jasonruckman.sidney.core.io.Encoder;

import java.util.HashMap;
import java.util.Map;

public class BenchmarkingBase {
    private final ThreadLocal<Map<Class<? extends Encoder>, Encoder>> encoderCache = new ThreadLocal<Map<Class<? extends Encoder>, Encoder>>() {
        @Override
        protected Map<Class<? extends Encoder>, Encoder> initialValue() {
            return new HashMap<>();
        }
    };

    private final ThreadLocal<Map<Class<? extends Decoder>, Decoder>> decoderCache = new ThreadLocal<Map<Class<? extends Decoder>, Decoder>>() {
        @Override
        protected Map<Class<? extends Decoder>, Decoder> initialValue() {
            return new HashMap<>();
        }
    };

    public <T extends Encoder> T getEncoder(Class<T> clazz) {
        if (encoderCache.get().containsKey(clazz)) {
            return (T) encoderCache.get().get(clazz);
        } else {
            try {
                T encoder = (T) clazz.getConstructors()[0].newInstance();
                encoderCache.get().put(clazz, encoder);
                return encoder;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public <T extends Decoder> T getDecoder(Class<T> clazz) {
        if (decoderCache.get().containsKey(clazz)) {
            return (T) decoderCache.get().get(clazz);
        } else {
            try {
                T decoder = (T) clazz.getConstructors()[0].newInstance();
                decoderCache.get().put(clazz, decoder);
                return decoder;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
