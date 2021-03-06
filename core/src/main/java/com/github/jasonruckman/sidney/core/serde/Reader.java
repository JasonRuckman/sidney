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
package com.github.jasonruckman.sidney.core.serde;

import java.io.InputStream;
import java.util.List;

public interface Reader<T> {
  /**
   * Check for new items
   *
   * @return whether there are more items
   */
  boolean hasNext();

  /**
   * Read the next item from the stream
   *
   * @return the next item
   */
  T read();

  /**
   * Read all items from the stream
   *
   * @return all items
   */
  List<T> readAll();

  /**
   * Open the given {@link java.io.InputStream} for reading.
   */
  void open(InputStream inputStream);

  /**
   * Marks this reader as closed, does not close the underlying stream
   */
  void close();
}
