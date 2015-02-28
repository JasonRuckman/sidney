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

import com.github.jasonruckman.sidney.core.io.output.Output;

public abstract class IndirectEncoder implements Encoder {
  protected Output output;

  public void flush() {

  }

  @Override
  public IndirectEncoder asIndirect() {
    return this;
  }

  @Override
  public DirectEncoder asDirect() {
    throw new IllegalStateException();
  }

  public Output getOutput() {
    return output;
  }

  public void setOutput(Output output) {
    this.output = output;
  }

  @Override
  public void reset() {

  }

  @Override
  public boolean isDirect() {
    return false;
  }
}