package org.sidney.core.resolver;

import org.sidney.core.field.Writer;
import org.sidney.core.schema.Definition;

import java.lang.reflect.Field;
import java.util.List;

public class CollectionResolver extends Resolver {
    public CollectionResolver(Class type, Field field) {
        super(type, field);
    }

    @Override
    public List<Resolver> children() {
        return null;
    }

    @Override
    public Writer consumer() {
        return null;
    }

    @Override
    public Definition definition() {
        //call child, wrap it in a repeated group
        return null;
    }
}
