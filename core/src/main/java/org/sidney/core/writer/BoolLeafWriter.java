package org.sidney.core.writer;

import org.sidney.core.field.FieldAccessor;

public class BoolLeafWriter implements LeafWriter {
    @Override
    public void writeRecordFromField(ColumnWriter consumer, Object parent, int index, FieldAccessor accessor) {
        consumer.writeBoolean(index, accessor.getBoolean(parent));
    }
}
