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

import com.github.jasonruckman.sidney.core.TypeRef;
import com.github.jasonruckman.sidney.core.serde.ReadContext;
import com.github.jasonruckman.sidney.core.serde.WriteContext;
import com.github.jasonruckman.sidney.core.serde.serializer.Serializer;
import com.github.jasonruckman.sidney.core.serde.serializer.SerializerContext;
import com.github.jasonruckman.sidney.core.serde.serializer.jdkserializers.Primitives;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class JodaDateTimeSerializer extends Serializer<DateTime> {
  private Primitives.LongSerializer millis;
  private Serializer<String> tz;
  private Serializer<Chronology> chrono;

  @Override
  public void consume(TypeRef typeRef, SerializerContext context) {
    millis = context.longSerializer(typeRef.field("iMillis"));
    chrono = context.serializer(typeRef.field("iChronology"));
    tz = context.stringSerializer();
  }

  @Override
  public void writeValue(DateTime value, WriteContext context) {
    millis.writeLong(value.getMillis(), context);
    chrono.writeValue(value.getChronology(), context);
    tz.writeValue(value.getZone().getID(), context);
  }

  @Override
  public DateTime readValue(ReadContext context) {
    long m = millis.readLong(context);
    Chronology chronology = chrono.readValue(context);
    String zone = tz.readValue(context);
    return new DateTime(m, chronology.withZone(DateTimeZone.forID(zone)));
  }
}