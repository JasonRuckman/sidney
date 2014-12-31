package org.sidney.core.writer;

import org.sidney.core.field.FieldAccessor;

public class FloatRefLeafWriter extends NullableLeafWriter {
    @Override
    protected void writeIfNotNull(Object value, int index, ColumnWriter consumer) {
        consumer.writeFloat(index, (Float) value);
    }
}
