package org.sidney.core.resolver;

import org.sidney.core.field.FieldUtils;
import org.sidney.core.field.Writer;
import org.sidney.core.schema.Definition;
import org.sidney.core.schema.GroupDefinition;
import org.sidney.core.schema.Repetition;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class BeanResolver extends Resolver {
    public BeanResolver(Class<?> type, Field field) {
        super(type, field);
    }

    @Override
    public List<Resolver> children() {
        return FieldUtils.getAllFields(getType())
                .stream()
                .map(ResolverFactory::resolver)
                .collect(Collectors.toList());
    }

    @Override
    public Writer consumer() {
        return null;
    }

    @Override
    public Definition definition() {
        String name = (getField() != null) ? getField().getName() : getType().getName();
        GroupDefinition groupDef = new GroupDefinition(
                name, Repetition.OPTIONAL
        );

        for(Resolver resolver : children()) {
            groupDef.getChildren().add(
                    resolver.definition()
            );
        }

        return groupDef;
    }
}