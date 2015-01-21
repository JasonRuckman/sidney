package org.sidney.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sidney.core.serde.ColumnWriter;
import org.sidney.core.serde.TypeWriter;
import org.sidney.core.serde.WriteContext;
import org.sidney.core.serde.serializer.Serializer;
import org.sidney.core.serde.serializer.SerializerRepository;

import java.io.IOException;
import java.io.OutputStream;

import static org.sidney.core.Bytes.writeIntToStream;

public abstract class BaseWriter<T> implements Writer<T> {
    public static final int DEFAULT_PAGE_SIZE = 1024;

    protected Registrations registrations;
    protected final Class<T> type;
    protected final Serializer serializer;
    protected final ObjectMapper json = new ObjectMapper();
    protected final TypeWriter typeWriter;
    private Class[] typeParams;
    private OutputStream outputStream;
    private int recordCount = 0;
    protected WriteContext context;
    protected SerializerRepository serializerRepository;
    private boolean isOpen = false;

    public BaseWriter(Class type, Registrations registrations, Class[] typeParams) {
        this.registrations = registrations;
        this.typeParams = typeParams;
        this.serializerRepository = getSerializerRepository();
        this.type = type;
        this.serializer = getSerializerWithParams();
        this.context = new WriteContext(new ColumnWriter(serializer), new PageHeader());
        this.typeWriter = new TypeWriter();
    }
    /**
     * Get the root type of this writer
     * @return the root type
     */
    public Class<T> getType() {
        return type;
    }

    /**
     * Write the given value
     */
    public void write(T value) {
        if(!isOpen) {
            throw new SidneyException("Cannot write to a closed writer");
        }
        context.setColumnIndex(0);
        serializer.writeValue(value, typeWriter, context);

        if (++recordCount == DEFAULT_PAGE_SIZE) {
            flush();
        }
    }

    /**
     * Open this writer against the given {@link java.io.OutputStream}
     */
    public void open(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.recordCount = 0;
        this.isOpen = true;
        this.context.setPageHeader(new PageHeader());
    }

    public void flush() {
        flushPage(false);
    }

    /**
     * Flush the last page and mark the writer as closed
     */
    public void close() {
        flushPage(true);

        outputStream = null;
        isOpen = false;
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

        //replace
        recordCount = 0;
        byte[] bytes = json.writeValueAsBytes(context.getPageHeader());
        writeIntToStream(bytes.length, outputStream);
        outputStream.write(bytes);
        context.getColumnWriter().flushToOutputStream(outputStream);
    }

    protected Serializer<T> getSerializer() {
        return serializerRepository.serializer(type, null, null, new Class[] { });
    }

    protected Serializer<T> getSerializerWithParams() {
        return serializerRepository.serializer(type, null, null, typeParams);
    }

    protected SerializerRepository getSerializerRepository() {
        return new SerializerRepository(registrations);
    }
}