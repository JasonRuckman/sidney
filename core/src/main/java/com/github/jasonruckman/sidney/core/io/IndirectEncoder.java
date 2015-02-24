package com.github.jasonruckman.sidney.core.io;

import com.github.jasonruckman.sidney.core.io.output.Output;

public abstract class IndirectEncoder implements Encoder {
  protected Output output;

  public void flush() {

  }

  @Override
  public IndirectEncoder asIndirect() {
    return this;
  }

  @Override
  public DirectEncoder asDirect() {
    throw new IllegalStateException();
  }

  public Output getOutput() {
    return output;
  }

  public void setOutput(Output output) {
    this.output = output;
  }

  @Override
  public void reset() {

  }

  @Override
  public boolean isDirect() {
    return false;
  }
}