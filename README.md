sidney
======

Sidney is an experimental general java serializer. 

It's named after my dog Sid.

It is heavily influenced by the [Parquet](https://github.com/apache/incubator-parquet-mr) project.  It will decompose your POJOs into their fields and write those as columns. It's generally useful for serializing lots of objects rather than something like Kryo which is more flexible.  Untyped maps / lists / arrays are not allowed, as Sidney needs to know types up front so it can generate column writers for leaves.

Sidney works on java beans, maps, arrays and collection types, there's underlying support to simply write primitives but I haven't exposed it yet via the API, enums are not yet supported (but planned very soon). Generally if [Jackson](https://github.com/FasterXML/jackson-databind/) supports serializing the pojo by default, Sidney will probably be able to (Sidney uses jackson for type resolution).  The one difference is that Sidney ignores getters and setters, it reads and writes fields directly.

### Algorithm Description

Sidney follows some of the same conventions that Parquet does, there are definition and repetition columns, however there's slight differences. 

Sidney will descend depth first into your object tree, and when it encounters nullable fields, it will set a true bit in a compressed bitmap.  As of this instant there's only one bitmap, not a bitmap per column which would improve compression if columns had long runs of null or not null, but that's relatively easy to change and it may be configurable in the future.

Repetition counts are encoded back to back and bitpacked into a single column.  When the reader starts reading entities, it follows the same path as the writer, reading null markers and repetition counts when necessary.

For map and collection types (and eventually generalized to any interface type) the actual concrete type is encoded as its own column, it is RLE encoded as an int and a map of the class name to that types int value is encoded into the page header.

### Sidney Leaf Types: 

| Java Type       | Nullable           | Supported Encodings  
| --------------- |:------------------:| --------------------:
| boolean.class   | NO                 | PLAIN / BITPACKED / BITMAP (compressed with JavaEWAH)
| int.class       | NO                 | PLAIN / BITPACKED / DELTABITPACKEDHYBRID / RLE
| long.class      | NO                 | PLAIN / GROUPVARINT / RLE
| float.class     | NO                 | PLAIN / RLE
| double.class    | NO                 | PLAIN / RLE
| byte[].class    | YES                | PLAIN
| String.class    | YES                | PLAIN / DELTALENGTH / CHARASINT

All of (byte.class, short.class, char.class) are encoded as ints, so int encodings apply to them.

For the reference implementations of the primitive types, the same encodings apply, but they are nullable.

Generics are supported, and they will be respected even if they are inherited and the correct column type will be chosen. You may pass in parameter types into your writer and readers as well.

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
  
  Sid sid = new Sid();
  
  Writer<Foo> writer = sid.newWriter(Foo.class);
  writer.open(baos);
  writer.write(one);
  writer.close();
  
  ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
  Reader<Foo> reader = sid.newReader(Foo.class);
  reader.open(bais);
  Foo out = (reader.hasNext()) ? reader.read() : null;
  reader.close();
```

Closing the writer flushes to the underlying stream.  However if you are writing many objects, Sidney will write them out in pages of 1024 (soon to be configurable) flushing them as needed and the .close() will flush the last page.
