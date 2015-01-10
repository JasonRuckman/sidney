package org.sidney.core;

public class InheritedAllPrimitives extends AllPrimitives {
    public InheritedAllPrimitives(boolean first, int second, char third, short fourth, byte fifth, long sixth, float seventh, double eighth, byte[] ninth, String tenth) {
        super(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth);
    }

    public InheritedAllPrimitives() {

    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}