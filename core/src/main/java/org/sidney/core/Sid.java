package org.sidney.core;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Sid {
    private final Map<Class, Writer> writerCache = new HashMap<>();
    private final Map<Class, Reader> readerCache = new HashMap<>();
    private final Registrations registrations = new Registrations();

    //don't use Class<T> it will make the inference weird when you have generics
    public <T> Writer<T> newCachedWriter(Class type, OutputStream outputStream) {
        return createWriter(type, outputStream, true);
    }

    public <T> Writer<T> newCachedWriter(Class type, OutputStream outputStream, Class... generics) {
        return createWriter(type, outputStream, true, generics);
    }

    public <T> Reader<T> newCachedReader(Class type, InputStream inputStream) {
        return createReader(type, inputStream, true);
    }

    public <T> Reader<T> newCachedReader(Class type, InputStream inputStream, Class... generics) {
        return createReader(type, inputStream, true, generics);
    }

    private <T> Writer<T> createWriter(Class type, OutputStream outputStream, boolean cache, Class... generics) {
        if (!cache) {
            return new Writer<>(type, outputStream, registrations, generics);
        }
        Writer<T> writer = (Writer<T>) writerCache.get(type);
        if (writer == null) {
            writer = new Writer<>(type, outputStream, registrations, generics);
            writerCache.put(type, writer);
        } else {
            writer.setOutputStream(outputStream);
        }
        return writer;
    }

    private <T> Reader<T> createReader(Class<T> type, InputStream inputStream, boolean cache, Class... generics) {
        if (!cache) {
            return new Reader<>(type, inputStream, registrations, generics);
        }
        Reader<T> reader = (Reader<T>) readerCache.get(type);
        if (reader == null) {
            reader = new Reader<>(type, inputStream, registrations, generics);
            readerCache.put(type, reader);
        } else {
            reader.setInputStream(inputStream);
        }
        return reader;
    }
}