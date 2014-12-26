package org.sidney.core.encoding;

import org.sidney.core.encoding.bool.BoolEncoder;
import org.sidney.core.encoding.float32.Float32Encoder;
import org.sidney.core.encoding.float64.Float64Encoder;
import org.sidney.core.encoding.int32.Int32Encoder;
import org.sidney.core.encoding.int64.Int64Encoder;

public enum Encoding {
    PLAIN {
        @Override
        public BoolEncoder newBoolEncoder() {
            return null;
        }

        @Override
        public Int32Encoder newInt32Encoder() {
            return null;
        }

        @Override
        public Int64Encoder newInt64Encoder() {
            return null;
        }

        @Override
        public Float32Encoder newFloat32Encoder() {
            return null;
        }

        @Override
        public Float64Encoder newFloat64Encoder() {
            return null;
        }
    },
    BITPACKED {
        @Override
        public BoolEncoder newBoolEncoder() {
            return null;
        }

        @Override
        public Int32Encoder newInt32Encoder() {
            return null;
        }

        @Override
        public Int64Encoder newInt64Encoder() {
            return null;
        }

        @Override
        public Float32Encoder newFloat32Encoder() {
            return null;
        }

        @Override
        public Float64Encoder newFloat64Encoder() {
            return null;
        }
    },
    DELTABITPACKINGHYBRID {
        @Override
        public BoolEncoder newBoolEncoder() {
            return null;
        }

        @Override
        public Int32Encoder newInt32Encoder() {
            return null;
        }

        @Override
        public Int64Encoder newInt64Encoder() {
            return null;
        }

        @Override
        public Float32Encoder newFloat32Encoder() {
            return null;
        }

        @Override
        public Float64Encoder newFloat64Encoder() {
            return null;
        }
    },
    EWAH {
        @Override
        public BoolEncoder newBoolEncoder() {
            return null;
        }

        @Override
        public Int32Encoder newInt32Encoder() {
            return null;
        }

        @Override
        public Int64Encoder newInt64Encoder() {
            return null;
        }

        @Override
        public Float32Encoder newFloat32Encoder() {
            return null;
        }

        @Override
        public Float64Encoder newFloat64Encoder() {
            return null;
        }
    },
    GROUPVARINT {
        @Override
        public BoolEncoder newBoolEncoder() {
            return null;
        }

        @Override
        public Int32Encoder newInt32Encoder() {
            return null;
        }

        @Override
        public Int64Encoder newInt64Encoder() {
            return null;
        }

        @Override
        public Float32Encoder newFloat32Encoder() {
            return null;
        }

        @Override
        public Float64Encoder newFloat64Encoder() {
            return null;
        }
    };

    public abstract BoolEncoder newBoolEncoder();

    public abstract Int32Encoder newInt32Encoder();

    public abstract Int64Encoder newInt64Encoder();

    public abstract Float32Encoder newFloat32Encoder();

    public abstract Float64Encoder newFloat64Encoder();
}
