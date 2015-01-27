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

public class ObjectReader<T> extends BaseReader implements Reader<T> {
    public ObjectReader(Class type, Registrations registrations, Class[] typeParams) {
        super(registrations);

        this.type = type;
        this.typeParams = typeParams;
        this.serializer = builder.serializer(TypeRefBuilder.typeRef(
                type, typeParams
        ), null);
    }

    /**
     * Read the next item from the stream
     *
     * @return the next item
     */
    public T read() {
        if (!isOpen) {
            throw new SidneyException("Reader is not open.");
        }
        context.setColumnIndex(0);
        return (T) serializer.readValue(typeReader, context);
    }
}