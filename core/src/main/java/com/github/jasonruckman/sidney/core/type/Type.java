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
package com.github.jasonruckman.sidney.core.type;

import com.github.jasonruckman.sidney.core.io.Encoding;

public enum Type {
  BOOLEAN {
    @Override
    public Encoding defaultEncoding() {
      return Encoding.BITMAP;
    }
  },
  INT16 {
    @Override
    public Encoding defaultEncoding() {
      return Encoding.PLAIN;
    }
  },
  CHAR {
    @Override
    public Encoding defaultEncoding() {
      return Encoding.PLAIN;
    }
  },
  INT32 {
    @Override
    public Encoding defaultEncoding() {
      return Encoding.PLAIN;
    }
  },
  INT64 {
    @Override
    public Encoding defaultEncoding() {
      return Encoding.PLAIN;
    }
  },
  FLOAT32 {
    @Override
    public Encoding defaultEncoding() {
      return Encoding.PLAIN;
    }
  },
  FLOAT64 {
    @Override
    public Encoding defaultEncoding() {
      return Encoding.PLAIN;
    }
  },
  STRING {
    @Override
    public Encoding defaultEncoding() {
      return Encoding.PLAIN;
    }
  },
  BINARY {
    @Override
    public Encoding defaultEncoding() {
      return Encoding.PLAIN;
    }
  },
  ENUM {
    @Override
    public Encoding defaultEncoding() {
      return Encoding.PLAIN;
    }
  };

  public abstract Encoding defaultEncoding();
}