package org.sidney.encoding.float64.fpc;

public interface Predictor {
    long nextPredictedDouble();
    void update(long actual);
}
