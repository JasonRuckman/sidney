package org.sidney.core;

import org.codehaus.jackson.map.ObjectMapper;
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
    private final ObjectMapper json = new ObjectMapper();
    private int recordCount = 0;
    private Container<Header> headerContainer = new Container<>(new Header());

    public Writer(Class<T> type, OutputStream outputStream) {
        this.type = type;
        this.outputStream = outputStream;
        this.serializer = SerializerFactory.serializer(type);
        this.columnWriter = new ColumnWriter(serializer, headerContainer);
    }

    public Writer(Class<T> type, OutputStream outputStream, Class... generics) {
        this.type = type;
        this.outputStream = outputStream;
        this.serializer = SerializerFactory.serializer(type, generics);
        this.columnWriter = new ColumnWriter(serializer, headerContainer);
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
            byte[] bytes = json.writeValueAsBytes(headerContainer.get());
            outputStream.write(bytes);
            StreamUtils.writeIntToStream(recordCount, outputStream);
            columnWriter.flushToOutputStream(outputStream);
            headerContainer.set(new Header());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}