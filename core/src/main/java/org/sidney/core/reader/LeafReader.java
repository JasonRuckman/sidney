package org.sidney.core.reader;

import org.sidney.core.field.FieldAccessor;

public interface LeafReader {
    void readRecordIntoField(Reader reader, Object parent, int index, FieldAccessor accessor);
}
