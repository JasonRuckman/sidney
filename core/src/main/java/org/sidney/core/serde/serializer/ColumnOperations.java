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
package org.sidney.core.serde.serializer;

import org.sidney.core.serde.column.*;
import org.sidney.core.io.Encoding;
import org.sidney.core.io.bool.BoolDecoder;
import org.sidney.core.io.bool.BoolEncoder;
import org.sidney.core.io.int32.Int32Decoder;
import org.sidney.core.io.int32.Int32Encoder;
import org.sidney.core.UnsupportedColumnTypeException;

import java.util.ArrayList;
import java.util.List;

public abstract class ColumnOperations {
    protected final List<ColumnIO> columnIOs;
    protected final BoolEncoder definitionEncoder = Encoding.BITMAP.newBoolEncoder();
    protected final Int32Encoder repetitionEncoder = Encoding.DELTABITPACKINGHYBRID.newInt32Encoder();
    protected final BoolDecoder definitionDecoder = Encoding.BITMAP.newBoolDecoder();
    protected final Int32Decoder repetitionDecoder = Encoding.DELTABITPACKINGHYBRID.newInt32Decoder();

    public ColumnOperations(Serializer serializer) {
        columnIOs = extractColumns(serializer);
    }

    private List<ColumnIO> extractColumns(Serializer serializer) {
        List<ColumnIO> columns = new ArrayList<>();
        columns.addAll(columnsFor(serializer));

        List<Serializer> handlers = serializer.getSerializers();
        for (Serializer r : handlers) {
            columns.addAll(columnsFor(r));
        }
        return columns;
    }

    private List<ColumnIO> columnsFor(Serializer serializer) {
        List<ColumnIO> columns = new ArrayList<>();
        ColumnIO columnIO = null;
        if (serializer instanceof PrimitiveSerializer) {
            PrimitiveSerializer primitiveTypeHandler = (PrimitiveSerializer) serializer;
            columnIO = createPrimitiveIO(primitiveTypeHandler);
        } else {
            if (serializer.requiresTypeColumn()) {
                columnIO = new TypeColumnIO(Encoding.RLE.newInt32Encoder(), Encoding.RLE.newInt32Decoder());
            } else {
                columnIO = new ColumnIO();
            }
        }
        columns.add(columnIO);

        for (ColumnIO co : columns) {
            co.setDefinitionEncoder(definitionEncoder);
            co.setRepetitionEncoder(repetitionEncoder);
            co.setDefinitionDecoder(definitionDecoder);
            co.setRepetitionDecoder(repetitionDecoder);
        }

        return columns;
    }

    private ColumnIO createPrimitiveIO(PrimitiveSerializer typeHandler) {
        ColumnIO columnIO;
        switch (typeHandler.getType()) {
            case BOOLEAN: {
                columnIO = new BoolColumnIO(
                        typeHandler.getEncoding().newBoolEncoder(),
                        typeHandler.getEncoding().newBoolDecoder()
                );
                break;
            }
            case INT32: {
                columnIO = new IntColumnIO(
                        typeHandler.getEncoding().newInt32Encoder(),
                        typeHandler.getEncoding().newInt32Decoder()
                );
                break;
            }
            case INT64: {
                columnIO = new LongColumnIO(
                        typeHandler.getEncoding().newInt64Encoder(),
                        typeHandler.getEncoding().newInt64Decoder()
                );
                break;
            }
            case FLOAT32: {
                columnIO = new FloatColumnIO(
                        typeHandler.getEncoding().newFloat32Encoder(),
                        typeHandler.getEncoding().newFloat32Decoder()
                );
                break;
            }
            case FLOAT64: {
                columnIO = new DoubleColumnIO(
                        typeHandler.getEncoding().newFloat64Encoder(),
                        typeHandler.getEncoding().newFloat64Decoder()
                );
                break;
            }
            case BINARY: {
                columnIO = new BytesColumnIO(
                        typeHandler.getEncoding().newBytesEncoder(),
                        typeHandler.getEncoding().newBytesDecoder()
                );
                break;
            }
            case STRING: {
                columnIO = new StringColumnIO(
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
}