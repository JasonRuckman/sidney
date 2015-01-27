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

import org.sidney.core.Registrations;
import org.sidney.core.SidneyException;
import org.sidney.core.TypeRefBuilder;
import org.sidney.core.serde.serializer.Serializer;


public class ObjectWriter<T> extends BaseWriter implements Writer<T> {
    public static final int DEFAULT_PAGE_SIZE = 256;

    protected final Class<T> type;
    protected final Serializer serializer;

    public ObjectWriter(Class type, Registrations registrations, Class[] typeParams) {
        super(registrations);

        this.type = type;
        ColumnWriter writer = new ColumnWriter();
        this.serializer = builder.serializer(TypeRefBuilder.typeRef(type, typeParams), null);
        this.builder.finish(writer);
        this.context = new WriteContext(writer, new PageHeader());
    }

    /**
     * Write the given value
     */
    public void write(T value) {
        if (!isOpen) {
            throw new SidneyException("Cannot write to a closed writer");
        }
        context.setColumnIndex(0);
        serializer.writeValue(value, typeWriter, context);

        if (++recordCount == DEFAULT_PAGE_SIZE) {
            flush();
        }
    }

    /**
     * Get the root type of this writer
     *
     * @return the root type
     */
    public Class<T> getType() {
        return type;
    }
}
