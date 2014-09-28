package org.sidney.encoding.float64.fpc;

public class FcmPredictor implements Predictor {
    private long[] table = new long[256];

    @Override
    public long nextPredictedDouble() {
        return 0;
    }

    @Override
    public void update(long actual) {

    }
}
