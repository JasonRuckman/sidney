package org.sidney.core;

import java.util.Arrays;

public class AllPrimitives {
    private boolean first;
    private int second;
    private char third;
    private short fourth;
    private byte fifth;
    private long sixth;
    private float seventh;
    private double eighth;
    private byte[] ninth;
    private String tenth;

    public AllPrimitives() {

    }

    public AllPrimitives(boolean first, int second, char third, short fourth, byte fifth, long sixth, float seventh, double eighth, byte[] ninth, String tenth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
        this.fifth = fifth;
        this.sixth = sixth;
        this.seventh = seventh;
        this.eighth = eighth;
        this.ninth = ninth;
        this.tenth = tenth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AllPrimitives that = (AllPrimitives) o;

        if (Double.compare(that.eighth, eighth) != 0) return false;
        if (fifth != that.fifth) return false;
        if (first != that.first) return false;
        if (fourth != that.fourth) return false;
        if (second != that.second) return false;
        if (Float.compare(that.seventh, seventh) != 0) return false;
        if (sixth != that.sixth) return false;
        if (third != that.third) return false;
        if (!Arrays.equals(ninth, that.ninth)) return false;
        if (tenth != null ? !tenth.equals(that.tenth) : that.tenth != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (first ? 1 : 0);
        result = 31 * result + second;
        result = 31 * result + (int) third;
        result = 31 * result + (int) fourth;
        result = 31 * result + (int) fifth;
        result = 31 * result + (int) (sixth ^ (sixth >>> 32));
        result = 31 * result + (seventh != +0.0f ? Float.floatToIntBits(seventh) : 0);
        temp = Double.doubleToLongBits(eighth);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (ninth != null ? Arrays.hashCode(ninth) : 0);
        result = 31 * result + (tenth != null ? tenth.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AllPrimitives{" +
                "first=" + first +
                ", second=" + second +
                ", third=" + third +
                ", fourth=" + fourth +
                ", fifth=" + fifth +
                ", sixth=" + sixth +
                ", seventh=" + seventh +
                ", eighth=" + eighth +
                ", ninth=" + Arrays.toString(ninth) +
                ", tenth='" + tenth + '\'' +
                '}';
    }
}
