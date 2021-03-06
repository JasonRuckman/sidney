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

import com.github.jasonruckman.sidney.core.Configuration;
import com.github.jasonruckman.sidney.core.UnsupportedColumnTypeException;
import com.github.jasonruckman.sidney.core.io.bool.BoolDecoder;
import com.github.jasonruckman.sidney.core.io.bool.BoolEncoder;
import com.github.jasonruckman.sidney.core.io.int32.Int32Decoder;
import com.github.jasonruckman.sidney.core.io.int32.Int32Encoder;
import com.github.jasonruckman.sidney.core.serde.Contexts;
import com.github.jasonruckman.sidney.core.serde.serializer.PrimitiveSerializer;
import com.github.jasonruckman.sidney.core.serde.serializer.Serializer;
import com.github.jasonruckman.sidney.core.serde.serializer.SerializerFinalizer;

import java.util.ArrayList;
import java.util.List;

public abstract class ColumnGenerator extends Contexts.Context implements SerializerFinalizer {
  public static final Encoding INT_DEFINITION_ENCODING = Encoding.DELTABITPACKINGHYBRID;
  public static final Encoding BOOL_DEFINITION_ENCODING = Encoding.BITMAP;
  public static final Encoding REPETITION_ENCODING = Encoding.DELTABITPACKINGHYBRID;

  private final List<Columns.ColumnIO> columnsAsList = new ArrayList<>();
  protected Columns.ColumnIO[] columnIOs;
  public ColumnGenerator(Configuration conf) {
    super(conf);
  }

  private List<Columns.ColumnIO> columnsFor(Serializer serializer) {
    List<Columns.ColumnIO> columns = new ArrayList<>();
    Columns.ColumnIO columnIO = null;
    if (PrimitiveSerializer.class.isAssignableFrom(serializer.getClass())) {
      PrimitiveSerializer primitiveTypeHandler = (PrimitiveSerializer) serializer;
      columnIO = createPrimitiveIO(primitiveTypeHandler);
    } else {
      if (serializer.requiresTypeColumn()) {
        columnIO = new Columns.TypeColumnIO(Encoding.RLE.newInt32Encoder(), Encoding.RLE.newInt32Decoder());
      } else {
        columnIO = new Columns.ColumnIO() {
          @Override
          public Encoder getEncoder() {
            return null;
          }

          @Override
          public Decoder getDecoder() {
            return null;
          }
        };
      }
    }
    columns.add(columnIO);

    BoolEncoder definitionEncoder = BOOL_DEFINITION_ENCODING.newBoolEncoder();
    BoolDecoder definitionDecoder = BOOL_DEFINITION_ENCODING.newBoolDecoder();

    Int32Encoder repetitionEncoder = REPETITION_ENCODING.newInt32Encoder();
    Int32Decoder repetitionDecoder = REPETITION_ENCODING.newInt32Decoder();

    Int32Encoder intDefinitionEncoder = INT_DEFINITION_ENCODING.newInt32Encoder();
    Int32Decoder intDefinitionDecoder = INT_DEFINITION_ENCODING.newInt32Decoder();

    for (Columns.ColumnIO co : columns) {
      if (getConf().isReferenceTrackingEnabled()) {
        co.setEncoding(new ReferencesMetaEncoding(repetitionEncoder,
                repetitionDecoder,
                definitionEncoder,
                definitionDecoder,
                intDefinitionEncoder,
                intDefinitionDecoder
            )
        );
      } else {
        co.setEncoding(new MetaEncoding(repetitionEncoder, repetitionDecoder, definitionEncoder, definitionDecoder));
      }

      co.setColumnOutput(getConf().newOutput());
      co.setDefinitionOutput(getConf().newOutput());
      co.setRepetitionOutput(getConf().newOutput());
      co.setReferencesOutput(getConf().newOutput());
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
      case INT16: {
        columnIO = new Columns.ShortColumnIO(
            typeHandler.getEncoding().newShortEncoder(),
            typeHandler.getEncoding().newShortDecoder()
        );
        break;
      }
      case CHAR: {
        columnIO = new Columns.CharColumnIO(
            typeHandler.getEncoding().newCharEncoder(),
            typeHandler.getEncoding().newCharDecoder()
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
      case ENUM: {
        columnIO = new Columns.IntColumnIO(
            typeHandler.getEncoding().newInt32Encoder(),
            typeHandler.getEncoding().newInt32Decoder()
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
    columnsAsList.addAll(columnsFor(serializer));
    columnIOs = columnsAsList.toArray(new Columns.ColumnIO[columnsAsList.size()]);
  }
}