package org.sidney.core.resolver;

import org.sidney.core.field.FieldUtils;
import org.sidney.core.schema.Definition;
import org.sidney.core.schema.GroupDefinition;
import org.sidney.core.schema.Repetition;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class BeanResolver extends Resolver {
    public BeanResolver(Class<?> type, Field field) {
        super(type, field);
    }

    @Override
    public List<Resolver> children() {
        List<Resolver> children = new ArrayList<>();
        for (Field field : FieldUtils.getAllFields(getJdkType())) {
            children.add(ResolverFactory.resolver(field));
        }
        return children;
    }

    @Override
    public Definition definition() {
        String name = (getField() != null) ? getField().getName() : getJdkType().getName();
        GroupDefinition groupDef = new GroupDefinition(
                name, Repetition.OPTIONAL
        );

        for (Resolver resolver : children()) {
            groupDef.getChildren().add(
                    resolver.definition()
            );
        }

        return groupDef;
    }
}