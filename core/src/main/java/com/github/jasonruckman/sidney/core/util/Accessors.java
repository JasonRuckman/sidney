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
package com.github.jasonruckman.sidney.core.util;

import com.github.jasonruckman.sidney.core.Configuration;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class Accessors {
  public static FieldAccessor newAccessor(Field field) {
    return new ReflectionFieldAccessor(field);
  }

  public static FieldAccessor newAccessor(Configuration configuration, Field field) {
    if (configuration.isUseUnsafe()) {
      return new UnsafeFieldAccessor(field);
    }
    return new ReflectionFieldAccessor(field);
  }

  public static interface FieldAccessor {
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

  /**
   * Uses reflection to access fields, usually used as a fall back to byte-code generation if the fields are private
   */
  static class ReflectionFieldAccessor implements FieldAccessor {
    private final Field field;

    public ReflectionFieldAccessor(Field field) {
      field.setAccessible(true);

      this.field = field;
    }

    @Override
    public Field getField() {
      return field;
    }

    @Override
    public boolean getBoolean(Object target) {
      try {
        return this.field.getBoolean(target);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }

    @Override
    public byte getByte(Object target) {
      try {
        return this.field.getByte(target);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }

    @Override
    public char getChar(Object target) {
      try {
        return this.field.getChar(target);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }

    @Override
    public short getShort(Object target) {
      try {
        return this.field.getShort(target);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }

    @Override
    public int getInt(Object target) {
      try {
        return this.field.getInt(target);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }

    @Override
    public long getLong(Object target) {
      try {
        return this.field.getLong(target);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }

    @Override
    public float getFloat(Object target) {
      try {
        return this.field.getFloat(target);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }

    @Override
    public double getDouble(Object target) {
      try {
        return this.field.getDouble(target);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }

    public Object get(Object o) {
      try {
        return this.field.get(o);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }

    @Override
    public void set(Object target, Object value) {
      try {
        this.field.set(target, value);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }

    @Override
    public void setBoolean(Object target, boolean b) {
      try {
        this.field.setBoolean(target, b);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }

    @Override
    public void setByte(Object target, byte b) {
      try {
        this.field.setByte(target, b);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }

    @Override
    public void setChar(Object target, char c) {
      try {
        this.field.setChar(target, c);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }

    @Override
    public void setShort(Object target, short s) {
      try {
        this.field.setShort(target, s);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }

    @Override
    public void setInt(Object target, int i) {
      try {
        this.field.setInt(target, i);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }

    @Override
    public void setLong(Object target, long l) {
      try {
        this.field.setLong(target, l);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }

    @Override
    public void setFloat(Object target, float f) {
      try {
        this.field.setFloat(target, f);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }

    @Override
    public void setDouble(Object target, double d) {
      try {
        this.field.setDouble(target, d);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }
  }

  static class UnsafeFieldAccessor implements FieldAccessor {
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
}
