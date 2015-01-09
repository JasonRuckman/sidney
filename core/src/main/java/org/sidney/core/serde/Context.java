package org.sidney.core.serde;

import org.sidney.core.Header;

public class Context {
    private int index = 0;
    private Header header;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
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
