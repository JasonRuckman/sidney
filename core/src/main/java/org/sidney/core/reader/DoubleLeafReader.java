package org.sidney.core.reader;

import org.sidney.core.field.FieldAccessor;

public class DoubleLeafReader implements LeafReader {
    @Override
    public void readRecordIntoField(ColumnReader columnReader, Object parent, int index, FieldAccessor accessor) {
        accessor.setDouble(parent, columnReader.nextDouble(index));
    }
}