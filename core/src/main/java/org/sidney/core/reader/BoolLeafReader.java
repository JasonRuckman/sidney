package org.sidney.core.reader;

import org.sidney.core.field.FieldAccessor;

public class BoolLeafReader implements LeafReader {
    @Override
    public void readRecordIntoField(Reader reader, Object parent, int index, FieldAccessor accessor) {
        accessor.setBoolean(parent, reader.nextBool(index));
    }
}
