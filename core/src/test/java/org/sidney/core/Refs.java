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

public class Refs {
    private Boolean first;
    private Integer second;
    private Long third;
    private Float fourth;
    private Double fifth;

    public Refs() {

    }

    public Refs(Boolean first, Integer second, Long third, Float fourth, Double fifth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
        this.fifth = fifth;
    }

    public Boolean getFirst() {
        return first;
    }

    public void setFirst(Boolean first) {
        this.first = first;
    }

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }

    public Long getThird() {
        return third;
    }

    public void setThird(Long third) {
        this.third = third;
    }

    public Float getFourth() {
        return fourth;
    }

    public void setFourth(Float fourth) {
        this.fourth = fourth;
    }

    public Double getFifth() {
        return fifth;
    }

    public void setFifth(Double fifth) {
        this.fifth = fifth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Refs that = (Refs) o;

        if (fifth != null ? !fifth.equals(that.fifth) : that.fifth != null) return false;
        if (first != null ? !first.equals(that.first) : that.first != null) return false;
        if (fourth != null ? !fourth.equals(that.fourth) : that.fourth != null) return false;
        if (second != null ? !second.equals(that.second) : that.second != null) return false;
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
        return result;
    }
}
