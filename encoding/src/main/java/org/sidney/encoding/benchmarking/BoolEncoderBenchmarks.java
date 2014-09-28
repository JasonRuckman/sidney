package org.sidney.encoding.benchmarking;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.sidney.core.Bytes;
import org.sidney.encoding.bool.BitPackingBoolDecoder;
import org.sidney.encoding.bool.BitPackingBoolEncoder;
import org.sidney.encoding.bool.BoolDecoder;
import org.sidney.encoding.bool.BoolEncoder;
import org.sidney.encoding.bool.EWAHBoolDecoder;
import org.sidney.encoding.bool.EWAHBoolEncoder;

import java.io.IOException;
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
    private int num = 65536;

    public BoolEncoderBenchmarks() {
        booleans = new boolean[num];
        for (int i = 0; i < num; i++) {
            booleans[i] = random.nextInt() % 2 == 0;
        }
    }

    @Benchmark
    @Group("boolEncoding")
    public boolean[] ewahBoolEncoderOnRandomInputs() throws IOException {
        return run(ewahBoolEncoders.get(), ewahBoolDecoders.get());
    }

    @Benchmark
    @Group("boolEncoding")
    public boolean[] bitpackingEncoderOnRandomInputs() throws IOException {
        return run(packingBoolEncoders.get(), packingBoolDecoders.get());
    }

    private boolean[] run(BoolEncoder encoder, BoolDecoder decoder) throws IOException {
        encoder.reset();
        encoder.writeBools(booleans);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encoder.writeToStream(baos);

        decoder.readFromStream(Bytes.wrap(baos.toByteArray()));
        return decoder.nextBools(num);
    }
}