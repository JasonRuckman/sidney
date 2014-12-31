package org.sidney.core.reader;

import org.sidney.core.field.FieldAccessor;

public class IntRefLeafReader extends NullableLeafReader {
    @Override
    protected void readIfNotNull(ColumnReader columnReader, Object parent, int index, FieldAccessor accessor) {
        accessor.set(parent, columnReader.nextInt(index));
    }
}