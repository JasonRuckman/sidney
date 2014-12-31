package org.sidney.core.writer;

import org.sidney.core.field.FieldAccessor;

public abstract class NullableLeafWriter implements LeafWriter {
    @Override
    public void writeRecordFromField(ColumnWriter consumer, Object parent, int index, FieldAccessor accessor) {
        Object value = accessor.get(parent);
        if(value == null) {
            consumer.writeNull(index);
        } else {
            consumer.writeNotNull(index);
        }

        writeIfNotNull(value, index, consumer);
    }

    protected abstract void writeIfNotNull(Object value, int index, ColumnWriter consumer);
}
