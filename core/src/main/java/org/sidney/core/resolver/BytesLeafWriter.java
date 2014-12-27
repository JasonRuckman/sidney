package org.sidney.core.resolver;

import org.sidney.core.column.MessageConsumer;
import org.sidney.core.field.FieldAccessor;

public class BytesLeafWriter implements LeafWriter {
    @Override
    public void writeRecordFromField(MessageConsumer consumer, Object parent, int index, FieldAccessor accessor) {
        consumer.writeBytes(index, (byte[]) accessor.get(parent));
    }
}