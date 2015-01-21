package org.sidney.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sidney.core.serde.ColumnReader;
import org.sidney.core.serde.ReadContext;
import org.sidney.core.serde.TypeReader;
import org.sidney.core.serde.serializer.Serializer;
import org.sidney.core.serde.serializer.Serializers;
import org.sidney.core.serde.serializer.Types;

import java.io.InputStream;

public abstract class BaseReader<T> implements Reader<T>  {
    protected Registrations registrations;
    protected Class type;
    protected Class[] typeParams;
    private InputStream inputStream;
    private ReadContext context;
    private ObjectMapper json = new ObjectMapper();
    private Serializer serializer;
    private TypeReader typeReader = new TypeReader();
    private int recordCount = 0;
    protected Serializers serializers;
    private PageHeader currentPageHeader = null;
    private boolean isOpen = false;

    public BaseReader(Class type, Registrations registrations, Class[] typeParams) {
        this.type = type;
        this.registrations = registrations;
        this.typeParams = typeParams;
        this.serializers = getSerializers();
        this.serializer = getSerializerWithTypeParams();
    }

    /**
     * Check for new items
     * @return whether there are more items
     */
    public boolean hasNext() {
        if (currentPageHeader == null) {
            loadNextPage();
            return hasNext();
        }

        if (recordCount-- > 0) {
            return true;
        }

        if (!currentPageHeader.isLastPage()) {
            loadNextPage();
            return hasNext();
        }

        return false;
    }

    /**
     * Read the next item from the stream
     * @return the next item
     */
    public T read() {
        if(!isOpen) {
            throw new SidneyException("Reader is not open.");
        }
        context.setColumnIndex(0);
        return (T) serializer.readValue(typeReader, context);
    }

    /**
     * Open the given {@link java.io.InputStream} for reading.
     */
    public void open(InputStream inputStream) {
        this.inputStream = inputStream;
        this.recordCount = 0;
        this.isOpen = true;
    }

    /**
     * Marks this reader as closed
     */
    public void close() {
        inputStream = null;
        isOpen = false;
    }

    private void loadNextPage() {
        try {
            int size = Bytes.readIntFromStream(inputStream);
            byte[] bytes = new byte[size];
            inputStream.read(bytes);
            currentPageHeader = json.readValue(bytes, PageHeader.class);
            currentPageHeader.prepareForRead();
            context = new ReadContext(
                    new ColumnReader(
                            serializer
                    )
            );
            recordCount = currentPageHeader.getPageSize();
            context.setPageHeader(currentPageHeader);
            context.getColumnReader().loadFromInputStream(inputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected Serializer<T> getSerializer() {
        return serializers.serializer(
                type, null, Types.binding(type), typeParams
        );
    }

    protected Serializer<T> getSerializerWithTypeParams() {
        return serializers.serializer(type, null, null, typeParams);
    }

    protected Serializers getSerializers() {
        return new Serializers(registrations);
    }
}
