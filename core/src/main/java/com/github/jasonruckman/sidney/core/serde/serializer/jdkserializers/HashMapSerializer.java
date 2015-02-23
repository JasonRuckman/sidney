package com.github.jasonruckman.sidney.core.serde.serializer.jdkserializers;

import com.github.jasonruckman.sidney.core.type.TypeRef;
import com.github.jasonruckman.sidney.core.serde.Contexts;
import com.github.jasonruckman.sidney.core.serde.serializer.Serializer;
import com.github.jasonruckman.sidney.core.serde.serializer.SerializerContext;

import java.util.*;

public class HashMapSerializer<K, V> extends Serializer<HashMap<K, V>> {
  private Serializer<K> keySerializer;
  private Serializer<V> valueSerializer;

  @Override
  public void initialize(TypeRef typeRef, SerializerContext context) {
    keySerializer = context.serializer(typeRef.getTypeParameters().get(0), this);
    valueSerializer = context.serializer(typeRef.getTypeParameters().get(1), this);
  }

  @Override
  public void writeValue(HashMap<K, V> value, Contexts.WriteContext context) {
    context.getMeta().writeRepetitionCount(value.size());
    for (Map.Entry<K, V> e : value.entrySet()) {
      keySerializer.writeValue(e.getKey(), context);
      valueSerializer.writeValue(e.getValue(), context);
    }
  }

  @Override
  public HashMap<K, V> readValue(Contexts.ReadContext context) {
    int size = context.getMeta().readRepetitionCount();
    return new HashMap<>(new MapView(size, context));
  }

  private class MapView extends AbstractMap<K, V> {
    private int size;
    private Contexts.ReadContext context;

    public MapView(int size, Contexts.ReadContext context) {
      this.size = size;
      this.context = context;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
      return new SetView();
    }

    public class SetView extends AbstractSet<Map.Entry<K, V>> {
      @Override
      public int size() {
        return size;
      }

      @Override
      public boolean isEmpty() {
        return size == 0;
      }

      @Override
      public Iterator<Entry<K, V>> iterator() {
        return new Iterator<Entry<K, V>>() {
          ViewEntry entry = new ViewEntry();
          private int counter = size();

          @Override
          public boolean hasNext() {
            return counter-- > 0;
          }

          @Override
          public Entry<K, V> next() {
            return entry;
          }

          @Override
          public void remove() {

          }

          class ViewEntry implements Entry<K, V> {
            @Override
            public K getKey() {
              return keySerializer.readValue(context);
            }

            @Override
            public V getValue() {
              return valueSerializer.readValue(context);
            }

            @Override
            public V setValue(V value) {
              return null;
            }
          }
        };
      }
    }
  }
}