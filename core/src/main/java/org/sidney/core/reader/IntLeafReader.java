package org.sidney.core.reader;

import org.sidney.core.field.FieldAccessor;

public class IntLeafReader implements LeafReader {
    @Override
    public void readRecordIntoField(ColumnReader columnReader, Object parent, int index, FieldAccessor accessor) {
        accessor.setInt(parent, columnReader.nextInt(index));
    }
}
