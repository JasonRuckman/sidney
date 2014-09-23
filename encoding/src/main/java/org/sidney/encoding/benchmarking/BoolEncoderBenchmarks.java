package org.sidney.encoding.benchmarking;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.sidney.encoding.bool.BitPackingBoolDecoder;
import org.sidney.encoding.bool.BitPackingBoolEncoder;
import org.sidney.encoding.bool.EWAHBoolDecoder;
import org.sidney.encoding.bool.EWAHBoolEncoder;

import java.util.Random;

@State(Scope.Group)
@Warmup(iterations = 5)
@Fork(value = 1, warmups = 1)
public class BoolEncoderBenchmarks {
    private final boolean[] booleans;
    private final Random random = new Random(11L);
    private final ThreadLocal<EWAHBoolEncoder> ewahBoolEncoders = ThreadLocal.withInitial(EWAHBoolEncoder::new);
    private final ThreadLocal<EWAHBoolDecoder> ewahBoolDecoders = ThreadLocal.withInitial(EWAHBoolDecoder::new);
    private final ThreadLocal<BitPackingBoolEncoder> packingBoolEncoders = ThreadLocal.withInitial(
        BitPackingBoolEncoder::new
    );
    private final ThreadLocal<BitPackingBoolDecoder> packingBoolDecoders = ThreadLocal.withInitial(
        BitPackingBoolDecoder::new
    );
    private final ThreadLocal<byte[]> byteArrs;
    private int num = 65536;

    public BoolEncoderBenchmarks() {
        booleans = new boolean[num];
        for (int i = 0; i < num; i++) {
            booleans[i] = random.nextInt() % 2 == 0;
        }
        byteArrs = ThreadLocal.withInitial(() -> new byte[num * 8 + 4]);
    }

    @Benchmark
    @Group("boolEncoding")
    public boolean[] ewahBoolEncoderOnRandomInputs() {
        byte[] bytes = byteArrs.get();

        EWAHBoolEncoder EWAHBoolEncoder = ewahBoolEncoders.get();
        EWAHBoolDecoder EWAHBoolDecoder = ewahBoolDecoders.get();

        EWAHBoolEncoder.reset();
        EWAHBoolEncoder.writeBools(booleans);
        EWAHBoolEncoder.writeToBuffer(bytes, 0);

        EWAHBoolDecoder.readFromBuffer(bytes);
        return EWAHBoolDecoder.nextBools(num);
    }

    @Benchmark
    @Group("boolEncoding")
    public boolean[] bitpackingEncoderOnRandomInputs() {
        BitPackingBoolEncoder packingBoolEncoder = packingBoolEncoders.get();
        BitPackingBoolDecoder packingBoolDecoder = packingBoolDecoders.get();

        packingBoolEncoder.reset();
        packingBoolEncoder.writeBools(booleans);
        packingBoolEncoder.writeToBuffer(byteArrs.get(), 0);

        packingBoolDecoder.readFromBuffer(byteArrs.get());
        return packingBoolDecoder.nextBools(num);
    }
}