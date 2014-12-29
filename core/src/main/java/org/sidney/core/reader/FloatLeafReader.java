package org.sidney.core.reader;

import org.sidney.core.field.FieldAccessor;

public class FloatLeafReader implements LeafReader {
    @Override
    public void readRecordIntoField(Reader reader, Object parent, int index, FieldAccessor accessor) {
        accessor.setFloat(parent, reader.nextFloat(index));
    }
}