package org.sidney.core.serde.serializer;

public interface TypeConsumer {
    /**
     * Registers serializers in the order that they are created
     */
    void consume(Serializer serializer);
}
