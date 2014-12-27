package org.sidney.core.column;

import org.junit.Test;
import org.sidney.core.TestClass;
import org.sidney.core.resolver.ResolverFactory;

public class MessageConsumerTest {
    @Test
    public void testSimple() {
        MessageConsumer consumer = new MessageConsumer(
                ResolverFactory.resolver(TestClass.class)
        );

        consumer.writeNotNull(0);
        consumer.writeInt(1, 1);
        consumer.writeLong(2, 1L);
    }
}
