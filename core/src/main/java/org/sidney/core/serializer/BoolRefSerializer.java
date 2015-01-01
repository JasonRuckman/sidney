package org.sidney.core.serializer;

import org.sidney.core.writer.ColumnWriter;

import java.lang.reflect.Field;

public class BoolRefSerializer extends PrimitiveSerializer {
    public BoolRefSerializer(Class type, Field field) {
        super(type, field);
    }

    @Override
    public int writeRecord(ColumnWriter consumer, Object value, int index) {
        consumer.writeBoolean(index, (Boolean) value);
        return 1;
    }
}