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
import org.openjdk.jmh.annotations.Setup;
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
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)
@NoArgsConstructor
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class SlugifyBenchmark {

  /**
   * State for benchmarks varying the length of simple ASCII strings.
   */
  @State(Scope.Benchmark)
  @NoArgsConstructor
  public static class SimpleStringState {
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
    public String input;

    private Slugify slugify;

    /**
     * Set up the Slugify instance.
     */
    @Setup
    public void setup() {
      slugify = Slugify.builder().build();
    }
  }

  /**
   * State for benchmarks varying the length of strings with special characters.
   */
  @State(Scope.Benchmark)
  @NoArgsConstructor
  public static class SpecialStringState {
    @Param({"some ą letters ^ are % strange *",
        "ąąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąą",
        "ąąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąą",
        "ąąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąą",
        "ąąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąą",
        "ąąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąą",
        "ąąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ą"})
    public String input;

    private Slugify slugify;

    /**
     * Set up the Slugify instance.
     */
    @Setup
    public void setup() {
      slugify = Slugify.builder().build();
    }
  }

  /**
   * State for benchmarks measuring the transliterator code path with varying string lengths.
   */
  @State(Scope.Benchmark)
  @NoArgsConstructor
  public static class TransliteratorStringState {
    @Param({"some ą letters ^ are % strange *",
        "ąąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąą",
        "ąąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąą",
        "ąąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąą",
        "ąąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąą",
        "ąąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąą",
        "ąąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ąąąąąąąąąąąą"
            + "ą"})
    public String input;

    private Slugify slugify;

    /**
     * Set up the Slugify instance with transliterator enabled.
     */
    @Setup
    public void setup() {
      slugify = Slugify.builder().transliterator(true).build();
    }
  }

  @Benchmark
  public void stringLengthPerformance(final SimpleStringState state) {
    state.slugify.slugify(state.input);
  }

  @Benchmark
  public void specialStringLengthPerformance(final SpecialStringState state) {
    state.slugify.slugify(state.input);
  }

  @Benchmark
  public void transliteratorPerformance(final TransliteratorStringState state) {
    state.slugify.slugify(state.input);
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
