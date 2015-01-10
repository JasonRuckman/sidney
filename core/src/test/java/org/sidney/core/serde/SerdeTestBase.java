package org.sidney.core.serde;

import java.util.Random;

public class SerdeTestBase {
    private final DataFactory dataFactory = new DataFactory();

    public DataFactory getDataFactory() {
        return dataFactory;
    }

    public Random getRandom() {
        return dataFactory.getRandom();
    }
}
