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
package com.github.jasonruckman.sidney.core.serde.serializer;

import com.github.jasonruckman.sidney.core.SidneyConf;
import com.github.jasonruckman.sidney.core.UnsupportedColumnTypeException;
import com.github.jasonruckman.sidney.core.io.Columns;
import com.github.jasonruckman.sidney.core.io.DefaultDefRepEncoding;
import com.github.jasonruckman.sidney.core.io.Encoding;
import com.github.jasonruckman.sidney.core.io.ReferenceCheckingDefRepEncoding;
import com.github.jasonruckman.sidney.core.io.bool.BoolDecoder;
import com.github.jasonruckman.sidney.core.io.bool.BoolEncoder;
import com.github.jasonruckman.sidney.core.io.int32.Int32Decoder;
import com.github.jasonruckman.sidney.core.io.int32.Int32Encoder;

import java.util.ArrayList;
import java.util.List;

public abstract class ColumnOperations implements SerializerFinalizer {
  public static final Encoding INT_DEFINITION_ENCODING = Encoding.BITPACKED;
  public static final Encoding BOOL_DEFINITION_ENCODING = Encoding.BITMAP;
  public static final Encoding REPETITION_ENCODING = Encoding.BITPACKED;
  protected final List<Columns.ColumnIO> columnIOs = new ArrayList<>();

  public abstract SidneyConf getConf();

  private List<Columns.ColumnIO> columnsFor(Serializer serializer) {
    List<Columns.ColumnIO> columns = new ArrayList<>();
    Columns.ColumnIO columnIO = null;
    if (serializer instanceof PrimitiveSerializer) {
      PrimitiveSerializer primitiveTypeHandler = (PrimitiveSerializer) serializer;
      columnIO = createPrimitiveIO(primitiveTypeHandler);
    } else {
      if (serializer.requiresTypeColumn()) {
        columnIO = new Columns.TypeColumnIO(Encoding.RLE.newInt32Encoder(), Encoding.RLE.newInt32Decoder());
      } else {
        columnIO = new Columns.ColumnIO();
      }
    }
    columns.add(columnIO);

    for (Columns.ColumnIO co : columns) {
      BoolEncoder definitionEncoder = BOOL_DEFINITION_ENCODING.newBoolEncoder();
      BoolDecoder definitionDecoder = BOOL_DEFINITION_ENCODING.newBoolDecoder();

      Int32Encoder repetitionEncoder = REPETITION_ENCODING.newInt32Encoder();
      Int32Decoder repetitionDecoder = REPETITION_ENCODING.newInt32Decoder();

      if (getConf().isReferenceTrackingEnabled()) {
        co.setEncoding(new ReferenceCheckingDefRepEncoding(repetitionEncoder, repetitionDecoder,
                INT_DEFINITION_ENCODING.newInt32Encoder(),
                INT_DEFINITION_ENCODING.newInt32Decoder(), definitionDecoder, definitionEncoder)
        );
      } else {
        co.setEncoding(new DefaultDefRepEncoding(repetitionEncoder, repetitionDecoder, definitionEncoder, definitionDecoder));
      }
    }

    return columns;
  }

  private Columns.ColumnIO createPrimitiveIO(PrimitiveSerializer typeHandler) {
    Columns.ColumnIO columnIO;
    switch (typeHandler.getType()) {
      case BOOLEAN: {
        columnIO = new Columns.BoolColumnIO(
            typeHandler.getEncoding().newBoolEncoder(),
            typeHandler.getEncoding().newBoolDecoder()
        );
        break;
      }
      case INT32: {
        columnIO = new Columns.IntColumnIO(
            typeHandler.getEncoding().newInt32Encoder(),
            typeHandler.getEncoding().newInt32Decoder()
        );
        break;
      }
      case INT64: {
        columnIO = new Columns.LongColumnIO(
            typeHandler.getEncoding().newInt64Encoder(),
            typeHandler.getEncoding().newInt64Decoder()
        );
        break;
      }
      case FLOAT32: {
        columnIO = new Columns.FloatColumnIO(
            typeHandler.getEncoding().newFloat32Encoder(),
            typeHandler.getEncoding().newFloat32Decoder()
        );
        break;
      }
      case FLOAT64: {
        columnIO = new Columns.DoubleColumnIO(
            typeHandler.getEncoding().newFloat64Encoder(),
            typeHandler.getEncoding().newFloat64Decoder()
        );
        break;
      }
      case BINARY: {
        columnIO = new Columns.BytesColumnIO(
            typeHandler.getEncoding().newBytesEncoder(),
            typeHandler.getEncoding().newBytesDecoder()
        );
        break;
      }
      case STRING: {
        columnIO = new Columns.StringColumnIO(
            typeHandler.getEncoding().newStringEncoder(),
            typeHandler.getEncoding().newStringDecoder()
        );
        break;
      }
      default: {
        throw new UnsupportedColumnTypeException(typeHandler.getType());
      }
    }
    return columnIO;
  }

  @Override
  public void finish(Serializer serializer) {
    columnIOs.addAll(columnsFor(serializer));
  }
}