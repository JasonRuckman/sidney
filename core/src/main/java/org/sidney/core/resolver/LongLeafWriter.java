package org.sidney.core.resolver;

import org.sidney.core.column.MessageConsumer;
import org.sidney.core.field.FieldAccessor;

public class LongLeafWriter implements LeafWriter {
    @Override
    public void writeRecordFromField(MessageConsumer consumer, Object parent, int index, FieldAccessor accessor) {
        consumer.writeLong(index, accessor.getLong(parent));
    }
}
