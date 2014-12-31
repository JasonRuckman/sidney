package org.sidney.core;

public class VeryGeneric<T, R, U> {
    private T first;
    private R second;
    private U third;

    public VeryGeneric() {

    }

    public VeryGeneric(T first, R second, U third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public T getFirst() {
        return first;
    }

    public R getSecond() {
        return second;
    }

    public U getThird() {
        return third;
    }
}
