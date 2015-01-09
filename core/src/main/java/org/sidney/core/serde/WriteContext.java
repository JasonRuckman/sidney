package org.sidney.core.serde;

import org.sidney.core.Header;

public class WriteContext extends Context {
    private ColumnWriter columnWriter;

    public WriteContext(ColumnWriter columnWriter) {
        this.columnWriter = columnWriter;
    }

    public WriteContext(ColumnWriter columnWriter, Header header) {
        this.columnWriter = columnWriter;
        setHeader(header);
    }

    public ColumnWriter getColumnWriter() {
        return columnWriter;
    }

    @Override
    public String toString() {
        return "WriteContext{" +
                "index=" + getIndex() +
                '}';
    }
}