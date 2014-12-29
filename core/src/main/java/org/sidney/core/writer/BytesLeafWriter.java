package org.sidney.core.writer;

import org.sidney.core.MessageConsumer;
import org.sidney.core.field.FieldAccessor;
import org.sidney.core.resolver.LeafWriter;

public class BytesLeafWriter implements LeafWriter {
    @Override
    public void writeRecordFromField(MessageConsumer consumer, Object parent, int index, FieldAccessor accessor) {
        consumer.writeBytes(index, (byte[]) accessor.get(parent));
    }
}