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
package com.github.jasonruckman.sidney.benchmarking.data;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jasonruckman.sidney.core.JavaSid;
import com.github.jasonruckman.sidney.core.TypeToken;
import com.github.jasonruckman.sidney.core.serde.Reader;
import com.github.jasonruckman.sidney.core.serde.Writer;
import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvFormat;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.openjdk.jmh.annotations.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@State(Scope.Benchmark)
@Warmup(iterations = 10)
@Measurement(iterations = 2)
@Threads(1)
@Fork(1)
public class FlaInsuranceRecordBenchmarks {
  private List<FlaInsuranceRecord> records;
  private JavaSid sid = new JavaSid();
  private Kryo kryo = new Kryo();
  private ObjectMapper objectMapper = new ObjectMapper();
  private Writer<FlaInsuranceRecord> writer = sid.newWriter(new TypeToken<FlaInsuranceRecord>() {
  });
  private Reader<FlaInsuranceRecord> reader = sid.newReader(new TypeToken<FlaInsuranceRecord>() {
  });

  public FlaInsuranceRecordBenchmarks() {
    sid.useUnsafeFieldAccess(true);

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
  public List<FlaInsuranceRecord> sidney() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    OutputStream os = new GZIPOutputStream(baos);
    writer.open(os);
    for (FlaInsuranceRecord record : records) {
      writer.write(record);
    }
    writer.close();
    os.close();

    List<FlaInsuranceRecord> list = new ArrayList<>();
    reader.open(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(baos.toByteArray()))));

    while (reader.hasNext()) {
      list.add(reader.read());
    }
    return list;
  }

  @Benchmark
  @Group("data")
  public List<FlaInsuranceRecord> kryo() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    OutputStream gzos = new GZIPOutputStream(baos);
    Output output = new Output(gzos);
    kryo.writeObject(output, records);
    output.close();
    gzos.close();

    InputStream inputStream = new BufferedInputStream(
        new GZIPInputStream(
            new ByteArrayInputStream(baos.toByteArray())
        )
    );

    Input input = new Input(inputStream);
    return kryo.readObject(input, ArrayList.class);
  }

  @Benchmark
  @Group("data")
  public List<FlaInsuranceRecord> jackson() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    OutputStream gzos = new GZIPOutputStream(baos);
    objectMapper.writeValue(gzos, records);
    gzos.close();

    InputStream inputStream = new BufferedInputStream(
        new GZIPInputStream(
            new ByteArrayInputStream(baos.toByteArray())
        )
    );

    return objectMapper.readValue(inputStream, ArrayList.class);
  }
}