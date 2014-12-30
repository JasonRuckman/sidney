package org.sidney.core.reader;

import org.sidney.core.field.FieldAccessor;

public class BytesLeafReader implements LeafReader {
    @Override
    public void readRecordIntoField(ColumnReader columnReader, Object parent, int index, FieldAccessor accessor) {
        accessor.set(parent, columnReader.nextBytes(index));
    }
}