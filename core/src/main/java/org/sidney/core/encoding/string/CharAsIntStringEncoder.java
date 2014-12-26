package org.sidney.core.encoding.string;

import org.sidney.core.encoding.AbstractEncoder;
import org.sidney.core.encoding.int32.DeltaBitPackingInt32Encoder;
import org.sidney.core.encoding.int32.Int32Encoder;
import org.sidney.core.field.FieldAccessor;
import org.sidney.core.field.FieldUtils;
import org.sidney.core.field.UnsafeFieldAccessor;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;

//Special sort of encoder that is efficient on text with characters that have high unicode values, since it delta / bitpacks the values
public class CharAsIntStringEncoder extends AbstractEncoder implements StringEncoder {
    private FieldAccessor charArrayField;
    private Int32Encoder lengthEncoder = new DeltaBitPackingInt32Encoder();
    private Int32Encoder charEncoder = new DeltaBitPackingInt32Encoder();

    public CharAsIntStringEncoder() {
        for (Field field : FieldUtils.getAllFieldsNoPrimitiveFilter(String.class)) {
            if (field.getName().equals("value")) {
                charArrayField = new UnsafeFieldAccessor(field);
                break;
            }
        }
    }

    public void writeString(String s) {
        char[] arr = (char[]) charArrayField.get(s);
        lengthEncoder.writeInt(s.length());
        for (char c : arr) {
            charEncoder.writeInt(c);
        }
    }

    @Override
    public void writeStrings(String[] s) {
        for (String str : s) {
            writeString(str);
        }
    }

    @Override
    public String supportedEncoding() {
        return null;
    }

    @Override
    public void reset() {
        lengthEncoder.reset();
        charEncoder.reset();
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        lengthEncoder.writeToStream(outputStream);
        charEncoder.writeToStream(outputStream);
    }
}
