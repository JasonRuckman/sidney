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
package com.github.jasonruckman.sidney.core.io;

import com.github.jasonruckman.sidney.core.UnsupportedEncodingException;
import com.github.jasonruckman.sidney.core.io.bool.BoolDecoder;
import com.github.jasonruckman.sidney.core.io.bool.BoolEncoder;
import com.github.jasonruckman.sidney.core.io.bool.EWAHBitmap;
import com.github.jasonruckman.sidney.core.io.bytes.BytesDecoder;
import com.github.jasonruckman.sidney.core.io.bytes.BytesEncoder;
import com.github.jasonruckman.sidney.core.io.character.CharDecoder;
import com.github.jasonruckman.sidney.core.io.character.CharEncoder;
import com.github.jasonruckman.sidney.core.io.float32.Float32Decoder;
import com.github.jasonruckman.sidney.core.io.float32.Float32Encoder;
import com.github.jasonruckman.sidney.core.io.float32.Plain;
import com.github.jasonruckman.sidney.core.io.float32.RLE;
import com.github.jasonruckman.sidney.core.io.float64.Float64Decoder;
import com.github.jasonruckman.sidney.core.io.float64.Float64Encoder;
import com.github.jasonruckman.sidney.core.io.int16.ShortDecoder;
import com.github.jasonruckman.sidney.core.io.int16.ShortEncoder;
import com.github.jasonruckman.sidney.core.io.int32.BitPacking;
import com.github.jasonruckman.sidney.core.io.int32.DeltaBitPacking;
import com.github.jasonruckman.sidney.core.io.int32.Int32Decoder;
import com.github.jasonruckman.sidney.core.io.int32.Int32Encoder;
import com.github.jasonruckman.sidney.core.io.int64.GroupVarInt;
import com.github.jasonruckman.sidney.core.io.int64.Int64Decoder;
import com.github.jasonruckman.sidney.core.io.int64.Int64Encoder;
import com.github.jasonruckman.sidney.core.io.string.StringDecoder;
import com.github.jasonruckman.sidney.core.io.string.StringEncoder;
import com.github.jasonruckman.sidney.core.type.Type;

