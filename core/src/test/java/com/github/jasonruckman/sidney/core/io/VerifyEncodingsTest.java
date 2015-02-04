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

import org.junit.Test;

public class VerifyEncodingsTest {
  private final Encoding[] validBoolEncodings = new Encoding[]{
      Encoding.PLAIN, Encoding.BITPACKED, Encoding.BITMAP
  };

  private final Encoding[] validIntEncodings = new Encoding[]{
      Encoding.PLAIN, Encoding.BITPACKED, Encoding.DELTABITPACKINGHYBRID, Encoding.RLE
  };

  private final Encoding[] validLongEncodings = new Encoding[]{
      Encoding.PLAIN, Encoding.GROUPVARINT, Encoding.RLE
  };

  private final Encoding[] validFloatEncodings = new Encoding[]{
      Encoding.PLAIN, Encoding.RLE
  };

  private final Encoding[] validFloat64Encodings = new Encoding[]{
      Encoding.PLAIN, Encoding.RLE
  };

  private final Encoding[] validStringEncodings = new Encoding[]{
      Encoding.PLAIN, Encoding.DELTALENGTH
  };

  private final Encoding[] validBytesEncodings = new Encoding[]{
      Encoding.PLAIN
  };

  @Test
  public void verifyAll() {
    for (Encoding encoding : validBoolEncodings) {
      encoding.newBoolEncoder();
      encoding.newBoolDecoder();
    }

    for (Encoding encoding : validIntEncodings) {
      encoding.newInt32Encoder();
      encoding.newInt32Decoder();
    }

    for (Encoding encoding : validFloatEncodings) {
      encoding.newFloat32Encoder();
      encoding.newFloat32Decoder();
    }

    for (Encoding encoding : validFloat64Encodings) {
      encoding.newFloat64Encoder();
      encoding.newFloat64Decoder();
    }

    for (Encoding encoding : validLongEncodings) {
      encoding.newInt64Encoder();
      encoding.newInt64Decoder();
    }

    for (Encoding encoding : validStringEncodings) {
      encoding.newStringDecoder();
      encoding.newStringEncoder();
    }

    for (Encoding encoding : validBytesEncodings) {
      encoding.newBytesEncoder();
      encoding.newBytesDecoder();
    }
  }
}
