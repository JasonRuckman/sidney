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
package com.github.jasonruckman.sidney.core;

import com.github.jasonruckman.sidney.core.serde.Reader;
import com.github.jasonruckman.sidney.core.serde.Writer;
import com.github.jasonruckman.sidney.core.type.TypeToken;

public class JavaSid extends AbstractSid {
  /**
   * Creates a new {@link com.github.jasonruckman.sidney.core.serde.Writer} for the given type token
   *
   * @param token a token capturing the type
   * @return a new {@link com.github.jasonruckman.sidney.core.serde.Writer} bound to the given type
   */
  public <T> Writer<T> newWriter(TypeToken<T> token) {
    return createWriter(token);
  }

  /**
   * Creates a new {@link com.github.jasonruckman.sidney.core.serde.Reader} for the given type
   *
   * @param token a token capturing the type
   * @return a new {@link com.github.jasonruckman.sidney.core.serde.Reader} bound to the given type
   */
  public <T> Reader<T> newReader(TypeToken<T> token) {
    return createReader(token);
  }
}