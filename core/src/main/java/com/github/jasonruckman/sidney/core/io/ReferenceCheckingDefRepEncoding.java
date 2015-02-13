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

import com.github.jasonruckman.sidney.core.io.bool.BoolDecoder;
import com.github.jasonruckman.sidney.core.io.bool.BoolEncoder;
import com.github.jasonruckman.sidney.core.io.int32.Int32Decoder;
import com.github.jasonruckman.sidney.core.io.int32.Int32Encoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ReferenceCheckingDefRepEncoding extends DefRepEncoding {
  private final Int32Encoder definitionEncoder;
  private final Int32Decoder definitionDecoder;
  private final BoolDecoder nullDecoder;
  private final BoolEncoder nullEncoder;

  public ReferenceCheckingDefRepEncoding(Int32Encoder repetitionEncoder,
                                         Int32Decoder repetitionDecoder,
                                         Int32Encoder definitionEncoder,
                                         Int32Decoder definitionDecoder, BoolDecoder nullDecoder, BoolEncoder nullEncoder) {
    super(repetitionEncoder, repetitionDecoder);
    this.definitionEncoder = definitionEncoder;
    this.definitionDecoder = definitionDecoder;
    this.nullDecoder = nullDecoder;
    this.nullEncoder = nullEncoder;
  }

  @Override
  public void readFromStream(InputStream inputStream) throws IOException {
    definitionDecoder.readFromStream(inputStream);
    nullDecoder.readFromStream(inputStream);
    super.readFromStream(inputStream);
  }

  @Override
  public void writeToStream(OutputStream outputStream) throws IOException {
    definitionEncoder.writeToStream(outputStream);
    nullEncoder.writeToStream(outputStream);
    super.writeToStream(outputStream);
  }

  @Override
  public void writeNullMarker(boolean value) {
    nullEncoder.writeBool(value);
  }

  @Override
  public boolean readNullMarker() {
    return nullDecoder.nextBool();
  }

  @Override
  public void writeDefinition(int value) {
    definitionEncoder.writeInt(value);
  }

  @Override
  public int readDefinition() {
    return definitionDecoder.nextInt();
  }

  @Override
  public void reset() {
    definitionEncoder.reset();
    nullEncoder.reset();
    super.reset();
  }
}
