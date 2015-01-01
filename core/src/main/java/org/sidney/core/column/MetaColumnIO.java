package org.sidney.core.column;

import org.sidney.core.Container;
import org.sidney.core.Header;
import org.sidney.core.encoding.Decoder;
import org.sidney.core.encoding.Encoder;
import org.sidney.core.encoding.int32.Int32Decoder;
import org.sidney.core.encoding.int32.Int32Encoder;

import java.util.Arrays;
import java.util.List;

public class MetaColumnIO extends ColumnIO {
    private Container<Header> header;
    private Int32Encoder concreteTypeEncoder;
    private Int32Decoder concreteTypeDecoder;

    public MetaColumnIO(Container<Header> header, Int32Encoder concreteTypeEncoder, Int32Decoder concreteTypeDecoder) {
        this.header = header;
        this.concreteTypeEncoder = concreteTypeEncoder;
        this.concreteTypeDecoder = concreteTypeDecoder;
    }

    @Override
    public void writeConcreteType(Class<?> type) {
        concreteTypeEncoder.writeInt(header.get().valueForType(type));
    }

    @Override
    public Class readConcreteType() {
        return header.get().readConcreteType(concreteTypeDecoder.nextInt());
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