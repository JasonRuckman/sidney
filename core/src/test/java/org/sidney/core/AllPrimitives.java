package org.sidney.core;

import org.sidney.core.annotations.Encode;
import org.sidney.core.encoding.Encoding;

import java.util.Arrays;

public class AllPrimitives {
    @Encode(Encoding.EWAH)
    private boolean first;
    @Encode(Encoding.BITPACKED)
    private int second;
    @Encode(Encoding.GROUPVARINT)
    private long third;
    @Encode(Encoding.RLE)
    private float fourth;
    @Encode(Encoding.PLAIN)
    private double fifth;
    @Encode(Encoding.PLAIN)
    private byte[] sixth;
    @Encode(Encoding.PLAIN)
    private String seventh;

    public AllPrimitives() {

    }

    public AllPrimitives(boolean first, int second, long third, float fourth, double fifth, byte[] sixth, String seventh) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
        this.fifth = fifth;
        this.sixth = sixth;
        this.seventh = seventh;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public long getThird() {
        return third;
    }

    public void setThird(long third) {
        this.third = third;
    }

    public float getFourth() {
        return fourth;
    }

    public void setFourth(float fourth) {
        this.fourth = fourth;
    }

    public double getFifth() {
        return fifth;
    }

    public void setFifth(double fifth) {
        this.fifth = fifth;
    }

    public byte[] getSixth() {
        return sixth;
    }

    public void setSixth(byte[] sixth) {
        this.sixth = sixth;
    }

    public String getSeventh() {
        return seventh;
    }

    public void setSeventh(String seventh) {
        this.seventh = seventh;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AllPrimitives allPrimitives = (AllPrimitives) o;

        if (Double.compare(allPrimitives.fifth, fifth) != 0) return false;
        if (first != allPrimitives.first) return false;
        if (Float.compare(allPrimitives.fourth, fourth) != 0) return false;
        if (second != allPrimitives.second) return false;
        if (third != allPrimitives.third) return false;
        if (seventh != null ? !seventh.equals(allPrimitives.seventh) : allPrimitives.seventh != null) return false;
        if (!Arrays.equals(sixth, allPrimitives.sixth)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (first ? 1 : 0);
        result = 31 * result + second;
        result = 31 * result + (int) (third ^ (third >>> 32));
        result = 31 * result + (fourth != +0.0f ? Float.floatToIntBits(fourth) : 0);
        temp = Double.doubleToLongBits(fifth);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (sixth != null ? Arrays.hashCode(sixth) : 0);
        result = 31 * result + (seventh != null ? seventh.hashCode() : 0);
        return result;
    }
}
