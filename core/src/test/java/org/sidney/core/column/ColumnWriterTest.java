package org.sidney.core.column;

import org.junit.Assert;
import org.junit.Test;
import org.sidney.core.writer.ColumnWriter;
import org.sidney.core.TestClass;
import org.sidney.core.reader.ColumnReader;
import org.sidney.core.serializer.SerializerFactory;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ColumnWriterTest {
    @Test
    public void testSimple() throws IOException {
        ColumnWriter consumer = new ColumnWriter(
                SerializerFactory.serializer(TestClass.class)
        );

        ColumnReader columnReader = new ColumnReader(SerializerFactory.serializer(TestClass.class));

        consumer.writeInt(1, 1);
        consumer.writeLong(2, 1L);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        consumer.flushToOutputStream(baos);
        consumer.prepare();

        columnReader.readFromInputStream(new BufferedInputStream(new ByteArrayInputStream(baos.toByteArray())));

        int value = columnReader.nextInt(1);
        long longValue = columnReader.nextLong(2);

        Assert.assertEquals(1, value);
        Assert.assertEquals(1L, longValue);
    }
}
