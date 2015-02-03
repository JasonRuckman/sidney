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
package com.github.jasonruckman.sidney.core;

public class AllPrimitiveRefs {
    private Boolean first;
    private Byte second;
    private Short third;
    private Character fourth;
    private Integer fifth;
    private Long sixth;
    private Float seventh;
    private Double eighth;

    public AllPrimitiveRefs() {

    }

    public AllPrimitiveRefs(Boolean first, Byte second, Short third, Character fourth, Integer fifth, Long sixth, Float seventh, Double eighth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
        this.fifth = fifth;
        this.sixth = sixth;
        this.seventh = seventh;
        this.eighth = eighth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AllPrimitiveRefs that = (AllPrimitiveRefs) o;

        if (eighth != null ? !eighth.equals(that.eighth) : that.eighth != null) return false;
        if (fifth != null ? !fifth.equals(that.fifth) : that.fifth != null) return false;
        if (first != null ? !first.equals(that.first) : that.first != null) return false;
        if (fourth != null ? !fourth.equals(that.fourth) : that.fourth != null) return false;
        if (second != null ? !second.equals(that.second) : that.second != null) return false;
        if (seventh != null ? !seventh.equals(that.seventh) : that.seventh != null) return false;
        if (sixth != null ? !sixth.equals(that.sixth) : that.sixth != null) return false;
        if (third != null ? !third.equals(that.third) : that.third != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        result = 31 * result + (second != null ? second.hashCode() : 0);
        result = 31 * result + (third != null ? third.hashCode() : 0);
        result = 31 * result + (fourth != null ? fourth.hashCode() : 0);
        result = 31 * result + (fifth != null ? fifth.hashCode() : 0);
        result = 31 * result + (sixth != null ? sixth.hashCode() : 0);
        result = 31 * result + (seventh != null ? seventh.hashCode() : 0);
        result = 31 * result + (eighth != null ? eighth.hashCode() : 0);
        return result;
    }
}