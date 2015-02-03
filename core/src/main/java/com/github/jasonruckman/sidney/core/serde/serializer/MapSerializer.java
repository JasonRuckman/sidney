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
package com.github.jasonruckman.sidney.core.serde.serializer;

import com.github.jasonruckman.sidney.core.TypeRef;
import com.github.jasonruckman.sidney.core.serde.ReadContext;
import com.github.jasonruckman.sidney.core.serde.WriteContext;

import java.util.Map;

public class MapSerializer extends Serializer<Map> {
    private Serializer keySerializer;
    private Serializer valueSerializer;
    private InstanceFactoryCache cache = new InstanceFactoryCache();

    @Override
    public void consume(TypeRef typeRef, SerializerContext context) {
        keySerializer = context.serializer(typeRef.getTypeParameters().get(0), this);
        valueSerializer = context.serializer(typeRef.getTypeParameters().get(1), this);

        addNumFieldsToIncrementBy(keySerializer.getNumFieldsToIncrementBy());
        addNumFieldsToIncrementBy(valueSerializer.getNumFieldsToIncrementBy());
    }

    @Override
    public void writeValue(Object value, WriteContext context) {
        writeMap((Map) value, context);
    }

    @Override
    public Object readValue(ReadContext context) {
        return readMap(context);
    }

    @Override
    public boolean requiresTypeColumn() {
        return true;
    }

    private void writeMap(Map map, WriteContext context) {
        if (context.writeNullMarkerAndType(map)) {
            context.writeRepetitionCount(map.size());
            //bump index forward to key
            context.incrementColumnIndex();
            int valueIndex = context.getColumnIndex();
            for (Object e : map.entrySet()) {
                context.setColumnIndex(valueIndex); //for each entry, make sure we are starting at the root
                Map.Entry entry = (Map.Entry) e;
                keySerializer.writeValue(entry.getKey(), context);
                valueSerializer.writeValue(entry.getValue(), context);
            }
        } else {
            context.incrementColumnIndex(getNumFieldsToIncrementBy() + 1);
        }
    }

    private Map readMap(ReadContext context) {
        if (context.readNullMarker()) {
            Map map = (Map) cache.newInstance(context.readConcreteType());
            int size = context.readRepetitionCount();
            context.incrementColumnIndex();
            int idx = context.getColumnIndex();
            for (int i = 0; i < size; i++) {
                context.setColumnIndex(idx);
                map.put(keySerializer.readValue(context), valueSerializer.readValue(context));
            }
            return map;
        }
        context.incrementColumnIndex(getNumFieldsToIncrementBy() + 1);
        return null;
    }
}