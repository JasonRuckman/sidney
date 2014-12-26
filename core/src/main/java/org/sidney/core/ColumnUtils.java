package org.sidney.core;

import java.util.BitSet;

public class ColumnUtils {
    public byte encode8Nulls(boolean[] columnOrdinals) {
        if (columnOrdinals.length > 8) {
            throw new SidneyException(String.format("Cannot encode %s ordinals on one byte", columnOrdinals.length));
        }

        return (byte) encodeNulls(columnOrdinals)[0];
    }

    public short encode16Nulls(boolean[] columnOrdinals) {
        if (columnOrdinals.length > 16) {
            throw new SidneyException(String.format("Cannot encode %s ordinals on two bytes", columnOrdinals.length));
        }

        return (short) encodeNulls(columnOrdinals)[0];
    }

    public int encode32Nulls(boolean[] columnOrdinals) {
        if (columnOrdinals.length > 32) {
            throw new SidneyException(String.format("Cannot encode %s ordinals on 4 bytes", columnOrdinals.length));
        }

        return (int) encodeNulls(columnOrdinals)[0];
    }

    public long[] encodeNulls(boolean[] columnOrdinals) {
        BitSet bitSet = new BitSet();
        for (int i = 0; i < columnOrdinals.length; i++) {
            bitSet.set(i);
        }
        return bitSet.toLongArray();
    }
}
