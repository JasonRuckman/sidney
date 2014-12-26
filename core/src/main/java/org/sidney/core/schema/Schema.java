package org.sidney.core.schema;

import org.sidney.core.resolver.Resolver;
import org.sidney.core.resolver.ResolverFactory;

import java.util.ArrayList;
import java.util.List;

public class Schema<T> {
    private final List<Definition> fields = new ArrayList<>();
    private final Class<T> source;

    public Schema(Class<T> source) {
        this.source = source;
    }

    public static <T> Schema<T> schema(Class<T> type) {
        Schema<T> schema = new Schema<>(type);
        //get the resolver, but then lift its children
        for (Resolver resolver : ResolverFactory.resolver(type).children()) {
            schema.fields.add(resolver.definition());
        }

        return schema;
    }

    public List<Definition> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        return "Schema{" +
                "fields=" + fields +
                ", source=" + source +
                '}';
    }
}