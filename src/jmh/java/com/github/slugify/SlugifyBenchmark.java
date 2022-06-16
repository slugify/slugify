package com.github.slugify;

import java.util.concurrent.TimeUnit;
import lombok.NoArgsConstructor;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * Class for benchmarking.
 *
 * @author Bartosz Galek
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)
@NoArgsConstructor
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class SlugifyBenchmark {
  @Param({"simple string",
      "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
      "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
      "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
          + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
      "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
          + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
          + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
      "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
          + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
          + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
          + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
      "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
          + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
          + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
          + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
          + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"})
  public String lengthVarySimpleString;

  @Param({"some \\u0105 letters ^ are % strange *",
      "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105",
      "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105",
      "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105",
      "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105",
      "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105",
      "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105\\u0105"
          + "\\u0105"})
  public String lengthVarySpecialString;

  @Benchmark
  public void stringLenghtPerformance() {
    Slugify.builder().build().slugify(lengthVarySimpleString);
  }

  @Benchmark
  public void specialStringLenghtPerformance() {
    Slugify.builder().build().slugify(lengthVarySpecialString);
  }

  /**
   * Run benchmarks.
   *
   * @param args CLI arguments
   * @throws RunnerException If benchmarking fails
   */
  public static void main(final String[] args) throws RunnerException {
    final Options opt = new OptionsBuilder().include(SlugifyBenchmark.class.getSimpleName())
        .build();
    new Runner(opt).run();
  }
}
