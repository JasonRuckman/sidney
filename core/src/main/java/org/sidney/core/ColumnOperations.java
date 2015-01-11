package org.sidney.core;

import org.sidney.core.column.*;
import org.sidney.core.encoding.Encoding;
import org.sidney.core.encoding.bool.BoolDecoder;
import org.sidney.core.encoding.bool.BoolEncoder;
import org.sidney.core.encoding.int32.Int32Decoder;
import org.sidney.core.encoding.int32.Int32Encoder;
import org.sidney.core.serde.PrimitiveTypeHandler;
import org.sidney.core.serde.TypeHandler;

import java.util.ArrayList;
import java.util.List;

public abstract class ColumnOperations {
    protected final List<ColumnIO> columnIOs;
    protected final BoolEncoder definitionEncoder = Encoding.BITPACKED.newBoolEncoder();
    protected final Int32Encoder repetitionEncoder = Encoding.DELTABITPACKINGHYBRID.newInt32Encoder();
    protected final BoolDecoder definitionDecoder = Encoding.BITPACKED.newBoolDecoder();
    protected final Int32Decoder repetitionDecoder = Encoding.DELTABITPACKINGHYBRID.newInt32Decoder();

    public ColumnOperations(TypeHandler typeHandler) {
        columnIOs = extractColumns(typeHandler);
    }

    private List<ColumnIO> extractColumns(TypeHandler typeHandler) {
        List<ColumnIO> columns = new ArrayList<>();
        columns.addAll(columnsFor(typeHandler));
        for (TypeHandler r : typeHandler.getHandlers()) {
            columns.addAll(columnsFor(r));
        }
        return columns;
    }

    private List<ColumnIO> columnsFor(TypeHandler typeHandler) {
        List<ColumnIO> columns = new ArrayList<>();
        ColumnIO columnIO = null;
        if (typeHandler instanceof PrimitiveTypeHandler) {
            PrimitiveTypeHandler primitiveTypeHandler = (PrimitiveTypeHandler) typeHandler;
            columnIO = createPrimitiveIO(primitiveTypeHandler);
        } else {
            if (typeHandler.requiresTypeColumn()) {
                columnIO = new TypeColumnIO(Encoding.BITPACKED.newInt32Encoder(), Encoding.BITPACKED.newInt32Decoder());
            } else {
                columnIO = new ColumnIO();
            }
            columnIO.setPath(String.format("%s_METADATA", typeHandler.name()));
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

    private ColumnIO createPrimitiveIO(PrimitiveTypeHandler typeHandler) {
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
                throw new IllegalStateException();
            }
        }
        columnIO.setPath(typeHandler.name());
        return columnIO;
    }
}