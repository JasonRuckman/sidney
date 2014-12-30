package org.sidney.core;

public class TestNestedClass {
    private TestClass first;
    private TestClass second;

    public TestNestedClass() {

    }

    public TestNestedClass(TestClass first, TestClass second) {
        this.first = first;
        this.second = second;
    }

    public TestClass getFirst() {
        return first;
    }

    public void setFirst(TestClass first) {
        this.first = first;
    }

    public TestClass getSecond() {
        return second;
    }

    public void setSecond(TestClass second) {
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestNestedClass that = (TestNestedClass) o;

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