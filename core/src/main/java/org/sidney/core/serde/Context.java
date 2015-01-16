/**
 * Copyright 2014 Jason Ruckman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
