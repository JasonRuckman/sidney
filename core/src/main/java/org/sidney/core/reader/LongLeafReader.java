package org.sidney.core.reader;

import org.sidney.core.field.FieldAccessor;

public class LongLeafReader implements LeafReader {
    @Override
    public void readRecordIntoField(ColumnReader columnReader, Object parent, int index, FieldAccessor accessor) {
        accessor.setLong(parent, columnReader.nextLong(index));
    }
}
