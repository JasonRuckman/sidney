package org.sidney.core;

import org.sidney.core.annotations.Encode;
import org.sidney.core.encoding.Encoding;

public class TestClass {
    @Encode(Encoding.BITPACKED)
    private int first;
    @Encode(Encoding.GROUPVARINT)
    private long second;

    public TestClass() {

    }

    public TestClass(int first, long second) {
        this.first = first;
        this.second = second;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public long getSecond() {
        return second;
    }

    public void setSecond(long second) {
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestClass testClass = (TestClass) o;

        if (first != testClass.first) return false;
        if (second != testClass.second) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = first;
        result = 31 * result + (int) (second ^ (second >>> 32));
        return result;
    }
}
