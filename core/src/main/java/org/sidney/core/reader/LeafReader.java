package org.sidney.core.reader;

import org.sidney.core.field.FieldAccessor;

public interface LeafReader {
    void readRecordIntoField(ColumnReader columnReader, Object parent, int index, FieldAccessor accessor);
}
