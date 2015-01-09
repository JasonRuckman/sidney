package org.sidney.core.serde;

public class ReadContext extends Context {
    private ColumnReader columnReader;

    public ReadContext(ColumnReader columnReader) {
        this.columnReader = columnReader;
    }

    public ColumnReader getColumnReader() {
        return columnReader;
    }

    public void setColumnReader(ColumnReader columnReader) {
        this.columnReader = columnReader;
    }
}