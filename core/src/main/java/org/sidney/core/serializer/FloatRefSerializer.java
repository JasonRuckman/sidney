package org.sidney.core.serializer;

import org.sidney.core.writer.ColumnWriter;

import java.lang.reflect.Field;

public class FloatRefSerializer extends PrimitiveSerializer {
    public FloatRefSerializer(Class type, Field field) {
        super(type, field);
    }

    @Override
    public int writeRecord(ColumnWriter consumer, Object value, int index) {
        consumer.writeFloat(index, (Float) value);
        return 1;
    }
}
