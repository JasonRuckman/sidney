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

import java.io.IOException;
import java.io.InputStream;
import java.nio.BufferUnderflowException;
import static com.github.jasonruckman.sidney.core.Bytes.*;

public abstract class BaseDecoder implements Decoder {
  private byte[] buffer;
  private int position = 0;

  public byte[] getBuffer() {
    return buffer;
  }

  public void setBuffer(byte[] buffer) {
    this.buffer = buffer;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public void incrementPosition(int size) {
    position += size;
  }

  @Override
  public void readFromStream(InputStream inputStream) throws IOException {
    setPosition(0);
    int bufferSize = readIntFromStream(inputStream);
    buffer = new byte[bufferSize];
    readFully(buffer, inputStream);
  }

  protected byte readByteFromBuffer() {
    require(1);
    return buffer[position++];
  }

  protected boolean readBooleanFromBuffer() {
    require(1);
    return buffer[position++] > 0;
  }

  protected byte[] readBytesFromBuffer(int num) {
    require(num);
    byte[] buf = new byte[num];
    System.arraycopy(getBuffer(), getPosition(), buf, 0, num);
    incrementPosition(num);
    return buf;
  }

  protected int readIntFromBuffer() {
    require(4);
    int i = readInt(getBuffer(), getPosition());
    position += 4;
    return i;
  }

  protected long readLongFromBuffer() {
    require(8);
    long l = readLong(getBuffer(), getPosition());
    position += 8;
    return l;
  }

  private void require(int size) {
    if (position + size > buffer.length) {
      throw new BufferUnderflowException();
    }
  }
}