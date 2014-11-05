package org.sidney.benchmarking.random;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.sidney.benchmarking.BenchmarkingBase;
import org.sidney.core.Bytes;
import org.sidney.core.encoding.string.CharAsIntStringDecoder;
import org.sidney.core.encoding.string.CharAsIntStringEncoder;
import org.sidney.core.encoding.string.DeltaLengthStringDecoder;
import org.sidney.core.encoding.string.DeltaLengthStringEncoder;
import org.sidney.core.encoding.string.PlainStringDecoder;
import org.sidney.core.encoding.string.PlainStringEncoder;
import org.sidney.core.encoding.string.StringDecoder;
import org.sidney.core.encoding.string.StringEncoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

@State(Scope.Benchmark)
@Warmup(iterations = 10)
@Measurement(iterations = 2)
@Fork(1)
public class StringEncoderBenchmarks extends BenchmarkingBase {
    private int num = 65536;
    private final String[] strings;

    public StringEncoderBenchmarks() {
        strings = new String[num];
        Random random = new Random(11L);
        for (int i = 0; i < num; i++) {
            char[] chars = new char[124];
            for (int j = 0; j < chars.length; j++) {
                chars[j] = (char) random.nextInt(32767);
            }
            strings[i] = new String(chars);
        }
    }

    @Benchmark
    @Group("stringEncodersRandom")
    public String[] runPlainStringEncoder() throws IOException {
        return run(getEncoder(PlainStringEncoder.class), getDecoder(PlainStringDecoder.class));
    }

    @Benchmark
    @Group("stringEncodersRandom")
    public String[] runDeltaLengthEncoder() throws IOException {
        return run(getEncoder(DeltaLengthStringEncoder.class), getDecoder(DeltaLengthStringDecoder.class));
    }

    @Benchmark
    @Group("stringEncodersRandom")
    public String[] runCharAsIntEncoder() throws IOException {
        return run(getEncoder(CharAsIntStringEncoder.class), getDecoder(CharAsIntStringDecoder.class));
    }

    private String[] run(StringEncoder encoder, StringDecoder decoder) throws IOException {
        for (String i : strings) {
            encoder.writeString(i);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encoder.writeToStream(baos);
        encoder.reset();
        decoder.populateBufferFromStream(Bytes.wrap(baos.toByteArray()));
        return decoder.readStrings(num);
    }
}
