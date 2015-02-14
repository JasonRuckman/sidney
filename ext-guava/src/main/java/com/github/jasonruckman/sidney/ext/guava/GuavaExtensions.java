package com.github.jasonruckman.sidney.ext.guava;

import com.github.jasonruckman.sidney.core.BaseSid;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.UnsignedInteger;
import com.google.common.primitives.UnsignedLong;

public class GuavaExtensions {
  public static <T extends BaseSid> T add(T sid) {
    sid.addSerializer(ImmutableList.class, ImmutableListSerializer.class);
    sid.addSerializer(UnsignedInteger.class, Unsigned.UnsignedIntegerSerializer.class);
    sid.addSerializer(UnsignedLong.class, Unsigned.UnsignedLongSerializer.class);
    MultimapSerializer.registerMaps(sid);
    return sid;
  }
}
