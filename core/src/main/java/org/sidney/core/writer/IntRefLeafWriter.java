package org.sidney.core.writer;

public class IntRefLeafWriter extends NullableLeafWriter {
    @Override
    protected void writeIfNotNull(Object value, int index, ColumnWriter consumer) {
        consumer.writeInt(index, (Integer) value);
    }
}
