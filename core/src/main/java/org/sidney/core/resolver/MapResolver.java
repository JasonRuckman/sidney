package org.sidney.core.resolver;

import org.sidney.core.schema.Definition;
import org.sidney.core.schema.GroupDefinition;
import org.sidney.core.schema.Repetition;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class MapResolver extends GenericResolver {
    public MapResolver(Class type, Field field) {
        super(type, field);
    }

    public MapResolver(Class type, Class[] generics) {
        super(type, generics);
    }

    @Override
    public List<Resolver> children() {
        return Arrays.asList(
                ResolverFactory.resolver(getGenerics()[0]),
                ResolverFactory.resolver(getGenerics()[1])
        );
    }

    @Override
    public Definition definition() {
        //this is wrong but leave it for now
        GroupDefinition definition = new GroupDefinition(
                name(), Repetition.OPTIONAL
        );

        for (Resolver child : children()) {
            definition.getChildren().add(child.definition());
        }

        //eventually rewrite the name / types
        return definition;
    }
}