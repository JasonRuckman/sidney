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

import java.io.OutputStream;

public interface Writer<T> {
    /**
     * Get the root type of this writer
     *
     * @return the root type
     */
    public Class<T> getType();

    /**
     * Write the given value
     */
    void write(T value);

    /**
     * Open this writer against the given {@link java.io.OutputStream}
     */
    void open(OutputStream outputStream);

    void flush();

    /**
     * Flush the last page and mark the writer as closed
     */
    void close();
}
