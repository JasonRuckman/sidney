package org.sidney.core.encoding;

public interface BiConsumer<T, R> {
    void accept(T first, R second);
}
