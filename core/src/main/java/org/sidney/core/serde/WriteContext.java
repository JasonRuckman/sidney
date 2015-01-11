package org.sidney.core.serde;

import org.sidney.core.PageHeader;

public class WriteContext extends Context {
    private ColumnWriter columnWriter;

    public WriteContext(ColumnWriter columnWriter) {
        this.columnWriter = columnWriter;
    }

    public WriteContext(ColumnWriter columnWriter, PageHeader pageHeader) {
        this.columnWriter = columnWriter;
        setPageHeader(pageHeader);
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