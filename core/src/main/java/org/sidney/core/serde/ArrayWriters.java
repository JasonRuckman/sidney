package org.sidney.core.serde;

public class ArrayWriters {
    static interface ArrayWriter<T> {
        void writeArray(T value, TypeWriter typeWriter, WriteContext context);
    }

    static class BoolArrayWriter implements ArrayWriter<boolean[]> {
        @Override
        public void writeArray(boolean[] value, TypeWriter typeWriter, WriteContext context) {
            for (boolean b : value) {
                typeWriter.writeBool(b, context);
            }
        }
    }

    static class IntArrayWriter implements ArrayWriter<int[]> {
        @Override
        public void writeArray(int[] value, TypeWriter typeWriter, WriteContext context) {
            for (int i : value) {
                typeWriter.writeInt(i, context);
            }
        }
    }

    static class LongArrayWriter implements ArrayWriter<long[]> {
        @Override
        public void writeArray(long[] value, TypeWriter typeWriter, WriteContext context) {
            for (long l : value) {
                typeWriter.writeLong(l, context);
            }
        }
    }

    static class FloatArrayWriter implements ArrayWriter<float[]> {
        @Override
        public void writeArray(float[] value, TypeWriter typeWriter, WriteContext context) {
            for (float f : value) {
                typeWriter.writeFloat(f, context);
            }
        }
    }

    static class DoubleArrayWriter implements ArrayWriter<double[]> {
        @Override
        public void writeArray(double[] value, TypeWriter typeWriter, WriteContext context) {
            for (double d : value) {
                typeWriter.writeDouble(d, context);
            }
        }
    }

    static class StringArrayWriter implements ArrayWriter<String[]> {
        @Override
        public void writeArray(String[] value, TypeWriter typeWriter, WriteContext context) {
            for (String s : value) {
                if(typeWriter.writeNullMarker(s, context)) {
                    typeWriter.writeString(s, context);
                }
            }
        }
    }
}