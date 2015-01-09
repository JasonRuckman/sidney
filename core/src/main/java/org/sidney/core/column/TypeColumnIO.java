package org.sidney.core.column;

import org.sidney.core.encoding.Decoder;
import org.sidney.core.encoding.Encoder;
import org.sidney.core.encoding.int32.Int32Decoder;
import org.sidney.core.encoding.int32.Int32Encoder;
import org.sidney.core.serde.ReadContext;
import org.sidney.core.serde.WriteContext;

import java.util.Arrays;
import java.util.List;

public class TypeColumnIO extends ColumnIO {
    private Int32Encoder concreteTypeEncoder;
    private Int32Decoder concreteTypeDecoder;

    public TypeColumnIO(Int32Encoder concreteTypeEncoder, Int32Decoder concreteTypeDecoder) {
        this.concreteTypeEncoder = concreteTypeEncoder;
        this.concreteTypeDecoder = concreteTypeDecoder;
    }

    @Override
    public void writeConcreteType(Class<?> type, WriteContext context) {
        concreteTypeEncoder.writeInt(context.getHeader().valueForType(type));
    }

    @Override
    public Class readConcreteType(ReadContext context) {
        return null;
    }

    @Override
    public List<Encoder> getEncoders() {
        return Arrays.asList((Encoder) concreteTypeEncoder);
    }

    @Override
    public List<Decoder> getDecoders() {
        return Arrays.asList((Decoder) concreteTypeDecoder);
    }
}