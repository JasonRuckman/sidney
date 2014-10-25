package org.sidney.core.field;

import java.lang.reflect.Field;

public interface FieldAccessor {
    Field getField();

    boolean getBoolean(Object target);

    byte getByte(Object target);

    char getChar(Object target);

    short getShort(Object target);

    int getInt(Object target);

    long getLong(Object target);

    float getFloat(Object target);

    double getDouble(Object target);

    Object get(Object o);

    void set(Object target, Object value);

    void setBoolean(Object target, boolean b);

    void setByte(Object target, byte b);

    void setChar(Object target, char c);

    void setShort(Object target, short s);

    void setInt(Object target, int i);

    void setLong(Object target, long l);

    void setFloat(Object target, float f);

    void setDouble(Object target, double d);
}
