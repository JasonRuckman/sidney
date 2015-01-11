package org.sidney.core;

import java.util.Arrays;

public class NestedArray<T> {
    private T[] array;

    public T[] getArray() {
        return array;
    }

    public void setArray(T[] array) {
        this.array = array;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NestedArray that = (NestedArray) o;

        if (!Arrays.equals(array, that.array)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return array != null ? Arrays.hashCode(array) : 0;
    }
}
