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
package com.github.jasonruckman.gzip;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jasonruckman.sidney.core.AbstractSid;
import com.github.jasonruckman.sidney.core.Configuration;
import com.github.jasonruckman.sidney.core.JavaSid;
import com.github.jasonruckman.sidney.core.serde.Reader;
import com.github.jasonruckman.sidney.core.serde.Writer;
import com.github.jasonruckman.sidney.core.type.TypeToken;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@State(Scope.Benchmark)
public abstract class AbstractBenchmark<T> {
  private final JavaSid javaSid;
  private final JavaSid unsafeSid;
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
      return new Kryo();
    }
  };
  private final ThreadLocal<Writer<T>> safeWriter = new ThreadLocal<Writer<T>>() {
    @Override
    protected Writer<T> initialValue() {
      return javaSid.newWriter(token);
    }
  };
  private final ThreadLocal<Reader<T>> safeReader = new ThreadLocal<Reader<T>>() {
    @Override
    protected Reader<T> initialValue() {
      return javaSid.newReader(token);
    }
  };
  private final ThreadLocal<Writer<T>> unsafeWriter = new ThreadLocal<Writer<T>>() {
    @Override
    protected Writer<T> initialValue() {
      return unsafeSid.newWriter(token);
    }
  };
  private final ThreadLocal<Reader<T>> unsafeReader = new ThreadLocal<Reader<T>>() {
    @Override
    protected Reader<T> initialValue() {
      return unsafeSid.newReader(token);
    }
  };
  private final ThreadLocal<Output> outputThreadLocal = new ThreadLocal<Output>() {
    @Override
    protected Output initialValue() {
      return new Output(8192);
    }
  };

  private byte[] serializedSidneyData;
  private byte[] serializedJacksonData;
  private byte[] serializedKryoData;
  private byte[] unsafeSidneyData;

  private List<T> sampleData = sampleData();

  public AbstractBenchmark(TypeToken<T> token) {
    this.token = token;
    this.unsafeSid = new JavaSid();
    this.unsafeSid.setIOType(Configuration.IOType.Unsafe);
    this.javaSid = new JavaSid();
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

  public AbstractSid getSid() {
    return javaSid;
  }

  @Setup
  public void setup() throws IOException {
    serializedSidneyData = initializeSidney(javaSid);
    unsafeSidneyData = initializeSidney(unsafeSid);
    initializeJackson();
    initializeKryo();
  }

  public abstract List<T> sampleData();

  public List<T> readSidney() {
    throw new IllegalStateException();
  }

  public List<T> unsafeReadSidney() {
    throw new IllegalStateException();
  }

  public List<T> readJackson() {
    throw new IllegalStateException();
  }

  public List<T> readKryo() {
    throw new IllegalStateException();
  }

  public byte[] writeSidney() {
    throw new IllegalStateException();
  }

  public byte[] writeUnsafeSidney() {
    throw new IllegalStateException();
  }

  public byte[] writeJackson() {
    throw new IllegalStateException();
  }

  public byte[] writeKryo() {
    throw new IllegalStateException();
  }

  protected List<T> doReadSidney() {
    return doReadSidney(getSerializedSidneyData());
  }

  protected List<T> doReadSidney(byte[] arr) {
    Reader<T> reader = reader();
    try {
      reader.open(new GZIPInputStream(new ByteArrayInputStream(arr)));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    List<T> list = reader.readAll();
    reader.close();
    return list;
  }

  protected List<T> doUnsafeReadSidney() {
    Reader<T> reader = unsafeReader.get();
    try {
      reader.open(new GZIPInputStream(new ByteArrayInputStream(unsafeSidneyData)));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    List<T> list = reader.readAll();
    reader.close();
    return list;
  }

  protected List<T> doReadJackson() {
    try {
      return mapper().readValue(new GZIPInputStream(new ByteArrayInputStream(getSerializedJacksonData())), List.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  protected List<T> doReadKryo() {
    return doReadKryo(getSerializedKryoData());
  }

  protected List<T> doReadKryo(byte[] arr) {
    Input input = null;
    try {
      input = new Input(new GZIPInputStream(new ByteArrayInputStream(arr)));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return kryo().readObject(input, ArrayList.class);
  }

  protected byte[] doWriteSidney() {
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      GZIPOutputStream gzos = new GZIPOutputStream(baos);
      Writer<T> writer = safeWriter.get();
      writer.open(gzos);
      for (T t : sampleData) {
        writer.write(t);
      }
      writer.close();
      gzos.close();
      return baos.toByteArray();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  protected byte[] doUnsafeWriteSidney() {
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      GZIPOutputStream gzos = new GZIPOutputStream(baos);
      Writer<T> writer = unsafeWriter.get();
      writer.open(gzos);
      for (T t : sampleData) {
        writer.write(t);
      }
      writer.close();
      gzos.close();
      return baos.toByteArray();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  protected byte[] doWriteJackson() {
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      GZIPOutputStream gzos = new GZIPOutputStream(baos);
      mapper().writeValue(gzos, sampleData);
      gzos.close();
      return baos.toByteArray();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  protected byte[] doWriteKryo() {
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      GZIPOutputStream gzos = new GZIPOutputStream(baos);
      Output output = output();
      output.setOutputStream(gzos);
      kryo().writeObject(output, sampleData);
      output.close();
      gzos.close();
      return baos.toByteArray();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  protected Reader<T> reader() {
    return safeReader.get();
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

  private byte[] initializeSidney(JavaSid sid) {
    Writer<T> writer = sid.newWriter(token);

    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      GZIPOutputStream gzos = new GZIPOutputStream(baos);
      writer.open(gzos);
      for (T t : sampleData) {
        writer.write(t);
      }
      writer.close();
      gzos.close();
      return baos.toByteArray();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void initializeJackson() {
    try {
      ObjectMapper mapper = new ObjectMapper();
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      GZIPOutputStream gzos = new GZIPOutputStream(baos);
      mapper.writeValue(gzos, sampleData);
      gzos.close();
      serializedJacksonData = baos.toByteArray();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void initializeKryo() {
    try {
      Kryo kryo = new Kryo();
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      GZIPOutputStream gzos = new GZIPOutputStream(outputStream);
      Output o = output();
      o.setOutputStream(gzos);
      kryo.writeObject(o, sampleData);
      o.close();
      gzos.close();
      serializedKryoData = outputStream.toByteArray();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}