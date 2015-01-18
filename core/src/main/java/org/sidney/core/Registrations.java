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
package org.sidney.core;

import org.sidney.core.serde.handler.TypeHandler;

import java.util.HashMap;
import java.util.Map;

public class Registrations {
    private Map<Class, Class<? extends TypeHandler>> concreteRegistrations = new HashMap<>();
    private Map<Class, Class<? extends TypeHandler>> superClassRegistrations = new HashMap<>();

    public Map<Class, Class<? extends TypeHandler>> getConcreteRegistrations() {
        return concreteRegistrations;
    }

    public Map<Class, Class<? extends TypeHandler>> getSuperClassRegistrations() {
        return superClassRegistrations;
    }

    public void registerSuperclass(Class type, Class<? extends TypeHandler> handlerType) {
        superClassRegistrations.put(
                type, handlerType
        );
    }

    public void registerConcrete(Class type, Class<? extends TypeHandler> handlerType) {
        concreteRegistrations.put(
                type, handlerType
        );
    }

    public boolean empty() {
        return superClassRegistrations.size() == 0 && concreteRegistrations.size() == 0;
    }
}