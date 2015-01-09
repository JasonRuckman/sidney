package org.sidney.core;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Sid {
    private final Map<Class, Writer> writerCache = new HashMap<>();
    private final Map<Class, Reader> readerCache = new HashMap<>();

    public <T> Writer<T> newCachedWriter(Class<T> type, OutputStream outputStream) {
        return createWriter(type, outputStream, true);
    }

    public <T> Writer<T> newCachedWriter(Class<T> type, OutputStream outputStream, Class... generics) {
        return createWriter(type, outputStream, true, generics);
    }

    public <T> Reader<T> newCachedReader(Class<T> type, InputStream inputStream) {
        return createReader(type, inputStream, true);
    }

    private <T> Writer<T> createWriter(Class<T> type, OutputStream outputStream, boolean cache, Class... generics) {
        if(!cache) {
            return new Writer<>(type, outputStream, generics);
        }
        Writer<T> writer = (Writer<T>) writerCache.get(type);
        if(writer == null) {
            writer = new Writer<>(type, outputStream, generics);
            writerCache.put(type, writer);
        } else {
            writer.setOutputStream(outputStream);
        }
        return writer;
    }

    private <T> Reader<T> createReader(Class<T> type, InputStream inputStream, boolean cache, Class... generics) {
        if(!cache) {
            return new Reader<>(type, inputStream);
        }
        Reader<T> reader = (Reader<T>) readerCache.get(type);
        if(reader == null) {
            reader = new Reader<>(type, inputStream);
            readerCache.put(type, reader);
        } else {
            reader.setInputStream(inputStream);
        }
        return reader;
    }
}
