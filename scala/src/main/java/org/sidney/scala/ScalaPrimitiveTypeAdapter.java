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
package org.sidney.scala;

import java.util.HashMap;
import java.util.Map;

public class ScalaPrimitiveTypeAdapter {
    private static final Map<String, Types> scalaNamesToJavaClasses = new HashMap<>();

    static {
        scalaNamesToJavaClasses.put(
                scala.Boolean.class.getName(), new Types(boolean.class, Boolean.class)
        );
        scalaNamesToJavaClasses.put(
                scala.Byte.class.getName(), new Types(byte.class, Byte.class)
        );
        scalaNamesToJavaClasses.put(
                scala.Char.class.getName(), new Types(char.class, Character.class)
        );
        scalaNamesToJavaClasses.put(
                scala.Short.class.getName(), new Types(short.class, Short.class)
        );
        scalaNamesToJavaClasses.put(
                scala.Int.class.getName(), new Types(int.class, Integer.class)
        );
        scalaNamesToJavaClasses.put(
                scala.Long.class.getName(), new Types(long.class, Long.class)
        );
        scalaNamesToJavaClasses.put(
                scala.Float.class.getName(), new Types(float.class, Float.class)
        );
        scalaNamesToJavaClasses.put(
                scala.Double.class.getName(), new Types(double.class, Double.class)
        );
    }

    /**
     * Check if this scala type is a java primitive
     */
    public static boolean isJavaPrimitive(Class<?> type) {
        for (Map.Entry<String, Types> entry : scalaNamesToJavaClasses.entrySet()) {
            if (entry.getValue().getPrimitive().equals(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return the java class for the given scala class string.  If this is a type arg, box it to a {@link java.lang.Integer} instead of a primitive
     */
    public static Class getJavaClassForScalaString(String s, boolean isTypeArg) {
        Types t = scalaNamesToJavaClasses.get(s);
        if (t == null) {
            return null;
        }
        if (isTypeArg) {
            return t.getWrapper();
        }
        return t.getPrimitive();
    }

    private static class Types {
        private Class primitive;
        private Class wrapper;

        private Types(Class primitive, Class wrapper) {
            this.primitive = primitive;
            this.wrapper = wrapper;
        }

        public Class getPrimitive() {
            return primitive;
        }

        public Class getWrapper() {
            return wrapper;
        }
    }
}