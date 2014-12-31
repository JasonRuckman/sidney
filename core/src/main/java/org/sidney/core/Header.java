package org.sidney.core;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

public class Header {
    private Class topLevelType;
    private Map<Class, Integer> classToValueMap = new IdentityHashMap<>();

    private transient Map<Integer, Class> valueToClassMap = new HashMap<>();
    private transient int counter = 0;

    public Class getTopLevelType() {
        return topLevelType;
    }

    public void setTopLevelType(Class topLevelType) {
        this.topLevelType = topLevelType;
    }

    public Map<Class, Integer> getClassToValueMap() {
        return classToValueMap;
    }

    public void setClassToValueMap(HashMap<Class, Integer> classToValueMap) {
        this.classToValueMap = classToValueMap;
    }

    public int concreteType(Class clazz) {
        Integer value = classToValueMap.get(clazz);
        if(value == null) {
            value = counter++;
            classToValueMap.put(clazz, value);
        }
        return value;
    }

    public Class readConcreteType(int value) {
        return valueToClassMap.get(value);
    }

    public void prepareForRead() {
        valueToClassMap = new HashMap<>();
        for(Map.Entry<Class, Integer> entry : classToValueMap.entrySet()) {
            valueToClassMap.put(entry.getValue(), entry.getKey());
        }
    }
}