package org.sidney.core.reader;

public interface Reader {
    boolean isNextNonNull();
    int nextRepetitionCount();

    boolean nextBool(int index);
    int nextInt(int index);
    long nextLong(int index);
    float nextFloat(int index);
    double nextDouble(int index);
    String nextString(int index);
    byte[] nextBytes(int index);

    <T> T newInstance(int index);
}