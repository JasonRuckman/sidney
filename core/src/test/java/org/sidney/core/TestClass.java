package org.sidney.core;

import org.sidney.core.annotations.Encode;
import org.sidney.core.encoding.Encoding;

public class TestClass {
    @Encode(Encoding.BITPACKED)
    private int first;
    @Encode(Encoding.GROUPVARINT)
    private long second;

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
}
