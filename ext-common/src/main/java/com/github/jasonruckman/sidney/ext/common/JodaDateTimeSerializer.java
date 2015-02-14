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