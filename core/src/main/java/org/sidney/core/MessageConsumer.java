package org.sidney.core;

import org.sidney.core.column.*;
import org.sidney.core.encoding.Encoding;
import org.sidney.core.encoding.bool.BoolDecoder;
import org.sidney.core.encoding.bool.BoolEncoder;
import org.sidney.core.encoding.int32.Int32Decoder;
import org.sidney.core.encoding.int32.Int32Encoder;
import org.sidney.core.resolver.PrimitiveResolver;
import org.sidney.core.resolver.Resolver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MessageConsumer {
    private final List<ColumnIO> columnIOs;
    private final BoolEncoder definitionEncoder = Encoding.EWAH.newBoolEncoder();
    private final Int32Encoder repetitionEncoder = Encoding.BITPACKED.newInt32Encoder();
    private final BoolDecoder definitionDecoder = Encoding.EWAH.newBoolDecoder();
    private final Int32Decoder repetitionDecoder = Encoding.BITPACKED.newInt32Decoder();

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

    public boolean readNullMarker(int index) {
        return columnIOs.get(index).readNullMarker();
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
            PrimitiveResolver primitiveResolver = (PrimitiveResolver) resolver;
            switch (resolver.getType()) {
                case BOOLEAN: {
                    columns.add(new BoolColumnIO(
                            primitiveResolver.getEncoding().newBoolEncoder(),
                            primitiveResolver.getEncoding().newBoolDecoder()
                    ));
                    break;
                }
                case INT32: {
                    columns.add(new IntColumnIO(
                            primitiveResolver.getEncoding().newInt32Encoder(),
                            primitiveResolver.getEncoding().newInt32Decoder()
                    ));
                    break;
                }
                case INT64: {
                    columns.add(new LongColumnIO(
                            primitiveResolver.getEncoding().newInt64Encoder(),
                            primitiveResolver.getEncoding().newInt64Decoder()
                    ));
                    break;
                }
                case FLOAT32: {
                    columns.add(new FloatColumnIO(
                            primitiveResolver.getEncoding().newFloat32Encoder(),
                            primitiveResolver.getEncoding().newFloat32Decoder()
                    ));
                    break;
                }
                case FLOAT64: {
                    columns.add(new DoubleColumnIO(
                            primitiveResolver.getEncoding().newFloat64Encoder(),
                            primitiveResolver.getEncoding().newFloat64Decoder()
                    ));
                    break;
                }
                case BINARY: {
                    columns.add(new BytesColumnIO(
                            primitiveResolver.getEncoding().newBytesEncoder(),
                            primitiveResolver.getEncoding().newBytesDecoder()
                    ));
                    break;
                }
                case STRING: {
                    columns.add(new StringColumnIO(
                            primitiveResolver.getEncoding().newStringEncoder(),
                            primitiveResolver.getEncoding().newStringDecoder()
                    ));
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

    public void flushToOutputStream(OutputStream outputStream) throws IOException {
        definitionEncoder.writeToStream(outputStream);
        repetitionEncoder.writeToStream(outputStream);

        for (ColumnIO columnIO : columnIOs) {
            if (columnIO.getEncoder() != null) {
                columnIO.getEncoder().writeToStream(outputStream);
            }
        }
    }

    public void readFromInputStream(InputStream inputStream) throws IOException {
        definitionDecoder.populateBufferFromStream(inputStream);
        repetitionDecoder.populateBufferFromStream(inputStream);

        for (ColumnIO columnIO : columnIOs) {
            if (columnIO.getEncoder() != null) {
                columnIO.getDecoder().populateBufferFromStream(inputStream);
            }
        }
    }

    public void prepare() {
        definitionEncoder.reset();
        repetitionEncoder.reset();

        for (ColumnIO columnIO : columnIOs) {
            if (columnIO.getEncoder() != null) {
                columnIO.getEncoder().reset();
            }
        }
    }
}