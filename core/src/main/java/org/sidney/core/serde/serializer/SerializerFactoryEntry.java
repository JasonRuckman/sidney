package org.sidney.core.serde.serializer;

public class SerializerFactoryEntry {
    private Class type;
    private SerializerFactory serializerFactory;

    public SerializerFactoryEntry(Class type, SerializerFactory serializerFactory) {
        this.type = type;
        this.serializerFactory = serializerFactory;
    }

    public Class getType() {
        return type;
    }

    public SerializerFactory getSerializerFactory() {
        return serializerFactory;
    }

    @Override
    public String toString() {
        return "SerializerFactoryEntry{" +
                "type=" + type +
                ", serializerFactory=" + serializerFactory +
                '}';
    }
}
