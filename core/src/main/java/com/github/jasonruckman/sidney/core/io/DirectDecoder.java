package com.github.jasonruckman.sidney.core.io;

import java.io.InputStream;

public abstract class DirectDecoder implements Decoder {
  public abstract void load(InputStream inputStream);

  @Override
  public boolean isDirect() {
    return true;
  }

  @Override
  public IndirectDecoder asIndirect() {
    throw new IllegalStateException();
  }
}