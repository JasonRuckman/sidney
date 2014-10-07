package org.sidney.encoding;

@FunctionalInterface
public interface TriConsumer<T, R, X> {
    void consume(T t, R r, X x);
}
