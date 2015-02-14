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

public class ReadMetaImpl implements ReadContext.Meta {
  private final ReadContextImpl parent;

  public ReadMetaImpl(ReadContextImpl parent) {
    this.parent = parent;
  }

  @Override
  public Class<?> readConcreteType() {
    return parent.column(getPosition()).readConcreteType(parent);
  }

  @Override
  public int readRepetitionCount() {
    return parent.column(getPosition()).readRepetitionCount();
  }

  private int getPosition() {
    return parent.getColumnIndex();
  }
}
