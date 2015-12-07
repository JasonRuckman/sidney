sidney
======

Sidney is an generic java / scala serializer. 

It's named after my dog Sid and is a side project I did on my own time.  It is very much still a work in progress. Please submit any bugs you have as issues and I'll try and get them taken care of ASAP.

It is heavily influenced by the [Parquet](https://github.com/apache/incubator-parquet-mr) project.  It will decompose your POJOs into their fields and write those as columns. It's generally useful for serializing lots of objects rather than something like [Kryo](https://github.com/EsotericSoftware/kryo) which is much more flexible and efficient on smaller numbers of objects (not to mention being far more battletested).  Untyped maps / lists / arrays are not allowed, as Sidney needs to know types up front so it can generate column writers for leaves.

Sidney works on java beans, maps, arrays and collection types and primitives, as well as enums and other types under (Joda / Guava types). Sidney ignores getters and setters and accesses fields directly, and will ignore transient fields.

Custom serializers are possible by implementing the [Serializer](https://github.com/JasonRuckman/sidney/blob/master/core/src/main/java/com/github/jasonruckman/sidney/core/serde/serializer/Serializer.java) class.

### When would I use it?

Sidney is very new, and hasn't had nearly the amount of work put into it that something like the very excellent [Kryo](https://github.com/EsotericSoftware/kryo) library has and doesn't support nearly the wide variety of data types, however for certain data shapes, Sidney can make your data friendlier to a compressor and can reduce data size and improve speed.

### Algorithm Description

Sidney follows some of the same conventions that Parquet does, there are definition and repetition columns, however there's slight differences. 

Sidney will descend depth first into your object tree, marking null / not null in a compressed bitmap (unless its a primitive leaf, which are non-nullable).  Each column has its own bitmap.

Repetition counts are encoded back to back and bitpacked into a single column.  When the reader starts reading entities, it follows the same path as the writer, reading null markers and repetition counts when necessary.

For interface types the actual concrete type is encoded as its own int column, it is RLE encoded and a map of the class name to that types int value is encoded into the page header.

Given an example bean:
```
public class VeryImportantData {
  @Encode(Encoding.RLE)
  private int first;
  @Encode(Encoding.GROUPVARINT)
  private long second;
}

public class MoreImportantData {
  private String description;
  private Map<String, VeryImportantData> map = new HashMap<>();
}
```

Encoding MoreImportantData Will roughly look like this in the byte stream: 

[Page_Header]

[MoreImportantData:description]

[HashMap.class token][map.Key : String][VeryImportantData:first][VeryImportantData:second]

### Sidney Leaf Types: 

| Java Type       | Nullable           | Supported Encodings  
| --------------- |:------------------:| --------------------:
| boolean.class   | NO                 | PLAIN / BITPACKED / BITMAP
| int.class       | NO                 | PLAIN / BITPACKED / DELTABITPACKEDHYBRID / RLE
| long.class      | NO                 | PLAIN / GROUPVARINT / RLE
| float.class     | NO                 | PLAIN / RLE
| double.class    | NO                 | PLAIN / RLE
| byte[].class    | YES                | PLAIN
| String.class    | YES                | PLAIN / DELTALENGTH / RLE

All of (byte.class, short.class, char.class, enums) are encoded as ints, so int encodings apply to them.

For the reference implementations of the primitive types, the same encodings apply, but they are nullable.

### Bean Example

```
  public class Foo {
    @Encode(Encoding.RLE)
    private int first;
    @Encode(Encoding.BITPACKED)
    private int second;
    
    public Foo(int first, int second) {
      this.first = first;
      this.second = second;
    }
  }
```

And here's how you would write and read it to Sidney: 
```
  Foo foo = new Foo(1, 1.0f);
  ByteArrayOutputStream baos = new ByteArrayOutputStream();
  TypeToken<Foo> fooToken = new TypeToken<Foo>(){};
  Sid sid = new Sid();
  
  Writer<Foo> writer = sid.newWriter(fooToken);
  writer.open(baos);
  writer.write(one);
  writer.close();
  
  ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
  Reader<Foo> reader = sid.newReader(fooToken);
  reader.open(bais);
  Foo out = (reader.hasNext()) ? reader.read() : null;
  reader.close();
```

Closing the writer flushes to the underlying stream.  However if you are writing many objects, Sidney will write them out in pages of 1024 (configurable) flushing them as needed and the .close() will flush the last page.

### Parameterized Type Example

Sidney supports generics, and will resolve them all the way down the object hierarchy.

```
  Map<Integer, Integer> map = new HashMap<>();
  map.put(1, 1);
  ByteArrayOutputStream baos = new ByteArrayOutputStream();
  TypeToken<Map<Integer, Integer>> mapToken = new TypeToken<Map<Integer, Integer>>(){};
  JavaSid sid = new JavaSid();
  
  Writer<Map<Integer, Integer>> writer = sid.newWriter(mapToken);
  writer.open(baos);
  writer.write(map);
  writer.close();
  
  ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
  Reader<Map<Integer, Integer>> reader = sid.newReader(mapToken);
  reader.open(bais);
  Map<Integer, Integer> out = (reader.hasNext()) ? reader.read() : null;
  reader.close();
```

##Scala Examples: 

```
  val sid = new ScalaSid
  val map = new util.HashMap[Int, Int]()
  val baos: ByteArrayOutputStream = new ByteArrayOutputStream
  val writer = sid.newWriter[util.Map[Int, Int]]()
  
  writer.open(baos)
  writer.write(map)
  writer.close
  
  val bais: ByteArrayInputStream = new ByteArrayInputStream(baos.toByteArray)
  val reader = sid.newReader[util.Map[Int, Int]]
  reader.open(bais)
  val results = reader.readAll()
```

Note: No type tokens are necessary, as under the covers typetags are used to decompose the generic argument.

Sidney as of 0.1.2 supports references, so an object getting serialized multiple types will be serialized with a reference and reconstructed on load. 

Implementing a custom serializer: 

```
public class BitSetSerializer extends Serializer<BitSet> {
  private Primitives.BooleanSerializer bitsSerializer;

  @Override
  public void consume(TypeRef typeRef, SerializerContext context) {
    bitsSerializer = context.boolSerializer();
  }

  @Override
  public void writeValue(BitSet value, WriteContext context) {
    int length = value.length();
    context.getMeta().writeRepetitionCount(length);
    for(int i = 0; i < length; i++) {
      bitsSerializer.writeBoolean(value.get(i), context);
    }
  }

  @Override
  public BitSet readValue(ReadContext context) {
    BitSet bitSet = new BitSet();
    int length = context.getMeta().readRepetitionCount();
    for(int i = 0; i < length; i++) {
      if(bitsSerializer.readBoolean(context)) {
        bitSet.set(i);
      }
    }
    return bitSet;
  }
}
```

The consume function is the entry point into the serializer, use it to construct any sub serializers you need.  Make sure you ask for them from the context, or else things won't work (under the hood its tracking column ordinals and binding them to serializers). 

No null checks are necessary, they are handled by the framework, your serializer will only be called if the value is not null (both on read and on write). 

## Other supported types

[BitSet](http://docs.oracle.com/javase/7/docs/api/java/util/BitSet.html)
[UUID](http://docs.oracle.com/javase/7/docs/api/java/util/UUID.html)
[Date](https://docs.oracle.com/javase/6/docs/api/java/util/Date.html)

Are available in the standard package.

## Extensions

[JodaTime](http://joda-time.sourceforge.net/apidocs/org/joda/time/DateTime.html) is available in the ext-common package.

### Guava

Support for guava [UnsignedInteger](http://docs.guava-libraries.googlecode.com/git/javadoc/com/google/common/primitives/UnsignedInteger.html) and [UnsignedLong](http://docs.guava-libraries.googlecode.com/git/javadoc/com/google/common/primitives/UnsignedLong.html) is available in the ext-guava package. 
Support for [ImmutableList](http://docs.guava-libraries.googlecode.com/git/javadoc/com/google/common/collect/ImmutableList.html) and [Multimaps](http://docs.guava-libraries.googlecode.com/git/javadoc/com/google/common/collect/Multimap.html) are also available.  Immutable multimaps are not yet supported.

### Common

## Notes

1. Circular Types / Data: Sidney doesn't resolve circular references either in the data, or in the type structure and will most likely get into an infinite loop and stackoverflow.
2. Erasure: If you cast your tokens to objects or something else, or nest them in generic types, things won't work, the type information needs to be known up front to work. The exception is in the scala api, where the typetags will be propagated. 
3. ClassLoading: There's an outstanding issue to allow classloaders to be passed in, but nothing at present.

## Versions: 

### 0.1.2: 

[Enum Support](https://github.com/JasonRuckman/sidney/issues/7)

[Object References](https://github.com/JasonRuckman/sidney/issues/10)

### 0.2.0

[Guava Support](https://github.com/JasonRuckman/sidney/issues/16)

[Jdk Classes Support](https://github.com/JasonRuckman/sidney/issues/17)

[Serializer API](https://github.com/JasonRuckman/sidney/issues/18)

### 0.2.1

[Benchmarks / Data Generator API](https://github.com/JasonRuckman/sidney/issues/23)

## Maven

Sidney is available on Maven Central.

Core: 
```
<dependency>
  <groupId>com.github.jasonruckman</groupId>
  <artifactId>sidney-core</artifactId>
  <version>0.2.1</version>
</dependency>
```
Scala: 
```
<dependency>
  <groupId>com.github.jasonruckman</groupId>
  <artifactId>sidney-scala</artifactId>
  <version>0.2.1</version>
</dependency>
```
Extensions:
```
<dependency>
  <groupId>com.github.jasonruckman</groupId>
  <artifactId>sidney-ext-common</artifactId>
  <version>0.2.1</version>
</dependency>
```
Guava: 
```
<dependency>
  <groupId>com.github.jasonruckman</groupId>
  <artifactId>sidney-ext-guava</artifactId>
  <version>0.2.1</version>
</dependency>
```
