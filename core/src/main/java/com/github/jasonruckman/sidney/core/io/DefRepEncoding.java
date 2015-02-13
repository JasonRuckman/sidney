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

import com.github.jasonruckman.sidney.core.io.int32.Int32Decoder;
import com.github.jasonruckman.sidney.core.io.int32.Int32Encoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class DefRepEncoding {
  private final Int32Encoder repetitionEncoder;
  private final Int32Decoder repetitionDecoder;

  protected DefRepEncoding(Int32Encoder repetitionEncoder, Int32Decoder repetitionDecoder) {
    this.repetitionEncoder = repetitionEncoder;
    this.repetitionDecoder = repetitionDecoder;
  }

  public int readRepetitionCount() {
    return repetitionDecoder.nextInt();
  }

  public void writeRepetitionCount(int count) {
    repetitionEncoder.writeInt(count);
  }

  public void readFromStream(InputStream inputStream) throws IOException {
    repetitionDecoder.readFromStream(inputStream);
  }

  public void writeToStream(OutputStream outputStream) throws IOException {
    repetitionEncoder.writeToStream(outputStream);
  }

  public void writeNullMarker(boolean value) {
    throw new IllegalStateException();
  }

  public boolean readNullMarker() {
    throw new IllegalStateException();
  }

  public void writeDefinition(int definition) {
    throw new IllegalStateException();
  }

  public int readDefinition() {
    throw new IllegalStateException();
  }

  public void reset() {
    repetitionEncoder.reset();
  }
}