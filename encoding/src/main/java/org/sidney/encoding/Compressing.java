package org.sidney.encoding;

public interface Compressing {
    public static enum Algorithm {
        GZIP,
        LZ4,
        SNAPPY,
        BZIP2
    }

    Algorithm algorithm();
}
