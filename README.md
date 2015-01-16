sidney
======

Sidney is an experimental general java serializer. 

It's named after my dog Sid.

It is heavily influenced by the [Parquet](https://github.com/apache/incubator-parquet-mr) project.  It will decompose your POJOs into their fields and write those as columns.

Right now Sidney works on java beans, maps, arrays and collection types, there's underlying support to simply write primitives but I haven't exposed it yet via the API, enums are not yet supported (but planned very soon). 


Sidney Leaf Types: 

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

Generics are supported, and they will be respected even if they are inherited and the correct column type will be chosen. You may pass in parameter types into your writer and readers as well.

A simple example of a java bean:

```
  public class Foo {
    private int first;
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
  
  Writer<Foo> writer = sid.newCachedWriter(Foo.class, baos);
  writer.write(one);
  writer.close();
  
  ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
  Reader<Foo> reader = sid.newCachedReader(Foo.class, bais);
  Foo out = reader.read();
```

Closing the writer flushes to the underlying stream.  However if you are writing many objects, Sidney will write them out in pages of 1024 (soon to be configurable) and the .close() will flush the last page only.
