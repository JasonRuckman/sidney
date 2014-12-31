package org.sidney.core.writer;

public class BoolRefLeafWriter extends NullableLeafWriter {
    @Override
    protected void writeIfNotNull(Object value, int index, ColumnWriter consumer) {
        consumer.writeBoolean(index, (Boolean) value);
    }
}
