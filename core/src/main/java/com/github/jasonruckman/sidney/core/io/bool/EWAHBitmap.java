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
package com.github.jasonruckman.sidney.core.io.bool;

import com.github.jasonruckman.sidney.core.io.input.Input;
import com.github.jasonruckman.sidney.core.io.output.Output;
import com.github.jasonruckman.sidney.core.io.StreamReader;
import com.github.jasonruckman.sidney.core.io.StreamWriter;
import com.github.jasonruckman.sidney.core.io.strategies.*;
import com.github.jasonruckman.sidney.core.util.Bytes;
import com.googlecode.javaewah.EWAHCompressedBitmap;
import com.googlecode.javaewah.IntIterator;

import java.io.*;

public class EWAHBitmap {
  public static class EWAHBitmapBoolDecoder extends StreamReader implements BoolDecoder {
    private EWAHCompressedBitmap bitmap = new EWAHCompressedBitmap();

    private int index = 0;
    private int nextTrueBit = -1;
    private IntIterator intIterator;

    @Override
    public boolean nextBool() {
      if (index++ == nextTrueBit) {
        if (intIterator.hasNext()) {
          nextTrueBit = intIterator.next();
        }
        return true;
      }
      return false;
    }

    @Override
    public boolean[] nextBools(int num) {
      boolean[] booleans = new boolean[num];
      for (int i = 0; i < num; i++) {
        booleans[i] = nextBool();
      }
      return booleans;
    }

    @Override
    public void initialize(Input input) {

    }

    @Override
    public ColumnLoadStrategy strategy() {
      return new DirectStream.DirectStreamColumnLoadStrategy();
    }

    @Override
    public void read(InputStream inputStream) {
      try {
        int num = Bytes.readIntFromStream(inputStream);
        if (num > 0) {
          index = 0;
          nextTrueBit = -1;
          bitmap.deserialize(new DataInputStream(inputStream));
          intIterator = bitmap.intIterator();
          if (intIterator.hasNext()) {
            nextTrueBit = intIterator.next();
          }
        }
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

  /**
   * Encodes booleans into a compressed bitmap.
   */
  public static class EWAHBitmapBoolEncoder extends StreamWriter implements BoolEncoder {
    private EWAHCompressedBitmap currentBitmap;
    private int currentIndex = 0;

    public EWAHBitmapBoolEncoder() {
      currentBitmap = new EWAHCompressedBitmap();
    }

    @Override
    public void writeBool(boolean value, Output output) {
      if (value) {
        currentBitmap.set(currentIndex);
      }
      ++currentIndex;
    }

    @Override
    public void writeBools(boolean[] values, Output output) {
      for (boolean value : values) {
        writeBool(value, output);
      }
    }

    @Override
    public void reset() {
      currentBitmap.clear();
      currentIndex = 0;
    }

    @Override
    public void flush(Output output) {

    }

    @Override
    public ColumnWriteStrategy strategy() {
      return new DirectStream.DirectStreamColumnWriteStrategy();
    }

    @Override
    public void write(OutputStream outputStream) {
      try {
        Bytes.writeIntToStream(currentIndex, outputStream);
        if (currentIndex > 0) {
          DataOutputStream bos = new DataOutputStream(outputStream);
          try {
            currentBitmap.serialize(bos);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }
}
