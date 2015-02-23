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
import com.github.jasonruckman.sidney.core.io.input.Input;
import com.github.jasonruckman.sidney.core.io.int32.Int32Decoder;
import com.github.jasonruckman.sidney.core.io.int32.Int32Encoder;
import com.github.jasonruckman.sidney.core.io.output.Output;

import java.io.OutputStream;

public class MetaEncoding {
  private final Int32Encoder repetitionEncoder;
  private final Int32Decoder repetitionDecoder;
  private final BoolEncoder definitionEncoder;
  private final BoolDecoder definitionDecoder;

  public MetaEncoding(Int32Encoder repetitionEncoder,
                      Int32Decoder repetitionDecoder,
                      BoolEncoder definitionEncoder,
                      BoolDecoder definitionDecoder) {
    this.repetitionEncoder = repetitionEncoder;
    this.repetitionDecoder = repetitionDecoder;
    this.definitionEncoder = definitionEncoder;
    this.definitionDecoder = definitionDecoder;
  }

  public Int32Encoder getRepetitionEncoder() {
    return repetitionEncoder;
  }

  public Int32Decoder getRepetitionDecoder() {
    return repetitionDecoder;
  }

  public BoolEncoder getDefinitionEncoder() {
    return definitionEncoder;
  }

  public BoolDecoder getDefinitionDecoder() {
    return definitionDecoder;
  }

  public void loadRepetition(Input input) {
    repetitionDecoder.initialize(input);
  }

  public void loadDefinition(Input input) {
    definitionDecoder.initialize(input);
  }

  public void loadReferences(Input input) {

  }

  public void writeNullMarker(boolean isNotNull, Output output) {
    definitionEncoder.writeBool(isNotNull, output);
  }

  public boolean readNullMarker() {
    return definitionDecoder.nextBool();
  }

  public void writeDefinition(int definition, Output output) {
    throw new IllegalStateException();
  }

  public int readDefinition() {
    throw new IllegalStateException();
  }

  public void writeRepetitionCount(int count, Output output) {
    repetitionEncoder.writeInt(count, output);
  }

  public int readRepetitionCount() {
    return repetitionDecoder.nextInt();
  }
}