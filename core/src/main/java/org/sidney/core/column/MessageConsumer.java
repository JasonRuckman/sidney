package org.sidney.core.column;

import org.sidney.core.encoding.Encoding;
import org.sidney.core.encoding.bool.BoolEncoder;
import org.sidney.core.encoding.int32.Int32Encoder;
import org.sidney.core.resolver.PrimitiveResolver;
import org.sidney.core.resolver.Resolver;

import java.util.ArrayList;
import java.util.List;

public class MessageConsumer {
    private final List<ColumnIO> columnIOs;

    public MessageConsumer(Resolver resolver) {
        columnIOs = extractColumns(resolver);
    }

    public void writeBoolean(int index, boolean value) {
        ColumnIO columnIO = columnIOs.get(index);
        columnIO.writeBoolean(value);
    }

    public void writeInt(int index, int value) {
        ColumnIO columnIO = columnIOs.get(index);
        columnIO.writeInt(value);
    }

    public void writeLong(int index, long value) {
        ColumnIO columnIO = columnIOs.get(index);
        columnIO.writeLong(value);
    }

    public void writeFloat(int index, float value) {
        ColumnIO columnIO = columnIOs.get(index);
        columnIO.writeNotNull();
        columnIO.writeFloat(value);
    }

    public void writeDouble(int index, double value) {
        ColumnIO columnIO = columnIOs.get(index);
        columnIO.writeDouble(value);
    }

    public void writeBytes(int index, byte[] bytes) {
        ColumnIO columnIO = columnIOs.get(index);
        columnIO.writeBytes(bytes);
    }

    public void writeString(int index, String value) {
        ColumnIO columnIO = columnIOs.get(index);
        columnIO.writeString(value);
    }

    public void writeNotNull(int index) {
        columnIOs.get(index).writeNotNull();
    }

    public void writeNull(int index) {
        columnIOs.get(index).writeNull();
    }

    private List<ColumnIO> extractColumns(Resolver resolver) {
        List<ColumnIO> columns = new ArrayList<>();
        columns.addAll(columnsFor(resolver, Encoding.EWAH.newBoolEncoder(), Encoding.BITPACKED.newInt32Encoder()));
        for (Resolver r : resolver.children()) {
            columns.addAll(extractColumns(r));
        }
        return columns;
    }

    private List<ColumnIO> columnsFor(Resolver resolver, BoolEncoder definitionEncoder, Int32Encoder repetitionEncoder) {
        List<ColumnIO> columns = new ArrayList<>();
        if (resolver instanceof PrimitiveResolver) {
            switch (resolver.getType()) {
                case BOOLEAN: {
                    columns.add(new BoolColumnIO(((PrimitiveResolver) resolver).getEncoding().newBoolEncoder()));
                    break;
                }
                case INT32: {
                    columns.add(new IntColumnIO(((PrimitiveResolver) resolver).getEncoding().newInt32Encoder()));
                    break;
                }
                case INT64: {
                    columns.add(new LongColumnIO(((PrimitiveResolver) resolver).getEncoding().newInt64Encoder()));
                    break;
                }
                case FLOAT32: {
                    columns.add(new FloatColumnIO(((PrimitiveResolver) resolver).getEncoding().newFloat32Encoder()));
                    break;
                }
                case FLOAT64: {
                    columns.add(new DoubleColumnIO(((PrimitiveResolver) resolver).getEncoding().newFloat64Encoder()));
                    break;
                }
                case BINARY: {
                    columns.add(new BytesColumnIO(((PrimitiveResolver) resolver).getEncoding().newBytesEncoder()));
                    break;
                }
                case STRING: {
                    columns.add(new StringColumnIO(((PrimitiveResolver) resolver).getEncoding().newStringEncoder()));
                    break;
                }
            }
        } else {
            columns.add(new ColumnIO());
        }

        for (ColumnIO columnIO : columns) {
            columnIO.setDefinitionEncoder(definitionEncoder);
            columnIO.setRepetitionEncoder(repetitionEncoder);
        }

        return columns;
    }
}