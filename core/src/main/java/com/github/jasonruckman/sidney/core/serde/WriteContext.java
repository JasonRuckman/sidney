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
import java.io.OutputStream;

public interface WriteContext {
    /**
     * Write bool to current column
     * @param value
     */
    void writeBool(boolean value);

    /**
     * Write byte to current column
     * @param value the byte
     */
    void writeByte(byte value);

    /**
     * Write char to current column
     * @param value the char
     */
    void writeChar(char value);

    /**
     * Write short to current column
     * @param value the short
     */
    void writeShort(short value);

    /**
     * Write int to current column
     * @param value the int
     */
    void writeInt(int value);

    /**
     * Write long to current column
     * @param value the long
     */
    void writeLong(long value);

    /**
     * Write float to current column
     * @param value the float
     */
    void writeFloat(float value);

    /**
     * Write double to current column
     * @param value the double
     */
    void writeDouble(double value);

    /**
     * Write bytes to current column
     * @param value the bytes
     */
    void writeBytes(byte[] value);

    /**
     * Write string to current column
     * @param value the string
     */
    void writeString(String value);

    /**
     * If the value is null, write a null marker
     * @return if the value is not null
     */
    <T> boolean writeNullMarker(T value);

    /**
     * If the value is null, write a null marker, also write the concrete type
     * @return if the value is not null
     */
    <T> boolean writeNullMarkerAndType(T value);

    /**
     * Write the repetition count to the current column
     * @param count the repetition count
     */
    void writeRepetitionCount(int count);

    /**
     * Get the current column index
     * @return the current column index
     */
    int getColumnIndex();

    /**
     * Set the column index to a given value
     * @param index the new value
     */
    void setColumnIndex(int index);

    /**
     * Increment the column index by one
     */
    void incrementColumnIndex();

    /**
     * Increment the column index by a given amount
     * @param size size to increment by
     */
    void incrementColumnIndex(int size);

    /**
     * Get the current page header
     * @return the current page header
     */
    PageHeader getPageHeader();

    /**
     * Set the current page header
     * @param pageHeader the new page header
     */
    void setPageHeader(PageHeader pageHeader);

    /**
     * Flush all columns to the output stream
     * @param outputStream the output stream
     * @throws IOException
     */
    void flushToOutputStream(OutputStream outputStream) throws IOException;
}