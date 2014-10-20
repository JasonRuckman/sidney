sidney
======

Sidney is an experimental serializer using Dremel encoding, state of the art encoding techniques and code generation. 

It's named after my dog Sid.

Currently a WIP, not usable at all to anyone. 

Planned features: 

* Columnar storage of field values using state of the art encoding techniques
* Code generation on hot paths to avoid megamorphic call sites (if benchmarking shows that it actually does anything)

Not intended for any sort of long term storage, its main use case is in something like Apache Spark for RDD serialization.

State: 

Encodings: 

Boolean: Bit Packed / Ewah DONE

Int32: Bit Packed / BitPacking-Delta / Plain DONE GroupVarInt PLANNED

Int64: Plain / DONE

Float32: Plain / ExpSplitting DONE

Float64: Plain / ExpSplitting DONE

String: Delta Length DONE
