package org.sidney.encoding.character;

import org.sidney.encoding.Encoder;

public interface CharEncoder extends Encoder {
    void writeChar(char value);

    void writeChars(char[] values);
}