package org.sidney.core;

import org.sidney.core.serde.TypeHandler;

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