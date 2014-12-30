package org.sidney.core;

import org.sidney.core.encoding.io.StreamUtils;
import org.sidney.core.reader.ColumnReader;
import org.sidney.core.serializer.Serializer;
import org.sidney.core.serializer.SerializerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.BufferOverflowException;
import java.nio.charset.Charset;

public class Reader<T> {
    private Class<T> type;
    private final InputStream inputStream;
    private final Serializer serializer;
    private final ColumnReader columnReader;
    private boolean initialized = false;

    private int recordCount = 0;

    public Reader(InputStream inputStream) {
        this.inputStream = inputStream;
        this.serializer = SerializerFactory.serializer(type);
        this.columnReader = new ColumnReader(serializer);
    }

    public Class<T> getType() {
        return type;
    }

    public boolean hasNext() {
        initialize();
        return recordCount > 0;
    }

    public T next() {
        initialize();
        if(recordCount-- <= 0) {
            throw new BufferOverflowException();
        }
        return (T) serializer.nextRecord(columnReader, 0);
    }

    private void initialize() {
        if(!initialized) {
            try {
                int size = StreamUtils.readIntFromStream(inputStream);
                byte[] bytes = new byte[size];
                inputStream.read(bytes);
                String className = new String(bytes, Charset.forName("UTF-8"));
                recordCount = StreamUtils.readIntFromStream(inputStream);
                columnReader.readFromInputStream(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}