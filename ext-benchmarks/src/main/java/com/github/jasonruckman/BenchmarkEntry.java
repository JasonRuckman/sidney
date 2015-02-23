package com.github.jasonruckman;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;

public class BenchmarkEntry {
  public static void main(String[] args) throws IOException, RunnerException {
    Options e = new OptionsBuilder()
        .threads(1)
        .forks(1)
        .measurementIterations(1)
        .warmupIterations(10).build();

    Runner runner = new Runner(e);
    runner.run();
  }
}