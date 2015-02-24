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

import com.github.jasonruckman.sidney.core.Configuration;
import com.github.jasonruckman.sidney.core.io.*;
import com.github.jasonruckman.sidney.core.io.input.Input;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Contexts {
  public static interface ReadContext {
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

    Meta getMeta();

    public interface Meta {
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
    }
  }

  public static class ReadContextImpl extends ColumnGenerator implements ReadContext {
    private final Meta meta;

    public ReadContextImpl(Configuration conf) {
      super(conf);

      meta = new ReadMetaImpl(this);
    }

    @Override
    public void loadFromInputStream(InputStream inputStream) throws IOException {
      for (Columns.ColumnIO columnIO : columnIOs) {
        if (!columnIO.shouldLoadColumn()) {
          continue;
        }

        Decoder decoder = columnIO.getDecoder();
        if (!decoder.isDirect()) {
          Input input = getConf().newInput();
          input.initialize(inputStream);
          decoder.asIndirect().setInput(input);
          decoder.asIndirect().load();
        } else {
          ((DirectDecoder) decoder).load(inputStream);
        }
      }

      for (Columns.ColumnIO columnIO : columnIOs) {
        Decoder decoder = columnIO.getEncoding().getDefinitionDecoder();

        if (!decoder.isDirect()) {
          Input input = getConf().newInput();
          input.initialize(inputStream);
          decoder.asIndirect().setInput(input);
          decoder.asIndirect().load();
        } else {
          ((DirectDecoder) decoder).load(inputStream);
        }
      }

      for (Columns.ColumnIO columnIO : columnIOs) {
        Decoder decoder = columnIO.getEncoding().getRepetitionDecoder();
        if (!decoder.isDirect()) {
          Input input = getConf().newInput();
          input.initialize(inputStream);
          decoder.asIndirect().setInput(input);
          decoder.asIndirect().load();
        } else {
          ((DirectDecoder) decoder).load(inputStream);
        }
      }

      for (Columns.ColumnIO columnIO : columnIOs) {
        if (!columnIO.shouldLoadReferences()) {
          continue;
        }

        ReferencesMetaEncoding rme = (ReferencesMetaEncoding) columnIO.getEncoding();
        Decoder decoder = rme.getReferencesDecoder();
        if (!decoder.isDirect()) {
          Input input = getConf().newInput();
          input.initialize(inputStream);
          decoder.asIndirect().setInput(input);
          decoder.asIndirect().load();
        } else {
          ((DirectDecoder) decoder).load(inputStream);
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

  public static interface WriteContext {
    /**
     * Write bool to current column
     *
     * @param value
     */
    void writeBool(boolean value);

    /**
     * Write byte to current column
     *
     * @param value the byte
     */
    void writeByte(byte value);

    /**
     * Write char to current column
     *
     * @param value the char
     */
    void writeChar(char value);

    /**
     * Write short to current column
     *
     * @param value the short
     */
    void writeShort(short value);

    /**
     * Write int to current column
     *
     * @param value the int
     */
    void writeInt(int value);

    /**
     * Write long to current column
     *
     * @param value the long
     */
    void writeLong(long value);

    /**
     * Write float to current column
     *
     * @param value the float
     */
    void writeFloat(float value);

    /**
     * Write double to current column
     *
     * @param value the double
     */
    void writeDouble(double value);

    /**
     * Write bytes to current column
     *
     * @param value the bytes
     */
    void writeBytes(byte[] value);

    /**
     * Write string to current column
     *
     * @param value the string
     */
    void writeString(String value);

    /**
     * If the value is null, write a null marker
     *
     * @return if the value is not null
     */
    <T> boolean shouldWriteValue(T value);

    /**
     * Get the current column index
     *
     * @return the current column index
     */
    int getColumnIndex();

    /**
     * Set the column index to a given value
     *
     * @param index the new value
     */
    void setColumnIndex(int index);

    /**
     * Increment the column index by one
     */
    void incrementColumnIndex();

    /**
     * Increment the column index by a given amount
     *
     * @param size size to increment by
     */
    void incrementColumnIndex(int size);

    /**
     * Get the current page header
     *
     * @return the current page header
     */
    PageHeader getPageHeader();

    /**
     * Set the current page header
     *
     * @param pageHeader the new page header
     */
    void setPageHeader(PageHeader pageHeader);

    /**
     * Flush all columns to the output stream
     *
     * @param outputStream the output stream
     * @throws java.io.IOException
     */
    void flushToOutputStream(OutputStream outputStream) throws IOException;

    Meta getMeta();

    public interface Meta {
      /**
       * Write the concrete type
       */
      void writeConcreteType(Class<?> type);

      /**
       * Write the repetition count to the current column
       *
       * @param count the repetition count
       */
      void writeRepetitionCount(int count);
    }
  }

  public static class WriteContextImpl extends ColumnGenerator implements WriteContext {
    private final Meta meta;

    public WriteContextImpl(PageHeader pageHeader, Configuration conf) {
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
      columnIOs.get(getColumnIndex()).writeReference(definition);
    }

    @Override
    public void flushToOutputStream(OutputStream outputStream) throws IOException {
      for (Columns.ColumnIO columnIO : columnIOs) {
        if (!columnIO.shouldLoadColumn()) {
          continue;
        }

        Encoder encoder = columnIO.getEncoder();
        encoder.flush();

        if (encoder.isDirect()) {
          encoder.asDirect().flush(outputStream);
        } else {
          encoder.asIndirect().getOutput().flush(outputStream);
          encoder.asIndirect().getOutput().clear();
        }

        encoder.reset();
      }

      for (Columns.ColumnIO columnIO : columnIOs) {
        Encoder encoder = columnIO.getEncoding().getDefinitionEncoder();
        encoder.flush();

        if (encoder.isDirect()) {
          encoder.asDirect().flush(outputStream);
        } else {
          encoder.asIndirect().getOutput().flush(outputStream);
          encoder.asIndirect().getOutput().clear();
        }

        encoder.reset();
      }

      for (Columns.ColumnIO columnIO : columnIOs) {
        Encoder encoder = columnIO.getEncoding().getRepetitionEncoder();
        encoder.flush();

        if (encoder.isDirect()) {
          encoder.asDirect().flush(outputStream);
        } else {
          encoder.asIndirect().getOutput().flush(outputStream);
          encoder.asIndirect().getOutput().clear();
        }

        encoder.reset();
      }

      for (Columns.ColumnIO columnIO : columnIOs) {
        if (!columnIO.shouldLoadReferences()) {
          continue;
        }
        Encoder encoder = ((ReferencesMetaEncoding) columnIO.getEncoding()).getReferencesEncoder();
        encoder.flush();

        if (encoder.isDirect()) {
          encoder.asDirect().flush(outputStream);
        } else {
          encoder.asIndirect().getOutput().flush(outputStream);
          encoder.asIndirect().getOutput().clear();
        }

        encoder.reset();
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

  public static class Context {
    private int columnIndex = 0;
    private PageHeader pageHeader;
    private Configuration conf;

    public Context(Configuration conf) {
      this.conf = conf;
    }

    public Configuration getConf() {
      return conf;
    }

    public PageHeader getPageHeader() {
      return pageHeader;
    }

    public void setPageHeader(PageHeader pageHeader) {
      this.pageHeader = pageHeader;
    }

    public int getColumnIndex() {
      return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
      this.columnIndex = columnIndex;
    }

    public void incrementColumnIndex() {
      ++columnIndex;
    }

    public void incrementColumnIndex(int size) {
      columnIndex += size;
    }

    public void resetColumnIndex() {
      columnIndex = 0;
    }
  }
}
