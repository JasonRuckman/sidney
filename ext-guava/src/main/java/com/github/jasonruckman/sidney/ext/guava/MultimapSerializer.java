package com.github.jasonruckman.sidney.ext.guava;

import com.github.jasonruckman.sidney.core.BaseSid;
import com.github.jasonruckman.sidney.core.TypeRef;
import com.github.jasonruckman.sidney.core.serde.InstanceFactory;
import com.github.jasonruckman.sidney.core.serde.InstanceFactoryCache;
import com.github.jasonruckman.sidney.core.serde.ReadContext;
import com.github.jasonruckman.sidney.core.serde.WriteContext;
import com.github.jasonruckman.sidney.core.serde.serializer.Serializer;
import com.github.jasonruckman.sidney.core.serde.serializer.SerializerContext;
import com.github.jasonruckman.sidney.core.serde.serializer.jdkserializers.Primitives;
import com.google.common.collect.*;

import java.util.Collection;
import java.util.Map;

public class MultimapSerializer<K, V> extends Serializer<Multimap<K, V>> {
  private Serializer<K> keySerializer;
  private Serializer<V> valueSerializer;
  private Primitives.IntSerializer sizeSerializer;
  private InstanceFactoryCache cache;

  @Override
  public void consume(TypeRef typeRef, SerializerContext context) {
    keySerializer = context.serializer(typeRef.param(0));
    sizeSerializer = context.intSerializer();
    valueSerializer = context.serializer(typeRef.param(1));
    cache = new InstanceFactoryCache(getFactories());
  }

  @Override
  public void writeValue(Multimap<K, V> value, WriteContext context) {
    Map<K, Collection<V>> map = value.asMap();
    context.getMeta().writeConcreteType(value.getClass());
    context.getMeta().writeRepetitionCount(map.size());
    for (Map.Entry<K, Collection<V>> entry : map.entrySet()) {
      keySerializer.writeValue(entry.getKey(), context);
      //can't use the defaults here, because its an inner class
      sizeSerializer.writeInt(entry.getValue().size(), context);
      for (V v : entry.getValue()) {
        valueSerializer.writeValue(v, context);
      }
    }
  }

  @Override
  public Multimap<K, V> readValue(ReadContext context) {
    Multimap<K, V> multimap = (Multimap<K, V>) cache.newInstance(context.getMeta().readConcreteType());
    int num = context.getMeta().readRepetitionCount();
    for (int i = 0; i < num; i++) {
      K key = keySerializer.readValue(context);
      int size = sizeSerializer.readInt(context);
      for (int j = 0; j < size; j++) {
        multimap.put(key, valueSerializer.readValue(context));
      }
    }
    return multimap;
  }

  @Override
  public boolean requiresTypeColumn() {
    return true;
  }

  public static void registerMaps(BaseSid sid) {
    sid.addSerializer(Multimap.class, MultimapSerializer.class);
    sid.addInstanceFactory(ArrayListMultimap.class, new InstanceFactory<ArrayListMultimap>() {
      @Override
      public ArrayListMultimap newInstance() {
        return ArrayListMultimap.create();
      }
    });
    sid.addInstanceFactory(LinkedListMultimap.class, new InstanceFactory<LinkedListMultimap>() {
      @Override
      public LinkedListMultimap newInstance() {
        return LinkedListMultimap.create();
      }
    });
    sid.addInstanceFactory(LinkedHashMultimap.class, new InstanceFactory<LinkedHashMultimap>() {
      @Override
      public LinkedHashMultimap newInstance() {
        return LinkedHashMultimap.create();
      }
    });
    sid.addInstanceFactory(TreeMultimap.class, new InstanceFactory<TreeMultimap>() {
      @Override
      public TreeMultimap newInstance() {
        return TreeMultimap.create();
      }
    });
    sid.addInstanceFactory(HashMultimap.class, new InstanceFactory<HashMultimap>() {
      @Override
      public HashMultimap newInstance() {
        return HashMultimap.create();
      }
    });
  }
}