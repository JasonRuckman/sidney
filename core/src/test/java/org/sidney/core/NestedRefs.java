package org.sidney.core;

public class NestedRefs {
    private AllPrimitives first;
    private AllPrimitives second;

    public NestedRefs() {

    }

    public NestedRefs(AllPrimitives first, AllPrimitives second) {
        this.first = first;
        this.second = second;
    }

    public AllPrimitives getFirst() {
        return first;
    }

    public void setFirst(AllPrimitives first) {
        this.first = first;
    }

    public AllPrimitives getSecond() {
        return second;
    }

    public void setSecond(AllPrimitives second) {
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NestedRefs that = (NestedRefs) o;

        if (first != null ? !first.equals(that.first) : that.first != null) return false;
        if (second != null ? !second.equals(that.second) : that.second != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        result = 31 * result + (second != null ? second.hashCode() : 0);
        return result;
    }
}