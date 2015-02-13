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

import java.io.IOException;
import java.io.InputStream;

public interface ReadContext {
  /**
   * Load all columns from the stream
   *
   * @param inputStream the input stream
   */
  void loadFromInputStream(InputStream inputStream) throws IOException;

  /**
   * Read a boolean value from the column at the current index
   *
   * @return a boolean value
   */
  boolean readBoolean();

  /**
   * Read an byte value from the column at the current index
   *
   * @return a boolean value
   */
  byte readByte();

  /**
   * Read an short value from the column at the current index
   *
   * @return a short value
   */
  short readShort();

  /**
   * Read an char value from the column at the current index
   *
   * @return a char value
   */
  char readChar();

  /**
   * Read an int from the column at the current index
   *
   * @return a int value
   */
  int readInt();

  /**
   * Read an long from the column at the current index
   *
   * @return a long value
   */
  long readLong();

  /**
   * Read an float from the column at the current index
   *
   * @return a float value
   */
  float readFloat();

  /**
   * Read an double from the column at the current index
   *
   * @return a double value
   */
  double readDouble();

  /**
   * Read bytes from the column at the current index
   *
   * @return bytes
   */
  byte[] readBytes();

  /**
   * Read a string from the column at the current index
   *
   * @return string
   */
  String readString();

  /**
   * Read the null marker at the current index.  Do not call on non-nullable columns
   *
   * @return whether or not the next value is null
   */
  boolean shouldReadValue();

  /**
   * Read the type for a column at the current index.  Do not call on columns that do not require types.
   *
   * @return whether or not the next value is null
   */
  Class<?> readConcreteType();

  /**
   * Read the current repetition count at the current index.
   *
   * @return the repetition count
   */
  int readRepetitionCount();

  /**
   * Get the current page header
   */
  PageHeader getPageHeader();

  /**
   * Set the current page header
   */
  void setPageHeader(PageHeader pageHeader);

  /**
   * Increment to the next column
   */
  void incrementColumnIndex();

  /**
   * Increment by multiple columns
   */
  void incrementColumnIndex(int size);

  /**
   * Get the current column index
   */
  int getColumnIndex();

  /**
   * Set this index as the current column
   */
  void setColumnIndex(int newIndex);
}