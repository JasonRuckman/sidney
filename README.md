sidney
======

Sidney is an experimental general java serializer. 

It's named after my dog Sid.

It is heavily influenced by the [Parquet](https://github.com/apache/incubator-parquet-mr) project.  It will decompose your POJOs into their fields and write those as columns.

Right now Sidney works on java beans, maps, arrays and collection types, there's underlying support to simply write primitives but I haven't exposed it yet via the API, enums are not yet supported (but planned very soon). 

Primitives in Sidney are these Java classes: 

boolean.class
byte.class
short.class
char.class
int.class
long.class
float.class
double.class
String.class
byte[].class

String and byte[] are handled slightly differently since they are nullable, you can customize the encoding and they will be stored as columns, but they will be marked as null or not null in the definition column.

Generics are supported, and they will be respected even if they are inherited and the correct column type will be chosen.

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
