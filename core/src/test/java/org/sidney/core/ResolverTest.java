package org.sidney.core;

import org.junit.Test;
import org.sidney.core.resolver.Resolver;

import java.util.ArrayList;
import java.util.List;

public class ResolverTest {
    @Test
    public void testOrdering() {
        Resolver<TestClass> resolver = new Resolver<>(TestClass.class);
        List<Resolver<?>> resolvers = new ArrayList<>();
        resolver.findLeaves(resolvers);
        int i = 0;
    }
}
