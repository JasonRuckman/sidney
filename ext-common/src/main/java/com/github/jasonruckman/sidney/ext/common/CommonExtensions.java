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
