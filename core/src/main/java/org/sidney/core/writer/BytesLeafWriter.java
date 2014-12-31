package org.sidney.core.writer;

import org.sidney.core.field.FieldAccessor;

public class BytesLeafWriter implements LeafWriter {
    @Override
    public void writeRecordFromField(ColumnWriter consumer, Object parent, int index, FieldAccessor accessor) {
        byte[] bytes = (byte[]) accessor.get(parent);
        if(bytes == null) {
            consumer.writeNull(index - 1);
            return;
        }
        consumer.writeNotNull(index - 1);
        consumer.writeBytes(index, bytes);
    }
}