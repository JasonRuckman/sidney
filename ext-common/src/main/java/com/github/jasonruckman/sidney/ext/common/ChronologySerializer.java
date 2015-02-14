package com.github.jasonruckman.sidney.ext.common;

import com.github.jasonruckman.sidney.core.TypeRef;
import com.github.jasonruckman.sidney.core.serde.ReadContext;
import com.github.jasonruckman.sidney.core.serde.WriteContext;
import com.github.jasonruckman.sidney.core.serde.serializer.Serializer;
import com.github.jasonruckman.sidney.core.serde.serializer.SerializerContext;
import com.github.jasonruckman.sidney.core.serde.serializer.jdkserializers.Primitives;
import org.joda.time.Chronology;
import org.joda.time.chrono.*;

public class ChronologySerializer extends Serializer<Chronology> {
  private final Chronologies chronologies = new Chronologies();
  private Primitives.IntSerializer serializer;

  @Override
  public void consume(TypeRef typeRef, SerializerContext context) {
    serializer = context.intSerializer();
  }

  @Override
  public void writeValue(Chronology value, WriteContext context) {
    serializer.writeInt(chronologies.forChronology(value), context);
  }

  @Override
  public Chronology readValue(ReadContext context) {
    return chronologies.forId(serializer.readInt(context));
  }

  private static enum IdentifiableChronology {
    ISO(0, ISOChronology.getInstance()),
    COPTIC(1, CopticChronology.getInstance()),
    ETHIOPIC(2, EthiopicChronology.getInstance()),
    GREGORIAN(3, GregorianChronology.getInstance()),
    JULIAN(4, JulianChronology.getInstance()),
    ISLAMIC(5, IslamicChronology.getInstance()),
    BUDDHIST(6, BuddhistChronology.getInstance()),
    GJ(7, GJChronology.getInstance());

    private final int id;
    private final Chronology chronology;

    IdentifiableChronology(int id, Chronology chronology) {
      this.id = id;
      this.chronology = chronology;
    }

    public int getId() {
      return id;
    }

    public Chronology getChronology() {
      return chronology;
    }
  }

  private static class Chronologies {
    private final Chronology[] chronologies;

    public Chronologies() {
      IdentifiableChronology[] values = IdentifiableChronology.values();
      chronologies = new Chronology[values.length];
      for (int i = 0; i < values.length; i++) {
        chronologies[i] = values[i].getChronology();
      }
    }

    public Chronology forId(int id) {
      return chronologies[id];
    }

    public int forChronology(Chronology chronology) {
      for (int i = 0; i < chronologies.length; i++) {
        if (chronologies[i] == chronology) {
          return i;
        }
      }
      throw new IllegalStateException();
    }
  }
}