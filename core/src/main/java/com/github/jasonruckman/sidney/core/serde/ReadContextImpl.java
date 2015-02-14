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

import com.github.jasonruckman.sidney.core.SidneyConf;
import com.github.jasonruckman.sidney.core.io.Columns;
import com.github.jasonruckman.sidney.core.io.Decoder;
import com.github.jasonruckman.sidney.core.serde.serializer.ColumnOperations;

import java.io.IOException;
import java.io.InputStream;

public class ReadContextImpl extends ColumnOperations implements ReadContext {
  private final Meta meta;

  public ReadContextImpl(SidneyConf conf) {
    super(conf);

    meta = new ReadMetaImpl(this);
  }

  @Override
  public void loadFromInputStream(InputStream inputStream) throws IOException {
    for (Columns.ColumnIO columnIO : columnIOs) {
      columnIO.getEncoding().readFromStream(inputStream);

      for (Decoder decoder : columnIO.getDecoders()) {
        decoder.readFromStream(inputStream);
      }
    }
  }

  public boolean readBoolean() {
    return columnIOs.get(getColumnIndex()).readBoolean();
  }

  public byte readByte() {
    return (byte) columnIOs.get(getColumnIndex()).readInt();
  }

  public short readShort() {
    return (short) columnIOs.get(getColumnIndex()).readInt();
  }

  public char readChar() {
    return (char) columnIOs.get(getColumnIndex()).readInt();
  }

  public int readInt() {
    return columnIOs.get(getColumnIndex()).readInt();
  }

  public long readLong() {
    return columnIOs.get(getColumnIndex()).readLong();
  }

  public float readFloat() {
    return columnIOs.get(getColumnIndex()).readFloat();
  }

  public double readDouble() {
    return columnIOs.get(getColumnIndex()).readDouble();
  }

  public byte[] readBytes() {
    return columnIOs.get(getColumnIndex()).readBytes();
  }

  public String readString() {
    return columnIOs.get(getColumnIndex()).readString();
  }

  public boolean shouldReadValue() {
    return columnIOs.get(getColumnIndex()).readNullMarker();
  }

  @Override
  public Meta getMeta() {
    return meta;
  }

  public int readDefinition() {
    return columnIOs.get(getColumnIndex()).readDefinition();
  }

  Columns.ColumnIO column(int pos) {
    return columnIOs.get(pos);
  }
}