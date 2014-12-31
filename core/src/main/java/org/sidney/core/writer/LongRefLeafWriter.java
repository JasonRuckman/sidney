package org.sidney.core.writer;

public class LongRefLeafWriter extends NullableLeafWriter {
    @Override
    protected void writeIfNotNull(Object value, int index, ColumnWriter consumer) {
        consumer.writeLong(index, (Long) value);
    }
}
