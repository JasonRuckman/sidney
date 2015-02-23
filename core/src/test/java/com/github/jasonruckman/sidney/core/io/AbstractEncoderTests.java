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
import com.github.jasonruckman.sidney.core.io.input.Input;
import com.github.jasonruckman.sidney.core.io.output.Output;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public abstract class AbstractEncoderTests<E extends Encoder, D extends Decoder, T> {
  private Logger logger = LoggerFactory.getLogger(getRunningClass());

  protected abstract TriConsumer<Output, E, T> encodingFunction();

  protected abstract IntFunction<T> dataSupplier();

  protected abstract BiConsumer<D, T> consumeAndAssert();

  protected abstract List<EncoderDecoderPair<E, D>> pairs();

  protected abstract Class getRunningClass();

  @Test
  public void runAll() {
    for (EncoderDecoderPair<E, D> pair : pairs()) {
      logger.info(
          String.format(
              "Testing %s with %s.",
              pair.getEncoder().getClass(),
              pair.getDecoder().getClass()
          )
      );
      try {
        for (int i = 0; i < 8; i++) {
          logAndRun(pair, i);
        }

        for (int i = 8; i < 500000; i += 65536) {
          logAndRun(pair, i);
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Test
  public void runAllWithCompression() {
    for (EncoderDecoderPair<E, D> pair : pairs()) {
      logger.info(
          String.format(
              "Testing %s with %s.",
              pair.getEncoder().getClass(),
              pair.getDecoder().getClass()
          )
      );
      try {
        for (int i = 0; i < 8; i++) {
          logAndRunWithCompression(pair, i);
        }

        for (int i = 8; i < 500000; i += 65536) {
          logAndRunWithCompression(pair, i);
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  private void logAndRunWithCompression(EncoderDecoderPair<E, D> pair, int size) throws IOException {
    pair.getEncoder().reset();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    GZIPOutputStream gos = new GZIPOutputStream(baos);
    logger.debug(String.format("Testing size %s", size));
    T t = dataSupplier().apply(size);
    Output output = new Output();
    pair.getEncoder().reset();
    encodingFunction().accept(output, pair.getEncoder(), t);
    pair.getEncoder().strategy().write(pair.getEncoder(), output, gos);
    pair.getEncoder().flush(output);
    output.flush(gos);

    baos.close();
    gos.close();

    byte[] bytes = baos.toByteArray();
    logger.info(String.format("Num values %s size in bytes uncompressed: %s", size, bytes.length));
    Input input = new Input();
    ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
    GZIPInputStream gis = new GZIPInputStream(bais);
    pair.getDecoder().strategy().load(pair.getDecoder(), input, gis);
    consumeAndAssert().accept(pair.getDecoder(), t);
  }

  private void logAndRun(EncoderDecoderPair<E, D> pair, int size) throws IOException {
    pair.getEncoder().reset();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    logger.debug(String.format("Testing size %s", size));
    T t = dataSupplier().apply(size);
    Output output = new Output();
    pair.getEncoder().reset();
    encodingFunction().accept(output, pair.getEncoder(), t);
    pair.getEncoder().strategy().write(pair.getEncoder(), output, baos);
    pair.getEncoder().flush(output);
    output.flush(baos);

    baos.close();

    byte[] bytes = baos.toByteArray();
    logger.info(String.format("Num values %s size in bytes uncompressed: %s", size, bytes.length));
    Input input = new Input();
    ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
    pair.getDecoder().strategy().load(pair.getDecoder(), input, bais);
    consumeAndAssert().accept(pair.getDecoder(), t);
  }
}
