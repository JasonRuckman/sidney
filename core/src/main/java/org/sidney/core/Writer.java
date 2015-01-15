package org.sidney.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sidney.core.serde.*;

import java.io.IOException;
import java.io.OutputStream;

import static org.sidney.core.encoding.io.StreamUtils.writeIntToStream;

public class Writer<T> {
    public static final int DEFAULT_PAGE_SIZE = 1024;

    private final Class<T> type;
    private final TypeHandler handler;
    private final ObjectMapper json = new ObjectMapper();
    private final TypeWriter typeWriter;
    private OutputStream outputStream;
    private int recordCount = 0;
    private Class[] generics = null;
    private WriteContext context;
    private TypeHandlerFactory handlerFactory;

    //don't use a type variable since it screws up the inference
    Writer(Class type, OutputStream outputStream, Registrations registrations) {
        this.handlerFactory = new TypeHandlerFactory(registrations);
        this.type = type;
        this.outputStream = outputStream;
        this.handler = handlerFactory.handler(type, null, null);
        this.context = new WriteContext(new ColumnWriter(handler), new PageHeader());
        this.typeWriter = new TypeWriter();
    }

    Writer(Class type, OutputStream outputStream, Registrations registrations, Class... generics) {
        this.handlerFactory = new TypeHandlerFactory(registrations);
        this.type = type;
        this.outputStream = outputStream;
        this.generics = generics;
        this.handler = handlerFactory.handler(type, null, null, generics);
        this.context = new WriteContext(new ColumnWriter(handler), new PageHeader());
        this.typeWriter = new TypeWriter();
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.recordCount = 0;
    }

    public Class<T> getType() {
        return type;
    }

    public void write(T value) {
        context.setColumnIndex(0);
        handler.writeValue(value, typeWriter, context);

        if (++recordCount == DEFAULT_PAGE_SIZE) {
            flushPage(false);
        }
    }

    public void write(Iterable<T> values) {
        for (T value : values) {
            write(value);
        }
    }

    public void close() {
        flushPage(true);
    }

    private void flushPage(boolean isLastPage) {
        try {
            writePage(isLastPage);
            context.setPageHeader(new PageHeader());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writePage(boolean isLastPage) throws IOException {
        context.getPageHeader().setLastPage(isLastPage);
        context.getPageHeader().prepareForStorage();
        context.getPageHeader().setPageSize(recordCount);

        recordCount = 0;
        byte[] bytes = json.writeValueAsBytes(context.getPageHeader());
        writeIntToStream(bytes.length, outputStream);
        outputStream.write(bytes);
        context.getColumnWriter().flushToOutputStream(outputStream);
    }
}