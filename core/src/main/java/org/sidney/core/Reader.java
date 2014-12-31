package org.sidney.core;

import org.codehaus.jackson.map.ObjectMapper;
import org.sidney.core.encoding.io.StreamUtils;
import org.sidney.core.reader.ColumnReader;
import org.sidney.core.serializer.Serializer;
import org.sidney.core.serializer.SerializerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.BufferOverflowException;

public class Reader<T> {
    private final Class<T> type;
    private final InputStream inputStream;
    private Serializer serializer;
    private ColumnReader columnReader;
    private Container<Header> header = new Container<>();
    private boolean initialized = false;
    private final ObjectMapper json = new ObjectMapper();

    private int recordCount = 0;

    public Reader(Class<T> type, InputStream inputStream) {
        this.type = type;
        this.inputStream = inputStream;
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
                this.header.set(json.readValue(inputStream, Header.class));
                this.serializer = SerializerFactory.serializer(type);
                this.columnReader = new ColumnReader(this.serializer, header);

                recordCount = StreamUtils.readIntFromStream(inputStream);
                columnReader.readFromInputStream(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}