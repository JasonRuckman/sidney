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
package com.github.jasonruckman;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jasonruckman.sidney.core.BaseSid;
import com.github.jasonruckman.sidney.core.JavaSid;
import com.github.jasonruckman.sidney.core.TypeToken;
import com.github.jasonruckman.sidney.core.serde.Reader;
import com.github.jasonruckman.sidney.core.serde.Writer;
import org.apache.commons.io.IOUtils;
import org.openjdk.jmh.annotations.Setup;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public abstract class AbstractBenchmark<T> {
  private final JavaSid javaSid = new JavaSid();
  private final TypeToken<T> token;
  private final ThreadLocal<ObjectMapper> threadLocalJackson = new ThreadLocal<ObjectMapper>() {
    @Override
    protected ObjectMapper initialValue() {
      return new ObjectMapper();
    }
  };
  private final ThreadLocal<Kryo> threadLocalKryo = new ThreadLocal<Kryo>() {
    @Override
    protected Kryo initialValue() {
      Kryo kryo = new Kryo();

      return kryo;
    }
  };
  private final ThreadLocal<Writer<T>> threadLocalSidneyWriter = new ThreadLocal<Writer<T>>() {
    @Override
    protected Writer<T> initialValue() {
      return javaSid.newWriter(token);
    }
  };
  private final ThreadLocal<Reader<T>> threadLocalSidneyReader = new ThreadLocal<Reader<T>>() {
    @Override
    protected Reader<T> initialValue() {
      return javaSid.newReader(token);
    }
  };
  private ThreadLocal<Input> inputThreadLocal = new ThreadLocal<Input>() {
    @Override
    protected Input initialValue() {
      return new Input(8192);
    }
  };
  private ThreadLocal<Output> outputThreadLocal = new ThreadLocal<Output>() {
    @Override
    protected Output initialValue() {
      return new Output(8192);
    }
  };
  private byte[] serializedSidneyData;
  private byte[] serializedJacksonData;
  private byte[] serializedKryoData;
  private byte[] gzippedSidneyData;
  private byte[] gzippedJacksonData;
  private byte[] gzippedKryoData;
  private List<T> sampleData = sampleData();

  public AbstractBenchmark(TypeToken<T> token) {
    this.token = token;
  }

  public byte[] getSerializedSidneyData() {
    return serializedSidneyData;
  }

  public byte[] getSerializedJacksonData() {
    return serializedJacksonData;
  }

  public byte[] getSerializedKryoData() {
    return serializedKryoData;
  }

  public byte[] getGzippedSidneyData() {
    return gzippedSidneyData;
  }

  public byte[] getGzippedJacksonData() {
    return gzippedJacksonData;
  }

  public byte[] getGzippedKryoData() {
    return gzippedKryoData;
  }

  public BaseSid getSid() {
    return javaSid;
  }

  @Setup
  public void setup() throws IOException {
    initializeSidney();
    initializeJackson();
    initializeKryo();

    gzipAll();

    printSizes();
  }

  public abstract List<T> sampleData();

  public abstract List<T> readSidney();

  public abstract List<T> readJackson();

  public abstract List<T> readKryo();

  public abstract byte[] writeSidney();

  public abstract byte[] writeJackson();

  public abstract byte[] writeKryo();

  public abstract List<T> readSidneyGZIP();

  public abstract List<T> readJacksonGZIP();

  public abstract List<T> readKryoGZIP();

  public abstract byte[] writeSidneyGZIP();

  public abstract byte[] writeJacksonGZIP();

  public abstract byte[] writeKryoGZIP();

  protected List<T> doReadSidney() {
    Reader<T> reader = reader();
    reader.open(new ByteArrayInputStream(getSerializedSidneyData()));
    List<T> list = reader.readAll();
    reader.close();
    return list;
  }

  protected List<T> doReadJackson() {
    try {
      return mapper().readValue(getSerializedJacksonData(), List.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  protected List<T> doReadKryo() {
    Input input = new Input(getSerializedKryoData());
    return kryo().readObject(input, ArrayList.class);
  }

  protected List<T> doReadSidneyGZIP() {
    Reader<T> reader = reader();
    try {
      reader.open(new GZIPInputStream(new ByteArrayInputStream(getGzippedSidneyData())));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    List<T> list = reader.readAll();
    reader.close();
    return list;
  }

  protected List<T> doReadJacksonGZIP() {
    try {
      return mapper().readValue(new GZIPInputStream(new ByteArrayInputStream(getGzippedJacksonData())), List.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  protected List<T> doReadKryoGZIP() {
    Input input = inputThreadLocal.get();

    try {
      GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(getGzippedKryoData()));
      input.setInputStream(gis);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return kryo().readObject(input, ArrayList.class);
  }

  protected byte[] doWriteSidney() {
    Output output = output();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    output.setOutputStream(baos);
    Writer<T> writer = threadLocalSidneyWriter.get();
    writer.open(baos);
    for (T t : sampleData) {
      writer.write(t);
    }
    writer.close();
    return baos.toByteArray();
  }

  protected byte[] doWriteJackson() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
      mapper().writeValue(baos, sampleData);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return baos.toByteArray();
  }

  protected byte[] doWriteKryo() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    Output output = output();
    output.setOutputStream(baos);
    kryo().writeObject(output, sampleData);
    output.close();
    return baos.toByteArray();
  }

  protected byte[] doWriteSidneyGZIP() {
    try {
      Output output = output();
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      GZIPOutputStream gos;
      gos = new GZIPOutputStream(baos);
      output.setOutputStream(gos);
      Writer<T> writer = threadLocalSidneyWriter.get();
      writer.open(gos);
      for (T t : sampleData) {
        writer.write(t);
      }
      writer.close();
      gos.close();
      return baos.toByteArray();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  protected byte[] doWriteJacksonGZIP() {
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      GZIPOutputStream gos = new GZIPOutputStream(baos);
      mapper().writeValue(gos, sampleData);
      gos.close();
      return baos.toByteArray();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  protected byte[] doWriteKryoGZIP() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    GZIPOutputStream gos = null;
    try {
      gos = new GZIPOutputStream(baos);
      Output output = output();
      output.clear();
      output.setOutputStream(gos);
      kryo().writeObject(output, sampleData);
      output.close();
      gos.close();
      return baos.toByteArray();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  protected Reader<T> reader() {
    return threadLocalSidneyReader.get();
  }

  protected ObjectMapper mapper() {
    return threadLocalJackson.get();
  }

  protected Kryo kryo() {
    return threadLocalKryo.get();
  }

  protected Output output() {
    return outputThreadLocal.get();
  }

  private void initializeSidney() {
    Writer<T> writer = javaSid.newWriter(token);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    writer.open(baos);
    for (T t : sampleData) {
      writer.write(t);
    }
    writer.close();

    serializedSidneyData = baos.toByteArray();
  }

  private void initializeJackson() {
    ObjectMapper mapper = new ObjectMapper();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
      mapper.writeValue(baos, sampleData);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    serializedJacksonData = baos.toByteArray();
  }

  private void initializeKryo() {
    Kryo kryo = new Kryo();
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    Output o = output();
    o.setOutputStream(outputStream);
    kryo.writeObject(o, sampleData);
    o.close();

    serializedKryoData = outputStream.toByteArray();
  }

  private void printSizes() {
    System.out.println("\n\n" +
            String.format("Sizes for %s", getClass()) + "\n\n" +
            String.format("Serialized Sidney Size %s", serializedSidneyData.length) + "\n" +
            String.format("Serialized Kryo Size %s", serializedKryoData.length) + "\n" +
            String.format("Serialized Jackson Size %s", serializedJacksonData.length + "\n" +
                String.format("GZIP Sidney Size %s", gzippedSidneyData.length) + "\n" +
                String.format("GZIP Kryo Size %s", gzippedKryoData.length) + "\n" +
                String.format("GZIP Jackson Size %s", gzippedJacksonData.length)
                + "\n\n")
    );
  }

  private void gzipAll() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    GZIPOutputStream gzos = new GZIPOutputStream(baos);
    IOUtils.copy(new ByteArrayInputStream(getSerializedSidneyData()), gzos);
    gzos.close();

    gzippedSidneyData = baos.toByteArray();

    baos = new ByteArrayOutputStream();
    gzos = new GZIPOutputStream(baos);
    IOUtils.copy(new ByteArrayInputStream(getSerializedJacksonData()), gzos);
    gzos.close();

    gzippedJacksonData = baos.toByteArray();

    baos = new ByteArrayOutputStream();
    gzos = new GZIPOutputStream(baos);
    IOUtils.copy(new ByteArrayInputStream(getSerializedKryoData()), gzos);
    gzos.close();

    gzippedKryoData = baos.toByteArray();
  }
}