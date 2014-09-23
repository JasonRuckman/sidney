package org.sidney.encoding.int32;

import org.sidney.encoding.Encoder;

public interface EnumEncoder extends Encoder {
    <T extends Enum> void writeEnum(T value);

    <T extends Enum> void writeEnums(T[] value);
}