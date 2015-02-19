/**
 * Copyright 2014 Jason Ruckman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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