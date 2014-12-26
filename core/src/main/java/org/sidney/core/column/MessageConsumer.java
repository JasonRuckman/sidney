package org.sidney.core.column;

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
        columnIO.writeNotNull();
        columnIO.writeBoolean(value);
    }

    public void writeInt(int index, int value) {
        ColumnIO columnIO = columnIOs.get(index);
        columnIO.writeNotNull();
        columnIO.writeInt(value);
    }

    public void writeLong(int index, long value) {
        ColumnIO columnIO = columnIOs.get(index);
        columnIO.writeNotNull();
        columnIO.writeLong(value);
    }

    public void writeFloat(int index, float value) {
        ColumnIO columnIO = columnIOs.get(index);
        columnIO.writeNotNull();
        columnIO.writeFloat(value);
    }

    public void writeDouble(int index, double value) {
        ColumnIO columnIO = columnIOs.get(index);
        columnIO.writeNotNull();
        columnIO.writeDouble(value);
    }

    public void writeBytes(int index, byte[] bytes) {
        ColumnIO columnIO = columnIOs.get(index);
        columnIO.writeNotNull();
        columnIO.writeBytes(bytes);
    }

    public void writeString(int index, String value) {
        ColumnIO columnIO = columnIOs.get(index);
        columnIO.writeNotNull();
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
        columns.addAll(columnsFor(resolver));
        for (Resolver r : resolver.children()) {
            columns.addAll(extractColumns(r));
        }
        return columns;
    }

    private List<ColumnIO> columnsFor(Resolver resolver) {
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
                    columns.add(new D)
                }
                case BINARY: {

                }
                case STRING: {

                }
            }
        }
        return columns;
    }
}