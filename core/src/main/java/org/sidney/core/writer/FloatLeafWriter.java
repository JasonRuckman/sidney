package org.sidney.core.writer;

import org.sidney.core.field.FieldAccessor;

public class FloatLeafWriter implements LeafWriter {
    @Override
    public void writeRecordFromField(ColumnWriter consumer, Object parent, int index, FieldAccessor accessor) {
        consumer.writeFloat(index, accessor.getFloat(parent));
    }
}