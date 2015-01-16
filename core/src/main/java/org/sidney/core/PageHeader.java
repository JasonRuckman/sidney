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
package org.sidney.core;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

public class PageHeader {
    private Map<String, Integer> classNameToValue = new HashMap<>();
    private boolean isLastPage;
    private int pageSize;

    private transient Map<Class, Integer> classToValueMap = new IdentityHashMap<>();
    private transient Map<Integer, Class> valueToClassMap = new HashMap<>();
    private transient int counter = 0;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public Map<String, Integer> getClassNameToValue() {
        return classNameToValue;
    }

    public void setClassNameToValue(Map<String, Integer> classNameToValue) {
        this.classNameToValue = classNameToValue;
    }

    public int valueForType(Class clazz) {
        Integer value = classToValueMap.get(clazz);
        if (value == null) {
            value = counter++;
            classToValueMap.put(clazz, value);
        }
        return value;
    }

    public Class classForValue(int value) {
        return valueToClassMap.get(value);
    }

    public Class readConcreteType(int value) {
        return valueToClassMap.get(value);
    }

    public void prepareForStorage() {
        for (Map.Entry<Class, Integer> entry : classToValueMap.entrySet()) {
            classNameToValue.put(entry.getKey().getName(), entry.getValue());
        }
    }

    public void prepareForRead() throws ClassNotFoundException {
        valueToClassMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : classNameToValue.entrySet()) {
            valueToClassMap.put(entry.getValue(), Class.forName(entry.getKey()));
        }
    }
}