package org.sidney.core;

import org.sidney.core.column.*;
import org.sidney.core.encoding.Encoding;
import org.sidney.core.encoding.bool.BoolDecoder;
import org.sidney.core.encoding.bool.BoolEncoder;
import org.sidney.core.encoding.int32.Int32Decoder;
import org.sidney.core.encoding.int32.Int32Encoder;
import org.sidney.core.serializer.PrimitiveSerializer;
import org.sidney.core.serializer.Serializer;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractColumnOperations {
    protected final List<ColumnIO> columnIOs;
    protected final BoolEncoder definitionEncoder = Encoding.EWAH.newBoolEncoder();
    protected final Int32Encoder repetitionEncoder = Encoding.BITPACKED.newInt32Encoder();
    protected final BoolDecoder definitionDecoder = Encoding.EWAH.newBoolDecoder();
    protected final Int32Decoder repetitionDecoder = Encoding.BITPACKED.newInt32Decoder();

    public AbstractColumnOperations(Serializer serializer, Container<Header> header) {
        columnIOs = extractColumns(serializer, header);
    }

    private List<ColumnIO> extractColumns(Serializer serializer, Container<Header> header) {
        List<ColumnIO> columns = new ArrayList<>();
        columns.addAll(columnsFor(serializer, header));
        for (Serializer r : serializer.children()) {
            columns.addAll(extractColumns(r, header));
        }
        return columns;
    }

    private List<ColumnIO> columnsFor(Serializer serializer, Container<Header> header) {
        List<ColumnIO> columns = new ArrayList<>();
        ColumnIO columnIO = null;
        if (serializer instanceof PrimitiveSerializer) {
            PrimitiveSerializer primitiveResolver = (PrimitiveSerializer) serializer;
            switch (serializer.getType()) {
                case BOOLEAN: {
                    columnIO = new BoolColumnIO(
                            primitiveResolver.getEncoding().newBoolEncoder(),
                            primitiveResolver.getEncoding().newBoolDecoder()
                    );
                    break;
                }
                case INT32: {
                    columnIO = new IntColumnIO(
                            primitiveResolver.getEncoding().newInt32Encoder(),
                            primitiveResolver.getEncoding().newInt32Decoder()
                    );
                    break;
                }
                case INT64: {
                    columnIO = new LongColumnIO(
                            primitiveResolver.getEncoding().newInt64Encoder(),
                            primitiveResolver.getEncoding().newInt64Decoder()
                    );
                    break;
                }
                case FLOAT32: {
                    columnIO = new FloatColumnIO(
                            primitiveResolver.getEncoding().newFloat32Encoder(),
                            primitiveResolver.getEncoding().newFloat32Decoder()
                    );
                    break;
                }
                case FLOAT64: {
                    columnIO = new DoubleColumnIO(
                            primitiveResolver.getEncoding().newFloat64Encoder(),
                            primitiveResolver.getEncoding().newFloat64Decoder()
                    );
                    break;
                }
                case BINARY: {
                    columnIO = new BytesColumnIO(
                            primitiveResolver.getEncoding().newBytesEncoder(),
                            primitiveResolver.getEncoding().newBytesDecoder()
                    );
                    break;
                }
                case STRING: {
                    columnIO = new StringColumnIO(
                            primitiveResolver.getEncoding().newStringEncoder(),
                            primitiveResolver.getEncoding().newStringDecoder()
                    );
                    break;
                }
            }
            columnIO.setPath(serializer.name());
        } else {
            if(serializer.requiresMetaColumn()) {
                columnIO = new MetaColumnIO(header, Encoding.BITPACKED.newInt32Encoder(), Encoding.BITPACKED.newInt32Decoder());
            } else {
                columnIO = new ColumnIO();
            }
            columnIO.setPath(String.format("%s_METADATA", serializer.name()));
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
}