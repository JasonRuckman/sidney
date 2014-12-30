package org.sidney.core.writer;

import org.sidney.core.writer.ColumnWriter;
import org.sidney.core.field.FieldAccessor;

public interface LeafWriter {
    void writeRecordFromField(ColumnWriter consumer, Object parent, int index, FieldAccessor accessor);
}