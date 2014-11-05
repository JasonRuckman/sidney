package org.sidney.core.resolver;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * When a class is empty, but still needs to be serialized (jodatime chronologies for example), serialize the class name as a string
 */
public class EmptyClassResolver extends Resolver {
    public EmptyClassResolver(Class type, Field field) {
        super(type, field);
    }

    @Override
    public List<Resolver> children() {
        return new ArrayList<>();
    }
}