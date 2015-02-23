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
package com.github.jasonruckman.sidney.core.io.string;

import com.github.jasonruckman.sidney.core.io.strategies.*;
import com.github.jasonruckman.sidney.core.util.Accessors;
import com.github.jasonruckman.sidney.core.util.Fields;
import com.github.jasonruckman.sidney.core.io.Encoding;
import com.github.jasonruckman.sidney.core.io.input.Input;
import com.github.jasonruckman.sidney.core.io.output.Output;
import com.github.jasonruckman.sidney.core.io.bytes.RawBytes;
import com.github.jasonruckman.sidney.core.io.int32.Int32Decoder;
import com.github.jasonruckman.sidney.core.io.int32.Int32Encoder;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class DeltaLength {
  private static final Encoding LENGTH_ENCODING = Encoding.PLAIN;

  public static class DeltaLengthStringDecoder implements StringDecoder {
    private final Int32Decoder lengthDecoder = LENGTH_ENCODING.newInt32Decoder();
    private final RawBytes.RawBytesDecoder bytesDecoder = new RawBytes.RawBytesDecoder();
    private final Charset charset = Charset.forName("UTF-8");

    public String readString() {
      int length = lengthDecoder.nextInt();
      byte[] bytes = bytesDecoder.readBytes(length);

      return charset.decode(ByteBuffer.wrap(bytes)).toString();
    }

    @Override
    public String[] readStrings(int num) {
      String[] strings = new String[num];
      for (int i = 0; i < num; i++) {
        strings[i] = readString();
      }
      return strings;
    }

    @Override
    public void initialize(Input input) {
      lengthDecoder.initialize(input);
      bytesDecoder.initialize(input);
    }

    @Override
    public ColumnLoadStrategy strategy() {
      return new Default.DefaultColumnLoadStrategy();
    }
  }

  public static class DeltaLengthStringEncoder implements StringEncoder {
    private final Int32Encoder lengthEncoder = LENGTH_ENCODING.newInt32Encoder();
    private final RawBytes.RawBytesEncoder bytesEncoder = new RawBytes.RawBytesEncoder();
    private final Charset charset = Charset.forName("UTF-8");
    private Accessors.FieldAccessor charArrayField;

    public DeltaLengthStringEncoder() {
      for (Field field : Fields.getAllFieldsNoPrimitiveFilter(String.class)) {
        if (field.getName().equals("value")) {
          charArrayField = Accessors.newAccessor(field);
          break;
        }
      }
    }

    public void writeString(String s, Output output) {
      char[] arr = (char[]) charArrayField.get(s);
      ByteBuffer bb = charset.encode(
          CharBuffer.wrap(arr)
      );

      lengthEncoder.writeInt(bb.limit(), output);
      bytesEncoder.writeBytes(bb.array(), 0, bb.limit(), output);
    }

    @Override
    public void writeStrings(String[] s, Output output) {
      for (String str : s) {
        writeString(str, output);
      }
    }

    @Override
    public void reset() {
      lengthEncoder.reset();
      bytesEncoder.reset();
    }

    @Override
    public void flush(Output output) {
      lengthEncoder.flush(output);
      bytesEncoder.flush(output);
    }

    @Override
    public ColumnWriteStrategy strategy() {
      return new Default.DefaultColumnWriteStrategy();
    }
  }
}
