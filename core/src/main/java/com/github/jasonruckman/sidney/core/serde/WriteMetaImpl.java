package com.github.jasonruckman.sidney.core.serde;

public class WriteMetaImpl implements WriteContext.Meta {
  private WriteContextImpl parent;

  public WriteMetaImpl(WriteContextImpl parent) {
    this.parent = parent;
  }

  @Override
  public void writeConcreteType(Class<?> type) {
    parent.column(getPosition()).writeConcreteType(type, parent);
  }

  @Override
  public void writeRepetitionCount(int count) {
    parent.column(getPosition()).writeRepetitionCount(count);
  }

  private int getPosition() {
    //meta is always -1 from the values
    return parent.getColumnIndex();
  }
}
