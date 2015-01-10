package org.sidney.core.serde;

import java.util.HashMap;
import java.util.Map;

public class InstanceFactoryCache {
    private Class lastClass;
    private InstanceFactory lastFactory;
    private Map<Class, InstanceFactory> factories = new HashMap<>();

    public Object newInstance(Class type) {
        if(type == lastClass) {
            return lastFactory.newInstance();
        }

        lastFactory = factories.get(type);
        lastClass = type;

        if(lastFactory == null) {
            lastFactory = new InstanceFactory(type);
            factories.put(type, lastFactory);
        }
        return lastFactory.newInstance();
    }
}
