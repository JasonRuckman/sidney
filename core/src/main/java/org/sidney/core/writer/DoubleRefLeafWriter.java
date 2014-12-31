package org.sidney.core.writer;

public class DoubleRefLeafWriter extends NullableLeafWriter {
    @Override
    protected void writeIfNotNull(Object value, int index, ColumnWriter consumer) {
        consumer.writeDouble(index, (Double) value);
    }
}
