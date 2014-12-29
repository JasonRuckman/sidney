package org.sidney.core.encoding;

import org.sidney.core.encoding.bool.BoolEncoder;
import org.sidney.core.encoding.bool.EWAHBitmapBoolEncoder;
import org.sidney.core.encoding.bool.PlainBoolEncoder;
import org.sidney.core.encoding.bytes.ByteArrayEncoder;
import org.sidney.core.encoding.bytes.BytesEncoder;
import org.sidney.core.encoding.float32.Float32Encoder;
import org.sidney.core.encoding.float32.PlainFloat32Encoder;
import org.sidney.core.encoding.float32.RLEFloat32Encoder;
import org.sidney.core.encoding.float64.Float64Encoder;
import org.sidney.core.encoding.float64.PlainFloat64Encoder;
import org.sidney.core.encoding.int32.BitPackingInt32Encoder;
import org.sidney.core.encoding.int32.DeltaBitPackingInt32Encoder;
import org.sidney.core.encoding.int32.Int32Encoder;
import org.sidney.core.encoding.int32.PlainInt32Encoder;
import org.sidney.core.encoding.int64.GroupVarInt64Encoder;
import org.sidney.core.encoding.int64.Int64Encoder;
import org.sidney.core.encoding.int64.PlainInt64Encoder;
import org.sidney.core.encoding.string.DeltaLengthStringEncoder;
import org.sidney.core.encoding.string.PlainStringEncoder;
import org.sidney.core.encoding.string.RLEStringEncoder;
import org.sidney.core.encoding.string.StringEncoder;

public enum Encoding {
    PLAIN {
        @Override
        public BoolEncoder newBoolEncoder() {
            return new PlainBoolEncoder();
        }

        @Override
        public Int32Encoder newInt32Encoder() {
            return new PlainInt32Encoder();
        }

        @Override
        public Int64Encoder newInt64Encoder() {
            return new PlainInt64Encoder();
        }

        @Override
        public Float32Encoder newFloat32Encoder() {
            return new PlainFloat32Encoder();
        }

        @Override
        public Float64Encoder newFloat64Encoder() {
            return new PlainFloat64Encoder();
        }

        @Override
        public StringEncoder newStringEncoder() {
            return new PlainStringEncoder();
        }

        @Override
        public BytesEncoder newBytesEncoder() {
            return new ByteArrayEncoder();
        }
    },
    BITPACKED {
        @Override
        public Int32Encoder newInt32Encoder() {
            return new BitPackingInt32Encoder();
        }
    },
    DELTABITPACKINGHYBRID {
        @Override
        public Int32Encoder newInt32Encoder() {
            return new DeltaBitPackingInt32Encoder();
        }
    },
    EWAH {
        @Override
        public BoolEncoder newBoolEncoder() {
            return new EWAHBitmapBoolEncoder();
        }
    },
    GROUPVARINT {
        @Override
        public Int64Encoder newInt64Encoder() {
            return new GroupVarInt64Encoder();
        }
    },
    DELTALENGTH {
        @Override
        public StringEncoder newStringEncoder() {
            return new DeltaLengthStringEncoder();
        }
    },
    RLE {
        @Override
        public Float32Encoder newFloat32Encoder() {
            return new RLEFloat32Encoder();
        }

        @Override
        public StringEncoder newStringEncoder() {
            return new RLEStringEncoder();
        }
    };

    public BoolEncoder newBoolEncoder() {
        throw new IllegalStateException();
    }

    public Int32Encoder newInt32Encoder() {
        throw new IllegalStateException();
    }

    public Int64Encoder newInt64Encoder() {
        throw new IllegalStateException();
    }

    public Float32Encoder newFloat32Encoder() {
        throw new IllegalStateException();
    }

    public Float64Encoder newFloat64Encoder() {
        throw new IllegalStateException();
    }

    public BytesEncoder newBytesEncoder() {
        throw new IllegalStateException();
    }

    public StringEncoder newStringEncoder() {
        throw new IllegalStateException();
    }
}
