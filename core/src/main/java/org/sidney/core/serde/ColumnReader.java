package org.sidney.core.serde;

import org.sidney.core.ColumnOperations;
import org.sidney.core.column.ColumnIO;
import org.sidney.core.encoding.Decoder;

import java.io.IOException;
import java.io.InputStream;

public class ColumnReader extends ColumnOperations {
    public ColumnReader(TypeHandler typeHandler) {
        super(typeHandler);
    }

    public boolean readBool(int index) {
        return columnIOs.get(index).readBoolean();
    }

    public int readInt(int index) {
        return columnIOs.get(index).readInt();
    }

    public long readLong(int index) {
        return columnIOs.get(index).readLong();
    }

    public float readFloat(int index) {
        return columnIOs.get(index).readFloat();
    }

    public double readDouble(int index) {
        return columnIOs.get(index).readDouble();
    }

    public String readString(int index) {
        return columnIOs.get(index).readString();
    }

    public byte[] readBytes(int index) {
        return columnIOs.get(index).readBytes();
    }

    public boolean readNullMarker(int index) {
        return columnIOs.get(index).readNullMarker();
    }

    public int readRepetitionCount(int index) {
        return columnIOs.get(index).readRepetitionCount();
    }

    public Class readConcreteType(int index, ReadContext context) {
        return columnIOs.get(index).readConcreteType(context);
    }

    public void loadFromInputStream(InputStream inputStream) throws IOException {
        definitionDecoder.populateBufferFromStream(inputStream);
        repetitionDecoder.populateBufferFromStream(inputStream);

        for (ColumnIO columnIO : columnIOs) {
            if (columnIO.getEncoders() != null) {
                for (Decoder decoder : columnIO.getDecoders()) {
                    decoder.populateBufferFromStream(inputStream);
                }
            }
        }
    }
}