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

public class GenericsContainer<T, R> {
    private NestedArray<T> array;
    private NestedMap<T, R> map;

    public GenericsContainer() {

    }

    public GenericsContainer(NestedArray<T> array, NestedMap<T, R> map) {
        this.array = array;
        this.map = map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GenericsContainer that = (GenericsContainer) o;

        if (array != null ? !array.equals(that.array) : that.array != null) return false;
        if (map != null ? !map.equals(that.map) : that.map != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = array != null ? array.hashCode() : 0;
        result = 31 * result + (map != null ? map.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GenericsContainer{" +
                "array=" + array +
                ", map=" + map +
                '}';
    }
}
