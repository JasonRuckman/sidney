sidney
======

An experimental serializer using Dremel encoding, state of the art encoding techniques and code generation

Currently a WIP, not usable at all to anyone. 

Planned features: 

* Columnar storage of field values using state of the art encoding techniques
* Code generation on hot paths to avoid megamorphic function calls (if benchmarking shows that it actually does anything)

Not intended for any sort of long term storage, its main use case is in something like Apache Spark for RDD serialization.
