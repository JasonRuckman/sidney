package org.sidney.tools.codegen.bitpacking;

import org.junit.Test;

public class LongByteBasedBitPackingGeneratorTest {
    @Test
    public void runTest() throws Exception {
        LongByteBasedBitPackingGenerator.main(
            new String[] {
                "/Users/jasonruckman/code/sidney/encoding/src/main/java/"
            }
        );
    }
}
