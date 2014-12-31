package org.sidney.core.writer;

import org.sidney.core.AbstractColumnOperations;
import org.sidney.core.Container;
import org.sidney.core.Header;
import org.sidney.core.column.*;
import org.sidney.core.encoding.Decoder;
import org.sidney.core.encoding.Encoder;
import org.sidney.core.serializer.Serializer;

import java.io.IOException;
import java.io.OutputStream;

public class ColumnWriter extends AbstractColumnOperations {
    public ColumnWriter(Serializer serializer, Container<Header> header) {
        super(serializer, header);
    }

    public void writeBoolean(int index, boolean value) {
        ColumnIO columnIO = columnIOs.get(index);
        columnIO.writeBoolean(value);
    }

    public void writeInt(int index, int value) {
        ColumnIO columnIO = columnIOs.get(index);
        columnIO.writeInt(value);
    }

    public void writeLong(int index, long value) {
        ColumnIO columnIO = columnIOs.get(index);
        columnIO.writeLong(value);
    }

    public void writeFloat(int index, float value) {
        ColumnIO columnIO = columnIOs.get(index);
        columnIO.writeNotNull();
        columnIO.writeFloat(value);
    }

    public void writeDouble(int index, double value) {
        ColumnIO columnIO = columnIOs.get(index);
        columnIO.writeDouble(value);
    }

    public void writeBytes(int index, byte[] bytes) {
        ColumnIO columnIO = columnIOs.get(index);
        columnIO.writeBytes(bytes);
    }

    public void writeString(int index, String value) {
        ColumnIO columnIO = columnIOs.get(index);
        columnIO.writeString(value);
    }

    public void writeNotNull(int index) {
        columnIOs.get(index).writeNotNull();
    }

    public void writeNull(int index) {
        columnIOs.get(index).writeNull();
    }

    public void startRepetition(int index) {
        columnIOs.get(index).startRepetition();
    }

    public void endRepetition(int index) {
        columnIOs.get(index).endRepetition();
    }

    public void writeConcreteType(Class<?> type, int index) {
        columnIOs.get(index).writeConcreteType(type);
    }

    public void flushToOutputStream(OutputStream outputStream) throws IOException {
        definitionEncoder.writeToStream(outputStream);
        repetitionEncoder.writeToStream(outputStream);

        for (ColumnIO columnIO : columnIOs) {
            if (columnIO.getEncoders() != null) {
                for(Encoder encoder : columnIO.getEncoders()) {
                    encoder.writeToStream(outputStream);
                }
            }
        }
    }

    public void prepare() {
        definitionEncoder.reset();
        repetitionEncoder.reset();

        for (ColumnIO columnIO : columnIOs) {
            for(Encoder encoder : columnIO.getEncoders()) {
                encoder.reset();
            }
        }
    }
}