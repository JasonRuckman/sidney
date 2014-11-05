package org.sidney.core;

import org.junit.Test;
import org.sidney.core.resolver.Resolver;
import org.sidney.core.resolver.ResolverFactory;

public class ResolverTest {
    @Test
    public void testOrdering() {
        Resolver resolver = ResolverFactory.resolver(TestClass.class);
        int i = 0;
    }
}
