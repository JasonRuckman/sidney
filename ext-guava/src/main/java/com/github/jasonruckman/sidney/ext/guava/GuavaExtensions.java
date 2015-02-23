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
package com.github.jasonruckman.sidney.ext.guava;

import com.github.jasonruckman.sidney.core.AbstractSid;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.UnsignedInteger;
import com.google.common.primitives.UnsignedLong;

public class GuavaExtensions {
  public static <T extends AbstractSid> T add(T sid) {
    sid.addSerializer(ImmutableList.class, ImmutableListSerializer.class);
    sid.addSerializer(UnsignedInteger.class, Unsigned.UnsignedIntegerSerializer.class);
    sid.addSerializer(UnsignedLong.class, Unsigned.UnsignedLongSerializer.class);
    MultimapSerializer.registerMaps(sid);
    return sid;
  }
}
