package org.sidney.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sidney.core.encoding.io.StreamUtils;
import org.sidney.core.serde.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Reader<T> {
    private Class<T> type;
    private InputStream inputStream;
    private Class[] generics;
    private ReadContext context;
    private ObjectMapper json = new ObjectMapper();
    private TypeHandler typeHandler;
    private TypeReader typeReader = new TypeReader();
    private int recordCount = 0;

    Reader(Class<T> type, InputStream inputStream) {
        this.type = type;
        this.inputStream = inputStream;
        this.typeHandler = TypeHandlerFactory.instance().handler(
                type, type, null, TypeUtil.binding(type)
        );
        try {
            loadHeader();
            loadColumns();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    Reader(Class<T> type, InputStream inputStream, Class... generics) {
        this.type = type;
        this.inputStream = inputStream;
        this.generics = generics;
        this.typeHandler = TypeHandlerFactory.instance().handler(
                type, type, null, TypeUtil.binding(type), generics
        );
        try {
            loadHeader();
            loadColumns();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public T read() {
        if(recordCount-- == 0) {
            throw new IllegalStateException();
        }
        context.setIndex(0);
        return (T) typeHandler.readValue(typeReader, context);
    }

    public List<T> readAll() {
        List<T> records = new ArrayList<>();
        for(int i = 0; i < recordCount; i++) {
            records.add(read());
        }
        return records;
    }

    private void loadHeader() throws IOException, ClassNotFoundException {
        int size = StreamUtils.readIntFromStream(inputStream);
        byte[] bytes = new byte[size];
        inputStream.read(bytes);
        Header header = json.readValue(bytes, Header.class);
        header.prepareForRead();
        context = new ReadContext(
                new ColumnReader(
                        typeHandler
                )
        );
        context.setHeader(header);
    }

    private void loadColumns() throws IOException {
        recordCount = StreamUtils.readIntFromStream(inputStream);
        context.getColumnReader().loadFromInputStream(inputStream);
    }
}