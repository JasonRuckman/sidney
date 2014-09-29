package parquet.column.values.bitpacking;

public enum LongPacker {
    BIG_ENDIAN {
        @Override
        public LongBytePacker newPacker(int width) {
            return beFactory.newBytePacker(width);
        }
    },
    LITTLE_ENDIAN {
        @Override
        public LongBytePacker newPacker(int width) {
            return leFactory.newBytePacker(width);
        }
    };

    public abstract LongBytePacker newPacker(int width);

    private static final LongBytePackerFactory leFactory = LongByteBitPackingLE.factory;
    private static final LongBytePackerFactory beFactory = LongByteBitPackingBE.factory;
}
