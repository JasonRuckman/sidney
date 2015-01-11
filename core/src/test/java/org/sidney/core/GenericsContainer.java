package org.sidney.core;

public class GenericsContainer<T, R> {
    private NestedArray<T> array;
    private NestedMap<T, R> map;

    public GenericsContainer() {

    }

    public GenericsContainer(NestedArray<T> array, NestedMap<T, R> map) {
        this.array = array;
        this.map = map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GenericsContainer that = (GenericsContainer) o;

        if (array != null ? !array.equals(that.array) : that.array != null) return false;
        if (map != null ? !map.equals(that.map) : that.map != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = array != null ? array.hashCode() : 0;
        result = 31 * result + (map != null ? map.hashCode() : 0);
        return result;
    }
}
