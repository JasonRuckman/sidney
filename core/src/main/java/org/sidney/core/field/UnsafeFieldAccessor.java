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
package org.sidney.core.field;

import org.sidney.core.encoding.unsafe.UnsafeUtil;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeFieldAccessor implements FieldAccessor {
    private final Field field;
    private final long offset;
    private final Unsafe unsafe;

    public UnsafeFieldAccessor(Field field) {
        this.field = field;
        this.unsafe = UnsafeUtil.unsafe();
        this.offset = unsafe.objectFieldOffset(field);
    }

    @Override
    public Field getField() {
        return field;
    }

    @Override
    public boolean getBoolean(Object target) {
        return this.unsafe.getBoolean(target, offset);
    }

    @Override
    public byte getByte(Object target) {
        return this.unsafe.getByte(target, offset);
    }

    @Override
    public char getChar(Object target) {
        return this.unsafe.getChar(target, offset);
    }

    @Override
    public short getShort(Object target) {
        return this.unsafe.getShort(target, offset);
    }

    @Override
    public int getInt(Object target) {
        return this.unsafe.getInt(target, offset);
    }

    @Override
    public long getLong(Object target) {
        return this.unsafe.getLong(target, offset);
    }

    @Override
    public float getFloat(Object target) {
        return this.unsafe.getFloat(target, offset);
    }

    @Override
    public double getDouble(Object target) {
        return this.unsafe.getDouble(target, offset);
    }

    @Override
    public Object get(Object o) {
        return this.unsafe.getObject(o, offset);
    }

    @Override
    public void set(Object target, Object value) {
        this.unsafe.putObject(target, offset, value);
    }

    @Override
    public void setBoolean(Object target, boolean b) {
        this.unsafe.putBoolean(target, offset, b);
    }

    @Override
    public void setByte(Object target, byte b) {
        this.unsafe.putByte(target, offset, b);
    }

    @Override
    public void setChar(Object target, char c) {
        this.unsafe.putChar(target, offset, c);
    }

    @Override
    public void setShort(Object target, short s) {
        this.unsafe.putShort(target, offset, s);
    }

    @Override
    public void setInt(Object target, int i) {
        this.unsafe.putInt(target, offset, i);
    }

    @Override
    public void setLong(Object target, long l) {
        this.unsafe.putLong(target, offset, l);
    }

    @Override
    public void setFloat(Object target, float f) {
        this.unsafe.putFloat(target, offset, f);
    }

    @Override
    public void setDouble(Object target, double d) {
        this.unsafe.putDouble(target, offset, d);
    }
}