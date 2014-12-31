package org.sidney.core;

public class Container<T> {
    private T value;

    public Container() {

    }

    public Container(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }
}
