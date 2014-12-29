package org.sidney.core.reader;

import org.sidney.core.field.FieldAccessor;

public class IntLeafReader implements LeafReader {
    @Override
    public void readRecordIntoField(Reader reader, Object parent, int index, FieldAccessor accessor) {
        accessor.setInt(parent, reader.nextInt(index));
    }
}
