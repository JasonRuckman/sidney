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
import com.github.jasonruckman.sidney.core.io.Encoder;
import com.github.jasonruckman.sidney.core.serde.serializer.ColumnOperations;

import java.io.IOException;
import java.io.OutputStream;

public class WriteContextImpl extends ColumnOperations implements WriteContext {
  private final Meta meta;

  public WriteContextImpl(PageHeader pageHeader, SidneyConf conf) {
    super(conf);
    setPageHeader(pageHeader);
    meta = new WriteMetaImpl(this);
  }

  public void writeBool(boolean value) {
    columnIOs.get(getColumnIndex()).writeBoolean(value);
  }

  public void writeByte(byte value) {
    writeIntLike(value);
  }

  public void writeChar(char value) {
    writeIntLike(value);
  }

  public void writeShort(short value) {
    writeIntLike(value);
  }

  public void writeInt(int value) {
    writeIntLike(value);
  }

  public void writeLong(long value) {
    columnIOs.get(getColumnIndex()).writeLong(value);
  }

  public void writeFloat(float value) {
    columnIOs.get(getColumnIndex()).writeFloat(value);
  }

  public void writeDouble(double value) {
    columnIOs.get(getColumnIndex()).writeDouble(value);
  }

  public void writeBytes(byte[] value) {
    columnIOs.get(getColumnIndex()).writeBytes(value);
  }

  public void writeString(String value) {
    columnIOs.get(getColumnIndex()).writeString(value);
  }

  public <T> boolean shouldWriteValue(T value) {
    if (value == null) {
      columnIOs.get(getColumnIndex()).writeNull();
      return false;
    }
    columnIOs.get(getColumnIndex()).writeNotNull();
    return true;
  }

  public void writeNotNull() {
    columnIOs.get(getColumnIndex()).writeNotNull();
  }

  public void writeNull() {
    columnIOs.get(getColumnIndex()).writeNull();
  }

  public void writeDefinition(int definition) {
    columnIOs.get(getColumnIndex()).writeDefinition(definition);
  }

  @Override
  public void flushToOutputStream(OutputStream outputStream) throws IOException {
    for (Columns.ColumnIO columnIO : columnIOs) {
      columnIO.getEncoding().writeToStream(outputStream);
      columnIO.getEncoding().reset();
      for (Encoder encoder : columnIO.getEncoders()) {
        encoder.writeToStream(outputStream);
        encoder.reset();
      }
    }
  }

  @Override
  public Meta getMeta() {
    return meta;
  }

  Columns.ColumnIO column(int pos) {
    return columnIOs.get(pos);
  }

  private void writeIntLike(int value) {
    columnIOs.get(getColumnIndex()).writeInt(value);
  }
}