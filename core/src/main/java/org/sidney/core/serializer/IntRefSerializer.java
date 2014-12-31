package org.sidney.core.serializer;

import org.sidney.core.reader.ColumnReader;
import org.sidney.core.writer.ColumnWriter;

import java.lang.reflect.Field;

public class IntRefSerializer extends PrimitiveSerializer {
    public IntRefSerializer(Class type, Field field) {
        super(type, field);
    }

    @Override
    public int writeRecord(ColumnWriter consumer, Object value, int index) {
        consumer.writeInt(index, (Integer) value);
        return 1;
    }

    @Override
    public Object nextRecord(ColumnReader columnReader, int index) {
        return columnReader.nextInt(index);
    }
}
