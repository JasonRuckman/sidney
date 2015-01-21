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
package org.sidney.core.serde.serializer;

import com.fasterxml.jackson.databind.type.TypeBindings;

import java.lang.reflect.*;

/**
 * A helper base class for handling classes that can be generic
 */
public abstract class GenericSerializer<T> extends Serializer<T> {
    public GenericSerializer(Type jdkType,
                             Field field,
                             TypeBindings parentTypeBindings,
                             SerializerRepository serializerRepository, Class[] generics) {
        super(jdkType, field, parentTypeBindings, serializerRepository, generics);
    }
}