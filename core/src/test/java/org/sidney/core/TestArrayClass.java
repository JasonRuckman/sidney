package org.sidney.core;

public class TestArrayClass {
    private int first;
    private int[] second;

    public TestArrayClass(int first, int[] second) {
        this.first = first;
        this.second = second;
    }


    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int[] getSecond() {
        return second;
    }

    public void setSecond(int[] second) {
        this.second = second;
    }
}
