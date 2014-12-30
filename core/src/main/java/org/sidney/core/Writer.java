package org.sidney.core;

import org.sidney.core.encoding.io.StreamUtils;
import org.sidney.core.serializer.Serializer;
import org.sidney.core.serializer.SerializerFactory;
import org.sidney.core.writer.ColumnWriter;

import java.io.IOException;
import java.io.OutputStream;

public class Writer<T> {
    private final Class<T> type;
    private final OutputStream outputStream;
    private final ColumnWriter columnWriter;
    private final Serializer serializer;
    private int recordCount = 0;

    public Writer(Class<T> type, OutputStream outputStream) {
        this.type = type;
        this.outputStream = outputStream;
        this.serializer = SerializerFactory.serializer(type);
        this.columnWriter = new ColumnWriter(SerializerFactory.serializer(type));
    }

    public Class<T> getType() {
        return type;
    }

    public void write(T value) {
        serializer.writeRecord(columnWriter, value, 0);
        ++recordCount;
    }

    public void write(Iterable<T> values) {
        for(T value : values) {
            write(value);
        }
    }

    public void flush() {
        try {
            StreamUtils.writeIntToStream(recordCount, outputStream);
            columnWriter.flushToOutputStream(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}