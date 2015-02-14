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
package com.github.jasonruckman.sidney.core;

import com.github.jasonruckman.sidney.core.serde.*;
import com.github.jasonruckman.sidney.core.serde.serializer.Serializer;

public class BaseSid {
  private SidneyConf conf = SidneyConf.newConf();

  /**
   * Creates a new writer for writing primitive bools
   */
  public BaseWriter.BoolWriter newBoolWriter() {
    return new BaseWriter.BoolWriter(conf);
  }

  /**
   * Creates a new reader for reading primitive bools
   */
  public BaseReader.BoolReader newBoolReader() {
    return new BaseReader.BoolReader(conf);
  }

  /**
   * Creates a new writer for writing primitive bytes
   */
  public BaseWriter.ByteWriter newByteWriter() {
    return new BaseWriter.ByteWriter(conf);
  }

  /**
   * Creates a new reader for reading primitive bytes
   */
  public BaseReader.ByteReader newByteReader() {
    return new BaseReader.ByteReader(conf);
  }

  /**
   * Creates a new writer for writing primitive chars
   */
  public BaseWriter.CharWriter newCharWriter() {
    return new BaseWriter.CharWriter(conf);
  }

  /**
   * Creates a new reader for reading primitive chars
   */
  public BaseReader.CharReader newCharReader() {
    return new BaseReader.CharReader(conf);
  }

  /**
   * Creates a new writer for writing primitive shorts
   */
  public BaseWriter.ShortWriter newShortWriter() {
    return new BaseWriter.ShortWriter(conf);
  }

  /**
   * Creates a new reader for reading primitive shorts
   */
  public BaseReader.ShortReader newShortReader() {
    return new BaseReader.ShortReader(getConf());
  }

  /**
   * Creates a new writer for writing primitive ints
   */
  public BaseWriter.IntWriter newIntWriter() {
    return new BaseWriter.IntWriter(getConf());
  }

  /**
   * Creates a new reader for reading primitive ints
   */
  public BaseReader.IntReader newIntReader() {
    return new BaseReader.IntReader(getConf());
  }

  /**
   * Creates a new writer for writing primitive longs
   */
  public BaseWriter.LongWriter newLongWriter() {
    return new BaseWriter.LongWriter(getConf());
  }

  /**
   * Creates a new reader for reading primitive longs
   */
  public BaseReader.LongReader newLongReader() {
    return new BaseReader.LongReader(getConf());
  }

  /**
   * Creates a new writer for writing primitive floats
   */
  public BaseWriter.FloatWriter newFloatWriter() {
    return new BaseWriter.FloatWriter(getConf());
  }

  /**
   * Creates a new reader for reading primitive floats
   */
  public BaseReader.FloatReader newFloatReader() {
    return new BaseReader.FloatReader(getConf());
  }

  /**
   * Creates a new writer for writing primitive doubles
   */
  public BaseWriter.DoubleWriter newDoubleWriter() {
    return new BaseWriter.DoubleWriter(getConf());
  }

  /**
   * Creates a new reader for reading primitive doubles
   */
  public BaseReader.DoubleReader newDoubleReader() {
    return new BaseReader.DoubleReader(getConf());
  }

  /**
   * Set whether or not to use unsafe field access
   */
  public void useUnsafeFieldAccess(boolean useUnsafe) {
    this.conf.useUnsafe(useUnsafe);
  }

  /**
   * Set whether or not Sidney will track references and relink them on deserialization.  Carries a performance penalty. Off by default
   * @param enabled whether or not to track references
   */
  public void setReferences(boolean enabled) {
    conf.setReferenceTrackingEnabled(enabled);
  }

  /**
   * Register a custom serializer.  Note order of added serializers matters, and the first serializer added that matches a type will be used
   */
  public <T, R extends Serializer<T>> void addSerializer(Class<T> type, Class<R> serializerType) {
    getConf().getRegistrations().register(
        type, serializerType
    );
  }

  /**
   * Register a instance factory.  Only one instance factory per type is allowed, any others will be overwritten.
   */
  public <T, R extends InstanceFactory<T>> void addInstanceFactory(Class<T> type, R instanceFactory) {
    getConf().getRegistrations().registerFactory(type, instanceFactory);
  }

  protected <T> Writer<T> createWriter(TypeToken<T> token) {
    return new JavaWriter<>(getConf(), token);
  }

  protected <T> JavaReader<T> createReader(TypeToken<T> token) {
    return new JavaReader<>(getConf(), token);
  }

  protected SidneyConf getConf() {
    return conf;
  }
}