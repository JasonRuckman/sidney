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
package com.github.jasonruckman.sidney.core.serde.serializer;

import com.github.jasonruckman.sidney.core.Encode;
import com.github.jasonruckman.sidney.core.TypeRef;
import com.github.jasonruckman.sidney.core.io.Encoding;
import com.github.jasonruckman.sidney.core.serde.Type;

public abstract class PrimitiveSerializer<T> extends Serializer<T> {
  private Encoding encoding;

  public abstract Type getType();

  public boolean intercept() {
    return true;
  }

  public Encoding getEncoding() {
    if (encoding != null) {
      return encoding;
    }
    return getType().defaultEncoding();
  }

  public void overrideEncoding(Encoding encoding) {
    this.encoding = encoding;
  }

  @Override
  public void consume(TypeRef typeRef, SerializerContext context) {
    if (typeRef instanceof TypeRef.TypeFieldRef &&
        ((TypeRef.TypeFieldRef) typeRef).getJdkField().getAnnotation(Encode.class) != null) {
      encoding = ((TypeRef.TypeFieldRef) typeRef).getJdkField().getAnnotation(Encode.class).value();
    }
  }
}