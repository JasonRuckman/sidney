package org.sidney.benchmarking;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.sidney.core.Bytes;
import org.sidney.encoding.bool.BoolDecoder;
import org.sidney.encoding.bool.BoolEncoder;
import org.sidney.encoding.bool.EWAHBoolDecoder;
import org.sidney.encoding.bool.EWAHBoolEncoder;
import org.sidney.encoding.bool.PlainBoolDecoder;
import org.sidney.encoding.bool.PlainBoolEncoder;

import java.io.IOException;
import java.util.Random;

@State(Scope.Benchmark)
@Warmup(iterations = 5)
@Fork(value = 1, warmups = 1)
public class BoolEncoderBenchmarks extends BenchmarkingBase {
    private final boolean[] booleans;
    private final Random random = new Random(11L);
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
        return run(getEncoder(EWAHBoolEncoder.class), getDecoder(EWAHBoolDecoder.class));
    }

    @Benchmark
    @Group("boolEncoding")
    public boolean[] plainEncoderOnRandomInputs() throws IOException {
        return run(getEncoder(PlainBoolEncoder.class), getDecoder(PlainBoolDecoder.class));
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