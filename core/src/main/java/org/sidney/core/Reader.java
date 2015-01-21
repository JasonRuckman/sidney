package org.sidney.core;

import java.io.InputStream;

public interface Reader<T> {
    /**
     * Check for new items
     * @return whether there are more items
     */
    boolean hasNext();

    /**
     * Read the next item from the stream
     * @return the next item
     */
    T read();

    /**
     * Open the given {@link java.io.InputStream} for reading.
     */
    void open(InputStream inputStream);

    /**
     * Marks this reader as closed
     */
    void close();
}
