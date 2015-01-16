/**
 * Copyright 2014 Jason Ruckman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sidney.core.encoding;

import org.sidney.core.encoding.bool.*;
import org.sidney.core.encoding.bytes.ByteArrayDecoder;
import org.sidney.core.encoding.bytes.ByteArrayEncoder;
import org.sidney.core.encoding.bytes.BytesDecoder;
import org.sidney.core.encoding.bytes.BytesEncoder;
import org.sidney.core.encoding.float32.*;
import org.sidney.core.encoding.float64.Float64Decoder;
import org.sidney.core.encoding.float64.Float64Encoder;
import org.sidney.core.encoding.float64.PlainFloat64Decoder;
import org.sidney.core.encoding.float64.PlainFloat64Encoder;
import org.sidney.core.encoding.int32.*;
import org.sidney.core.encoding.int64.*;
import org.sidney.core.encoding.string.*;
import org.sidney.core.schema.Type;

public enum Encoding {
    PLAIN {
        @Override
        public BoolEncoder newBoolEncoder() {
            return new PlainBoolEncoder();
        }

        @Override
        public BoolDecoder newBoolDecoder() {
            return new PlainBoolDecoder();
        }

        @Override
        public Int32Encoder newInt32Encoder() {
            return new PlainInt32Encoder();
        }

        @Override
        public Int32Decoder newInt32Decoder() {
            return new PlainInt32Decoder();
        }

        @Override
        public Int64Encoder newInt64Encoder() {
            return new PlainInt64Encoder();
        }

        @Override
        public Int64Decoder newInt64Decoder() {
            return new PlainInt64Decoder();
        }

        @Override
        public Float32Encoder newFloat32Encoder() {
            return new PlainFloat32Encoder();
        }

        @Override
        public Float32Decoder newFloat32Decoder() {
            return new PlainFloat32Decoder();
        }

        @Override
        public Float64Encoder newFloat64Encoder() {
            return new PlainFloat64Encoder();
        }

        @Override
        public Float64Decoder newFloat64Decoder() {
            return new PlainFloat64Decoder();
        }

        @Override
        public StringEncoder newStringEncoder() {
            return new PlainStringEncoder();
        }

        @Override
        public StringDecoder newStringDecoder() {
            return new PlainStringDecoder();
        }

        @Override
        public BytesEncoder newBytesEncoder() {
            return new ByteArrayEncoder();
        }

        @Override
        public BytesDecoder newBytesDecoder() {
            return new ByteArrayDecoder();
        }
    },
    BITPACKED {
        @Override
        public Int32Encoder newInt32Encoder() {
            return new BitPackingInt32Encoder();
        }

        @Override
        public Int32Decoder newInt32Decoder() {
            return new BitPackingInt32Decoder();
        }

        @Override
        public BoolEncoder newBoolEncoder() {
            return new BitPackingBoolEncoder();
        }

        @Override
        public BoolDecoder newBoolDecoder() {
            return new BitPackingBoolDecoder();
        }
    },
    DELTABITPACKINGHYBRID {
        @Override
        public Int32Encoder newInt32Encoder() {
            return new DeltaBitPackingInt32Encoder();
        }

        @Override
        public Int32Decoder newInt32Decoder() {
            return new DeltaBitPackingInt32Decoder();
        }
    },
    BITMAP {
        @Override
        public BoolEncoder newBoolEncoder() {
            return new EWAHBitmapBoolEncoder();
        }

        @Override
        public BoolDecoder newBoolDecoder() {
            return new EWAHBitmapBoolDecoder();
        }
    },
    GROUPVARINT {
        @Override
        public Int64Encoder newInt64Encoder() {
            return new GroupVarInt64Encoder();
        }

        @Override
        public Int64Decoder newInt64Decoder() {
            return new GroupVarInt64Decoder();
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
        public Int32Encoder newInt32Encoder() {
            return new RLEInt32Encoder();
        }

        @Override
        public Int32Decoder newInt32Decoder() {
            return new RLEInt32Decoder();
        }

        @Override
        public Int64Encoder newInt64Encoder() {
            return new RLEInt64Encoder();
        }

        @Override
        public Int64Decoder newInt64Decoder() {
            return new RLEInt64Decoder();
        }

        @Override
        public Float32Encoder newFloat32Encoder() {
            return new RLEFloat32Encoder();
        }

        @Override
        public Float32Decoder newFloat32Decoder() {
            return new RLEFloat32Decoder();
        }

        @Override
        public StringEncoder newStringEncoder() {
            return new RLEStringEncoder();
        }

        @Override
        public StringDecoder newStringDecoder() {
            return new RLEStringDecoder();
        }
    };

    public BoolEncoder newBoolEncoder() {
        throw new UnsupportedEncodingException(
                this, Type.BOOLEAN
        );
    }

    public BoolDecoder newBoolDecoder() {
        throw new UnsupportedEncodingException(
                this, Type.BOOLEAN
        );
    }

    public Int32Encoder newInt32Encoder() {
        throw new UnsupportedEncodingException(
                this, Type.INT32
        );
    }

    public Int32Decoder newInt32Decoder() {
        throw new UnsupportedEncodingException(
                this, Type.INT32
        );
    }

    public Int64Encoder newInt64Encoder() {
        throw new UnsupportedEncodingException(
                this, Type.INT64
        );
    }

    public Int64Decoder newInt64Decoder() {
        throw new UnsupportedEncodingException(
                this, Type.INT64
        );
    }

    public Float32Encoder newFloat32Encoder() {
        throw new UnsupportedEncodingException(
                this, Type.FLOAT32
        );
    }

    public Float32Decoder newFloat32Decoder() {
        throw new UnsupportedEncodingException(
                this, Type.FLOAT32
        );
    }

    public Float64Encoder newFloat64Encoder() {
        throw new UnsupportedEncodingException(
                this, Type.FLOAT64
        );
    }

    public Float64Decoder newFloat64Decoder() {
        throw new UnsupportedEncodingException(
                this, Type.FLOAT64
        );
    }

    public BytesEncoder newBytesEncoder() {
        throw new UnsupportedEncodingException(
                this, Type.BINARY
        );
    }

    public BytesDecoder newBytesDecoder() {
        throw new UnsupportedEncodingException(
                this, Type.BINARY
        );
    }

    public StringEncoder newStringEncoder() {
        throw new UnsupportedEncodingException(
                this, Type.STRING
        );
    }

    public StringDecoder newStringDecoder() {
        throw new UnsupportedEncodingException(
                this, Type.STRING
        );
    }
}