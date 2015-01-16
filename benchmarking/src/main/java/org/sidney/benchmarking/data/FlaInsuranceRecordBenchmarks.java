/**
 * Copyright 2014 Jason Ruckman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sidney.benchmarking.data;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvFormat;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.openjdk.jmh.annotations.*;
import org.sidney.core.Sid;

import java.io.*;
import java.util.List;
import java.util.zip.GZIPOutputStream;

@State(Scope.Benchmark)
@Warmup(iterations = 10)
@Measurement(iterations = 2)
@Threads(1)
@Fork(1)
public class FlaInsuranceRecordBenchmarks {
    private List<FlaInsuranceRecord> records;
    private Sid sid = new Sid();
    private Kryo kryo = new Kryo();
    private ObjectMapper objectMapper = new ObjectMapper();

    public FlaInsuranceRecordBenchmarks() {
        BeanListProcessor<FlaInsuranceRecord> processor = new BeanListProcessor<>(FlaInsuranceRecord.class);

        CsvParserSettings parserSettings = new CsvParserSettings();
        parserSettings.setRowProcessor(processor);
        parserSettings.setHeaderExtractionEnabled(true);
        CsvFormat format = new CsvFormat();
        format.setDelimiter(',');
        format.setLineSeparator("\n");
        parserSettings.setFormat(format);
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("FL_insurance_sample.csv");
        CsvParser parser = new CsvParser(parserSettings);
        parser.parse(new InputStreamReader(inputStream));
        records = processor.getBeans();
    }

    @Benchmark
    @Group("data")
    public byte[] sidney() throws IOException {
        return writeSidney();
    }

    private byte[] writeSidney() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStream os = new GZIPOutputStream(new BufferedOutputStream(baos));
        org.sidney.core.Writer<FlaInsuranceRecord> recordSerializer = sid.newCachedWriter(FlaInsuranceRecord.class, os);
        for (FlaInsuranceRecord record : records) {
            recordSerializer.write(record);
        }
        recordSerializer.close();
        os.close();

        return baos.toByteArray();
    }

    @Benchmark
    @Group("data")
    public byte[] kryo() throws IOException {
        ByteArrayOutputStream kryoBaos = new ByteArrayOutputStream();
        OutputStream kryoGzos = new GZIPOutputStream(new BufferedOutputStream(kryoBaos));
        Output output = new Output(kryoGzos);
        kryo.writeObject(output, records);
        output.close();
        kryoGzos.close();
        return kryoBaos.toByteArray();
    }

    @Benchmark
    @Group("data")
    public byte[] jackson() throws IOException {
        ByteArrayOutputStream kryoBaos = new ByteArrayOutputStream();
        OutputStream kryoGzos = new GZIPOutputStream(new BufferedOutputStream(kryoBaos));
        objectMapper.writeValue(kryoGzos, records);
        kryoGzos.close();
        return kryoBaos.toByteArray();
    }
}
