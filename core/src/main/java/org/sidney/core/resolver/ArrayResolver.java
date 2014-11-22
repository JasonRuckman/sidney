package org.sidney.core.resolver;

import org.sidney.core.field.Writer;
import org.sidney.core.schema.Definition;
import org.sidney.core.schema.GroupDefinition;
import org.sidney.core.schema.Repetition;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class ArrayResolver extends Resolver {
    private final Resolver lengthResolver;
    private final Resolver componentResolver;

    public ArrayResolver(Class type, Field field) {
        super(type, field);

        Class componentType = getType().getComponentType();
        lengthResolver = ResolverFactory.resolver(int.class);
        componentResolver = ResolverFactory.resolver(componentType);
    }

    @Override
    public List<Resolver> children() {
        return Arrays.asList(lengthResolver, componentResolver);
    }

    @Override
    public Writer consumer() {
        return null;
    }

    @Override
    public Definition definition() {
        //this is wrong, change it so that we re-write the component to repeated
        GroupDefinition definition = new GroupDefinition(name(), Repetition.OPTIONAL);
        definition.getChildren().add(lengthResolver.definition());
        definition.getChildren().add(componentResolver.definition());
        return definition;
    }
}
