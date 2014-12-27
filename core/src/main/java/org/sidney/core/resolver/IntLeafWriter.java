package org.sidney.core.resolver;

import org.sidney.core.column.MessageConsumer;
import org.sidney.core.field.FieldAccessor;

public class IntLeafWriter implements LeafWriter {
    @Override
    public void writeRecordFromField(MessageConsumer consumer, Object parent, int index, FieldAccessor accessor) {
        consumer.writeInt(index, accessor.getInt(parent));
    }
}
