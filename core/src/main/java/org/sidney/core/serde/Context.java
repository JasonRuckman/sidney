package org.sidney.core.serde;

import org.sidney.core.PageHeader;

public class Context {
    private int index = 0;
    private PageHeader pageHeader;

    public PageHeader getPageHeader() {
        return pageHeader;
    }

    public void setPageHeader(PageHeader pageHeader) {
        this.pageHeader = pageHeader;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void incrementIndex() {
        ++index;
    }

    public void incrementIndex(int size) {
        index += size;
    }

    public void reset() {
        index = 0;
    }
}
