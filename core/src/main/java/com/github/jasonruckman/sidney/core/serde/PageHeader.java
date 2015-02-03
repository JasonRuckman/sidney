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
package com.github.jasonruckman.sidney.core.serde;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Represents data about the serialized page
 */
public class PageHeader {
    private boolean isLastPage;
    private int pageSize;
    private Map<Class, Integer> classToValueMap = new IdentityHashMap<>();
    private Map<Integer, Class> valueToClassMap = new HashMap<>();
    private int counter = 0;

    /**
     * Get the concrete type to value mapping
     */
    public Map<Class, Integer> getClassToValueMap() {
        return classToValueMap;
    }

    /**
     * Get the value to concrete type map
     */
    public Map<Integer, Class> getValueToClassMap() {
        return valueToClassMap;
    }

    /**
     * Get the number of records in this page
     *
     * @return the page size
     */
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Whether or not this is the last page that was serialized (set when the writer is closed)
     *
     * @return whether this is the last page
     */
    public boolean isLastPage() {
        return isLastPage;
    }

    /**
     * Set this page as the last page
     */
    public void setLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    /**
     * Return an integer value for
     *
     * @param clazz
     * @return
     */
    public int valueForType(Class clazz) {
        Integer value = classToValueMap.get(clazz);
        if (value == null) {
            value = counter++;
            classToValueMap.put(clazz, value);
        }
        return value;
    }

    public Class readConcreteType(int value) {
        return valueToClassMap.get(value);
    }
}