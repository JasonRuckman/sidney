package org.sidney.core.resolver;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class PrimitiveResolver extends Resolver {
    public PrimitiveResolver(Class type, Field field) {
        super(type, field);
    }

    @Override
    protected List<Resolver> children() {
        return new ArrayList<>();
    }
}