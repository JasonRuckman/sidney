package org.sidney.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sidney.core.encoding.io.StreamUtils;
import org.sidney.core.serde.TypeWriter;
import org.sidney.core.serde.WriteContext;
import org.sidney.core.serde.TypeHandler;
import org.sidney.core.serde.TypeHandlerFactory;
import org.sidney.core.serde.TypeUtil;
import org.sidney.core.serde.ColumnWriter;

import java.io.IOException;
import java.io.OutputStream;

public class Writer<T> {
    private final Class<T> type;
    private final TypeHandler handler;
    private final ObjectMapper json = new ObjectMapper();
    private final TypeWriter typeWriter;
    private OutputStream outputStream;
    private int recordCount = 0;
    private Class[] generics = null;
    private WriteContext context;

    Writer(Class<T> type, OutputStream outputStream) {
        this.type = type;
        this.outputStream = outputStream;
        this.handler = TypeHandlerFactory.instance().handler(type, type, null, TypeUtil.binding(type));
        this.context = new WriteContext(new ColumnWriter(handler), new Header());
        this.typeWriter = new TypeWriter();
    }
    Writer(Class<T> type, OutputStream outputStream, Class... generics) {
        this.type = type;
        this.outputStream = outputStream;
        this.generics = generics;
        this.handler = TypeHandlerFactory.instance().handler(type, type, null, TypeUtil.binding(type), generics);
        this.context = new WriteContext(new ColumnWriter(handler), new Header());
        this.typeWriter = new TypeWriter();
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public Class<T> getType() {
        return type;
    }

    public void write(T value) {
        context.setIndex(0);
        handler.writeValue(value, typeWriter, context);
        ++recordCount;
    }

    public void write(Iterable<T> values) {
        for (T value : values) {
            write(value);
        }
    }

    public void flush() {
        try {
            writeHeader();
            writeColumns();
            context.setHeader(new Header());
            context.getColumnWriter().reset();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeHeader() throws IOException {
        if (generics != null) {
            context.getHeader().setGenerics(generics);
        }
        context.getHeader().prepareForStorage();
        byte[] bytes = json.writeValueAsBytes(context.getHeader());
        StreamUtils.writeIntToStream(bytes.length, outputStream);
        outputStream.write(bytes);
    }

    private void writeColumns() throws IOException {
        StreamUtils.writeIntToStream(recordCount, outputStream);
        context.getColumnWriter().flushToOutputStream(outputStream);
    }
}