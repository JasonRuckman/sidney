package org.sidney.core.reader;

import org.sidney.core.field.FieldAccessor;

public class BoolLeafReader implements LeafReader {
    @Override
    public void readRecordIntoField(ColumnReader columnReader, Object parent, int index, FieldAccessor accessor) {
        accessor.setBoolean(parent, columnReader.nextBoolean(index));
    }
}
