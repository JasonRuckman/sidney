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

public class ColumnWriter extends ColumnOperations {
  private final SidneyConf conf;

  public ColumnWriter(SidneyConf conf) {
    this.conf = conf;
  }

  public void writeBoolean(int index, boolean value) {
    Columns.ColumnIO columnIO = columnIOs.get(index);
    columnIO.writeBoolean(value);
  }

  public void writeInt(int index, int value) {
    Columns.ColumnIO columnIO = columnIOs.get(index);
    columnIO.writeInt(value);
  }

  public void writeLong(int index, long value) {
    Columns.ColumnIO columnIO = columnIOs.get(index);
    columnIO.writeLong(value);
  }

  public void writeFloat(int index, float value) {
    Columns.ColumnIO columnIO = columnIOs.get(index);
    columnIO.writeFloat(value);
  }

  public void writeDouble(int index, double value) {
    Columns.ColumnIO columnIO = columnIOs.get(index);
    columnIO.writeDouble(value);
  }

  public void writeBytes(int index, byte[] bytes) {
    Columns.ColumnIO columnIO = columnIOs.get(index);
    columnIO.writeBytes(bytes);
  }

  public void writeString(int index, String value) {
    Columns.ColumnIO columnIO = columnIOs.get(index);
    columnIO.writeString(value);
  }

  public void writeNotNull(int index) {
    columnIOs.get(index).writeNotNull();
  }

  public void writeNull(int index) {
    columnIOs.get(index).writeNull();
  }

  public void writeDefinition(int index, int definition) {
    columnIOs.get(index).writeDefinition(definition);
  }

  public void writeRepetitionCount(int index, int value) {
    columnIOs.get(index).writeRepetitionCount(value);
  }

  public void writeConcreteType(Class<?> type, int index, WriteContext context) {
    columnIOs.get(index).writeConcreteType(type, context);
  }

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
  public SidneyConf getConf() {
    return conf;
  }
}