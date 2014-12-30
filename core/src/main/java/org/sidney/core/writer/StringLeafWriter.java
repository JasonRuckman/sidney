package org.sidney.core.writer;

import org.sidney.core.field.FieldAccessor;

public class StringLeafWriter implements LeafWriter {
    @Override
    public void writeRecordFromField(ColumnWriter consumer, Object parent, int index, FieldAccessor accessor) {
        consumer.writeString(index, (String) accessor.get(parent));
    }
}
