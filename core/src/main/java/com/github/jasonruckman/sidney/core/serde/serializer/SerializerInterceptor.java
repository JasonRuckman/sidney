package com.github.jasonruckman.sidney.core.serde.serializer;

import com.github.jasonruckman.sidney.core.Accessors;
import com.github.jasonruckman.sidney.core.TypeRef;
import com.github.jasonruckman.sidney.core.serde.ReadContext;
import com.github.jasonruckman.sidney.core.serde.WriteContext;

public class SerializerInterceptor<T> extends Serializer<T> {
  private final Serializer<T> delegate;

  public SerializerInterceptor(Serializer<T> delegate) {
    this.delegate = delegate;
  }

  @Override
  public void consume(TypeRef typeRef, SerializerContext context) {
    delegate.consume(typeRef, context);
  }

  @Override
  public void writeValue(T value, WriteContext context) {
    context.setColumnIndex(delegate.startIndex());
    if (context.shouldWriteValue(value)) {
      delegate.writeValue(value, context);
    }
  }

  @Override
  public T readValue(ReadContext context) {
    context.setColumnIndex(delegate.startIndex());
    T value;
    if (context.shouldReadValue()) {
      value = delegate.readValue(context);
    } else {
      value = null;
    }
    return value;
  }

  @Override
  protected Accessors.FieldAccessor getAccessor() {
    return delegate.getAccessor();
  }

  @Override
  public void setAccessor(Accessors.FieldAccessor accessor) {
    delegate.setAccessor(accessor);
  }
}