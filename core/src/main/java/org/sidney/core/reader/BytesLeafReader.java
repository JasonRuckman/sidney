package org.sidney.core.reader;

import org.sidney.core.field.FieldAccessor;

public class BytesLeafReader implements LeafReader {
    @Override
    public void readRecordIntoField(Reader reader, Object parent, int index, FieldAccessor accessor) {
        accessor.set(parent, reader.nextBytes(index));
    }
}