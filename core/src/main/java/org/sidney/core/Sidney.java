package org.sidney.core;

import java.io.OutputStream;
import java.util.List;

public class Sidney<T> {
    private final Class<T> type;

    public Sidney(Class<T> type) {
        this.type = type;
    }

    public void write(T item) {

    }

    public void write(List<T> items) {
        for(T item : items) {
            write(item);
        }
    }

    public void flushToStream(OutputStream outputStream) {

    }

    public T readNext() {
        return null;
    }

    public List<T> readAll() {
        return null;
    }
}
