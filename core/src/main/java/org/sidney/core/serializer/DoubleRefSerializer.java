package org.sidney.core.serializer;

import org.sidney.core.writer.ColumnWriter;

import java.lang.reflect.Field;

public class DoubleRefSerializer extends PrimitiveSerializer {
    public DoubleRefSerializer(Class type, Field field) {
        super(type, field);
    }

    @Override
    public int writeRecord(ColumnWriter consumer, Object value, int index) {
        consumer.writeDouble(index, (Double) value);
        return 1;
    }
}
