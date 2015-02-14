package com.github.jasonruckman.sidney.core.serde;

public class ReadMetaImpl implements ReadContext.Meta {
  private final ReadContextImpl parent;

  public ReadMetaImpl(ReadContextImpl parent) {
    this.parent = parent;
  }

  @Override
  public Class<?> readConcreteType() {
    return parent.column(getPosition()).readConcreteType(parent);
  }

  @Override
  public int readRepetitionCount() {
    return parent.column(getPosition()).readRepetitionCount();
  }

  private int getPosition() {
    return parent.getColumnIndex();
  }
}
