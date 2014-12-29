package org.sidney.core.reader;

import org.sidney.core.field.FieldAccessor;

public class LongLeafReader implements LeafReader {
    @Override
    public void readRecordIntoField(Reader reader, Object parent, int index, FieldAccessor accessor) {
        accessor.setLong(parent, reader.nextLong(index));
    }
}
