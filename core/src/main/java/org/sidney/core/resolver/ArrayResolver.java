package org.sidney.core.resolver;

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

        Class componentType = getJdkType().getComponentType();
        lengthResolver = ResolverFactory.resolver(int.class);
        componentResolver = ResolverFactory.resolver(componentType);
    }

    @Override
    public List<Resolver> children() {
        return Arrays.asList(lengthResolver, componentResolver);
    }

    @Override
    public Definition definition() {
        GroupDefinition arrayDefinition = new GroupDefinition(name(), Repetition.OPTIONAL);
        arrayDefinition.getChildren().add(lengthResolver.definition());
        Definition componentDefinition = componentResolver.definition();

        if (componentDefinition.isPrimitive()) {
            componentDefinition.setRepetition(Repetition.REPEATED);
        } else {
            GroupDefinition componentGroupDefinition = new GroupDefinition(
                    String.format("%s_array_value", componentDefinition.getName()),
                    Repetition.REPEATED
            );
            componentGroupDefinition.getChildren().add(componentDefinition);
            componentDefinition = componentGroupDefinition;
        }
        arrayDefinition.getChildren().add(componentDefinition);

        return arrayDefinition;
    }
}