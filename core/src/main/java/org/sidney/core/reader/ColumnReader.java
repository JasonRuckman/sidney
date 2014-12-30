package org.sidney.core.reader;

import org.sidney.core.AbstractColumnOperations;
import org.sidney.core.column.ColumnIO;
import org.sidney.core.serializer.Serializer;

import java.io.IOException;
import java.io.InputStream;

public class ColumnReader extends AbstractColumnOperations {
    public ColumnReader(Serializer serializer) {
        super(serializer);
    }

    public int readRepetitionCount() {
        return repetitionDecoder.nextInt();
    }

    public boolean readNullMarker(int index) {
        return columnIOs.get(index).readNullMarker();
    }

    public boolean nextBoolean(int index) {
        return columnIOs.get(index).readBoolean();
    }

    public int nextInt(int index) {
        return columnIOs.get(index).readInt();
    }

    public long nextLong(int index) {
        return columnIOs.get(index).readLong();
    }

    public float nextFloat(int index) {
        return columnIOs.get(index).readFloat();
    }

    public double nextDouble(int index) {
        return columnIOs.get(index).readDouble();
    }

    public String nextString(int index) {
        return columnIOs.get(index).readString();
    }

    public byte[] nextBytes(int index) {
        return columnIOs.get(index).readBytes();
    }

    public void readFromInputStream(InputStream inputStream) throws IOException {
        definitionDecoder.populateBufferFromStream(inputStream);
        repetitionDecoder.populateBufferFromStream(inputStream);

        for (ColumnIO columnIO : columnIOs) {
            if (columnIO.getEncoder() != null) {
                columnIO.getDecoder().populateBufferFromStream(inputStream);
            }
        }
    }
}