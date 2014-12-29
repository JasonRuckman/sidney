package org.sidney.core.reader;

import org.sidney.core.field.FieldAccessor;

public class DoubleLeafReader implements LeafReader {
    @Override
    public void readRecordIntoField(Reader reader, Object parent, int index, FieldAccessor accessor) {
        accessor.setDouble(parent, reader.nextDouble(index));
    }
}