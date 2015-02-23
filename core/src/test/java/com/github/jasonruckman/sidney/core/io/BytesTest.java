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
package com.github.jasonruckman.sidney.core.io;

import com.github.jasonruckman.sidney.core.IntFunction;
import com.github.jasonruckman.sidney.core.io.bytes.*;
import com.github.jasonruckman.sidney.core.io.output.Output;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BytesTest extends AbstractEncoderTests<BytesEncoder, BytesDecoder, byte[]> {
  private final List<EncoderDecoderPair<BytesEncoder, BytesDecoder>> pairs = Arrays.asList(
      new EncoderDecoderPair<BytesEncoder, BytesDecoder>(new Plain.ByteArrayEncoder(), new Plain.ByteArrayDecoder())
  );

  protected TriConsumer<Output, BytesEncoder, byte[]> encodingFunction() {
    return new TriConsumer<Output, BytesEncoder, byte[]>() {
      @Override
      public void accept(Output output, BytesEncoder encoder, byte[] bytes) {
        encoder.writeBytes(bytes, output);
      }
    };
  }

  @Override
  protected IntFunction<byte[]> dataSupplier() {
    return new IntFunction<byte[]>() {
      @Override
      public byte[] apply(int size) {
        Random random = new Random(11L);
        byte[] bytes = new byte[size];
        random.nextBytes(bytes);
        return bytes;
      }
    };
  }

  @Override
  protected BiConsumer<BytesDecoder, byte[]> consumeAndAssert() {
    return new BiConsumer<BytesDecoder, byte[]>() {
      @Override
      public void accept(BytesDecoder decoder, byte[] bytes) {
        decoder.readBytes(bytes.length);
      }
    };
  }

  @Override
  protected List<EncoderDecoderPair<BytesEncoder, BytesDecoder>> pairs() {
    return pairs;
  }

  @Override
  protected Class getRunningClass() {
    return BytesTest.class;
  }
}