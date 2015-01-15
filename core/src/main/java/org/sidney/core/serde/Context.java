package org.sidney.core.serde;

import org.sidney.core.PageHeader;

public class Context {
    private int columnIndex = 0;
    private PageHeader pageHeader;

    public PageHeader getPageHeader() {
        return pageHeader;
    }

    public void setPageHeader(PageHeader pageHeader) {
        this.pageHeader = pageHeader;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public void incrementColumnIndex() {
        ++columnIndex;
    }

    public void incrementColumnIndex(int size) {
        columnIndex += size;
    }

    public void resetColumnIndex() {
        columnIndex = 0;
    }
}
