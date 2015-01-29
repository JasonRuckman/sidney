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
package org.sidney.core.serde;

import java.io.IOException;
import java.io.OutputStream;

public interface WriteContext {
    void writeBool(boolean value);
    void writeByte(byte value);
    void writeChar(char value);
    void writeShort(short value);
    void writeInt(int value);
    void writeLong(long value);
    void writeFloat(float value);
    void writeDouble(double value);
    void writeBytes(byte[] value);
    void writeString(String value);
    <T> boolean writeNullMarker(T value);
    <T> boolean writeNullMarkerAndType(T value);
    void writeRepetitionCount(int index, int count);

    int getColumnIndex();
    void setColumnIndex(int index);
    void incrementColumnIndex();
    void incrementColumnIndex(int size);

    PageHeader getPageHeader();
    void setPageHeader(PageHeader pageHeader);
    void flushToOutputStream(OutputStream outputStream) throws IOException;
}