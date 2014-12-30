package org.sidney.core.reader;

import org.sidney.core.field.FieldAccessor;

public class FloatLeafReader implements LeafReader {
    @Override
    public void readRecordIntoField(ColumnReader columnReader, Object parent, int index, FieldAccessor accessor) {
        accessor.setFloat(parent, columnReader.nextFloat(index));
    }
}