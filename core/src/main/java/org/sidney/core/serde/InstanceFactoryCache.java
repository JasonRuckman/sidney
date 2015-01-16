/**
 * Copyright 2014 Jason Ruckman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sidney.core.serde;

import java.util.HashMap;
import java.util.Map;

public class InstanceFactoryCache {
    private Class lastClass;
    private InstanceFactory lastFactory;
    private Map<Class, InstanceFactory> factories = new HashMap<>();

    public Object newInstance(Class type) {
        if (type == lastClass) {
            return lastFactory.newInstance();
        }

        lastFactory = factories.get(type);
        lastClass = type;

        if (lastFactory == null) {
            lastFactory = new InstanceFactory(type);
            factories.put(type, lastFactory);
        }
        return lastFactory.newInstance();
    }
}
