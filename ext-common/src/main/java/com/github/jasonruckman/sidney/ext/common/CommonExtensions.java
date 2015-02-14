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
package com.github.jasonruckman.sidney.ext.common;

import com.github.jasonruckman.sidney.core.BaseSid;
import org.joda.time.Chronology;
import org.joda.time.DateTime;

public class CommonExtensions {
  public static <T extends BaseSid> T add(T sid) {
    sid.addSerializer(Chronology.class, ChronologySerializer.class);
    sid.addSerializer(DateTime.class, JodaDateTimeSerializer.class);
    return sid;
  }
}
