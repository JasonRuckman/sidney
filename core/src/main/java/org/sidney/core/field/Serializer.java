package org.sidney.core.field;

import org.sidney.core.column.MessageConsumer;
import org.sidney.core.resolver.Resolver;
import org.sidney.core.resolver.ResolverFactory;

import java.io.IOException;
import java.io.OutputStream;

public class Serializer<T> {
    private final Class<T> type;

    private MessageConsumer messageConsumer;
    private Resolver resolver;

    public Serializer(Class<T> type) {
        this.type = type;
        this.resolver = ResolverFactory.resolver(type);
        this.messageConsumer = new MessageConsumer(resolver);
    }

    public void serialize(T value) {
        resolver.writeRecord(messageConsumer, value, 0);
    }

    public void flush(OutputStream outputStream) throws IOException {
        messageConsumer.flushToOutputStream(outputStream);
        messageConsumer.prepare();
    }
}