public enum Encoding {
  PLAIN {
    @Override
    public BoolEncoder newBoolEncoder() {
      return new com.github.jasonruckman.sidney.core.io.bool.Plain.PlainBoolEncoder();
    }

    @Override
    public BoolDecoder newBoolDecoder() {
      return new com.github.jasonruckman.sidney.core.io.bool.Plain.PlainBoolDecoder();
    }

    @Override
    public CharEncoder newCharEncoder() {
      return new com.github.jasonruckman.sidney.core.io.character.Plain.PlainCharEncoder();
    }

    @Override
    public CharDecoder newCharDecoder() {
      return new com.github.jasonruckman.sidney.core.io.character.Plain.PlainCharDecoder();
    }

    @Override
    public ShortEncoder newShortEncoder() {
      return new com.github.jasonruckman.sidney.core.io.int16.Plain.PlainShortEncoder();
    }

    @Override
    public ShortDecoder newShortDecoder() {
      return new com.github.jasonruckman.sidney.core.io.int16.Plain.PlainShortDecoder();
    }

    @Override
    public Int32Encoder newInt32Encoder() {
      return new com.github.jasonruckman.sidney.core.io.int32.Plain.PlainInt32Encoder();
    }

    @Override
    public Int32Decoder newInt32Decoder() {
      return new com.github.jasonruckman.sidney.core.io.int32.Plain.PlainInt32Decoder();
    }

    @Override
    public Int64Encoder newInt64Encoder() {
      return new com.github.jasonruckman.sidney.core.io.int64.Plain.PlainInt64Encoder();
    }

    @Override
    public Int64Decoder newInt64Decoder() {
      return new com.github.jasonruckman.sidney.core.io.int64.Plain.PlainInt64Decoder();
    }

    @Override
    public Float32Encoder newFloat32Encoder() {
      return new Plain.PlainFloat32Encoder();
    }

    @Override
    public Float32Decoder newFloat32Decoder() {
      return new Plain.PlainFloat32Decoder();
    }

    @Override
    public Float64Encoder newFloat64Encoder() {
      return new com.github.jasonruckman.sidney.core.io.float64.Plain.PlainFloat64Encoder();
    }

    @Override
    public Float64Decoder newFloat64Decoder() {
      return new com.github.jasonruckman.sidney.core.io.float64.Plain.PlainFloat64Decoder();
    }

    @Override
    public StringEncoder newStringEncoder() {
      return new com.github.jasonruckman.sidney.core.io.string.Plain.PlainStringEncoder();
    }

    @Override
    public StringDecoder newStringDecoder() {
      return new com.github.jasonruckman.sidney.core.io.string.Plain.PlainStringDecoder();
    }

    @Override
    public BytesEncoder newBytesEncoder() {
      return new com.github.jasonruckman.sidney.core.io.bytes.Plain.ByteArrayEncoder();
    }

    @Override
    public BytesDecoder newBytesDecoder() {
      return new com.github.jasonruckman.sidney.core.io.bytes.Plain.ByteArrayDecoder();
    }
  },
  BITPACKED {
    @Override
    public CharEncoder newCharEncoder() {
      return new com.github.jasonruckman.sidney.core.io.character.BitPacking.BitPackingCharEncoder();
    }

    @Override
    public CharDecoder newCharDecoder() {
      return new com.github.jasonruckman.sidney.core.io.character.BitPacking.BitPackingCharDecoder();
    }

    @Override
    public ShortEncoder newShortEncoder() {
      return new com.github.jasonruckman.sidney.core.io.int16.BitPacking.BitPackingShortEncoder();
    }

    @Override
    public ShortDecoder newShortDecoder() {
      return new com.github.jasonruckman.sidney.core.io.int16.BitPacking.BitPackingShortDecoder();
    }

    @Override
    public Int32Encoder newInt32Encoder() {
      return new BitPacking.BitPackingInt32Encoder();
    }

    @Override
    public Int32Decoder newInt32Decoder() {
      return new BitPacking.BitPackingInt32Decoder();
    }

    @Override
    public BoolEncoder newBoolEncoder() {
      return new com.github.jasonruckman.sidney.core.io.bool.BitPacking.BitPackingBoolEncoder();
    }

    @Override
    public BoolDecoder newBoolDecoder() {
      return new com.github.jasonruckman.sidney.core.io.bool.BitPacking.BitPackingBoolDecoder();
    }
  },
  DELTABITPACKINGHYBRID {
    @Override
    public CharDecoder newCharDecoder() {
      return new com.github.jasonruckman.sidney.core.io.character.DeltaBitPacking.DeltaBitpackingCharDecoder();
    }

    @Override
    public CharEncoder newCharEncoder() {
      return new com.github.jasonruckman.sidney.core.io.character.DeltaBitPacking.DeltaBitpackingCharEncoder();
    }

    @Override
    public ShortEncoder newShortEncoder() {
      return new com.github.jasonruckman.sidney.core.io.int16.DeltaBitPacking.DeltaBitpackingShortEncoder();
    }

    @Override
    public ShortDecoder newShortDecoder() {
      return new com.github.jasonruckman.sidney.core.io.int16.DeltaBitPacking.DeltaBitpackingShortDecoder();
    }

    @Override
    public Int32Encoder newInt32Encoder() {
      return new DeltaBitPacking.DeltaBitpackingInt32Encoder();
    }

    @Override
    public Int32Decoder newInt32Decoder() {
      return new DeltaBitPacking.DeltaBitpackingInt32Decoder();
    }
  },
  BITMAP {
    @Override
    public BoolEncoder newBoolEncoder() {
      return new EWAHBitmap.EWAHBitmapBoolEncoder();
    }

    @Override
    public BoolDecoder newBoolDecoder() {
      return new EWAHBitmap.EWAHBitmapBoolDecoder();
    }
  },
  GROUPVARINT {
    @Override
    public Int64Encoder newInt64Encoder() {
      return new GroupVarInt.GroupVarInt64Encoder();
    }

    @Override
    public Int64Decoder newInt64Decoder() {
      return new GroupVarInt.GroupVarInt64Decoder();
    }
  },
  RLE {
    @Override
    public ShortEncoder newShortEncoder() {
      return new com.github.jasonruckman.sidney.core.io.int16.RLE.RLEShortEncoder();
    }

    @Override
    public ShortDecoder newShortDecoder() {
      return new com.github.jasonruckman.sidney.core.io.int16.RLE.RLEShortDecoder();
    }

    @Override
    public CharEncoder newCharEncoder() {
      return new com.github.jasonruckman.sidney.core.io.character.RLE.RLECharEncoder();
    }

    @Override
    public CharDecoder newCharDecoder() {
      return new com.github.jasonruckman.sidney.core.io.character.RLE.RLECharDecoder();
    }

    @Override
    public Int32Encoder newInt32Encoder() {
      return new com.github.jasonruckman.sidney.core.io.int32.RLE.RLEInt32Encoder();
    }

    @Override
    public Int32Decoder newInt32Decoder() {
      return new com.github.jasonruckman.sidney.core.io.int32.RLE.RLEInt32Decoder();
    }

    @Override
    public Int64Encoder newInt64Encoder() {
      return new com.github.jasonruckman.sidney.core.io.int64.RLE.RLEInt64Encoder();
    }

    @Override
    public Int64Decoder newInt64Decoder() {
      return new com.github.jasonruckman.sidney.core.io.int64.RLE.RLEInt64Decoder();
    }

    @Override
    public Float32Encoder newFloat32Encoder() {
      return new RLE.RLEFloat32Encoder();
    }

    @Override
    public Float32Decoder newFloat32Decoder() {
      return new RLE.RLEFloat32Decoder();
    }

    @Override
    public Float64Encoder newFloat64Encoder() {
      return new com.github.jasonruckman.sidney.core.io.float64.RLE.RLEFloat64Encoder();
    }

    @Override
    public Float64Decoder newFloat64Decoder() {
      return new com.github.jasonruckman.sidney.core.io.float64.RLE.RLEFloat64Decoder();
    }

    @Override
    public StringEncoder newStringEncoder() {
      return new com.github.jasonruckman.sidney.core.io.string.RLE.RLEStringEncoder();
    }

    @Override
    public StringDecoder newStringDecoder() {
      return new com.github.jasonruckman.sidney.core.io.string.RLE.RLEStringDecoder();
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

  public CharDecoder newCharDecoder() {
    throw new UnsupportedEncodingException(this, Type.CHAR);
  }

  public CharEncoder newCharEncoder() {
    throw new UnsupportedEncodingException(this, Type.CHAR);
  }

  public ShortEncoder newShortEncoder() {
    throw new UnsupportedEncodingException(this, Type.INT16);
  }

  public ShortDecoder newShortDecoder() {
    throw new UnsupportedEncodingException(this, Type.INT16);
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