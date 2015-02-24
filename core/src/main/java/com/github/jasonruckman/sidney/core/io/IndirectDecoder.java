package com.github.jasonruckman.sidney.core.io;

import com.github.jasonruckman.sidney.core.io.input.Input;

public abstract class IndirectDecoder implements Decoder {
  protected Input input;

  public void setInput(Input input) {
    this.input = input;
  }

  public void load() {

  }

  @Override
  public boolean isDirect() {
    return false;
  }

  @Override
  public IndirectDecoder asIndirect() {
    return this;
  }
}
