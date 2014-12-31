package org.sidney.core.reader;

import org.sidney.core.field.FieldAccessor;

public abstract class NullableLeafReader implements LeafReader {
    @Override
    public void readRecordIntoField(ColumnReader columnReader, Object parent, int index, FieldAccessor accessor) {
        if(columnReader.readNullMarker(index)) {
            readIfNotNull(columnReader, parent, index, accessor);
        }
    }

    protected abstract void readIfNotNull(ColumnReader columnReader, Object parent, int index, FieldAccessor accessor);
}
