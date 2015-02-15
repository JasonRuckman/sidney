package com.github.jasonruckman.sidney.generator;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class Strings {
  public static class RandomStringGenerator extends Generator<String> {
    private final Random random = new Random(11L);

    @Override
    public String next() {
      byte[] bytes = new byte[128];
      random.nextBytes(bytes);
      return new String(bytes, Charset.forName("UTF-8"));
    }
  }

  public static class FromCorpusStringGenerator extends Generator<String> {
    private final List<String> strings = new ArrayList<>();
    private final Random random = new Random(11L);

    public FromCorpusStringGenerator(Collection<String> corpus) {
      strings.addAll(corpus);
    }

    @Override
    public String next() {
      return strings.get(random.nextInt(strings.size() - 1));
    }
  }
}