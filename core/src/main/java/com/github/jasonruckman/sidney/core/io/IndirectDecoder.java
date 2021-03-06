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

import com.github.jasonruckman.sidney.core.io.input.Input;

public abstract class IndirectDecoder implements Decoder {
  protected Input input;

  public void setInput(Input input) {
    this.input = input;
  }

  public void load() {

  }

  @Override
  public boolean isDirect() {
    return false;
  }

  @Override
  public IndirectDecoder asIndirect() {
    return this;
  }
}
