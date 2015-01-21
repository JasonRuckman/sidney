package org.sidney.core;

import java.io.OutputStream;

public interface Writer<T> {
    /**
     * Get the root type of this writer
     * @return the root type
     */
    public Class<T> getType();

    /**
     * Write the given value
     */
    void write(T value);

    /**
     * Open this writer against the given {@link java.io.OutputStream}
     */
    void open(OutputStream outputStream);

    void flush();

    /**
     * Flush the last page and mark the writer as closed
     */
    void close();
}
