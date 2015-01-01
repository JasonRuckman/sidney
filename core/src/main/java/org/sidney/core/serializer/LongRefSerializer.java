package org.sidney.core.serializer;

import org.sidney.core.writer.ColumnWriter;

import java.lang.reflect.Field;

public class LongRefSerializer extends PrimitiveSerializer {
    public LongRefSerializer(Class type, Field field) {
        super(type, field);
    }

    @Override
    public int writeRecord(ColumnWriter consumer, Object value, int index) {
        consumer.writeLong(index, (Long) value);
        return 1;
    }
}
