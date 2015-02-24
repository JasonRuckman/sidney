package com.github.jasonruckman.sidney.core.io;

import java.io.OutputStream;

public abstract class DirectEncoder implements Encoder {
  public abstract void flush(OutputStream outputStream);

  public void flush() {

  }

  @Override
  public IndirectEncoder asIndirect() {
    throw new IllegalStateException();
  }

  @Override
  public DirectEncoder asDirect() {
    return this;
  }

  @Override
  public boolean isDirect() {
    return true;
  }
}