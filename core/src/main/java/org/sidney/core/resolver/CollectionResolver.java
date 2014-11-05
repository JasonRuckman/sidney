package org.sidney.core.resolver;

import java.lang.reflect.Field;
import java.util.List;

public class CollectionResolver extends Resolver {
    public CollectionResolver(Class type, Field field) {
        super(type, field);
    }

    @Override
    protected List<Resolver> children() {
        return null;
    }
}
