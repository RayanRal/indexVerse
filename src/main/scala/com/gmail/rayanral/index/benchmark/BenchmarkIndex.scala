package com.gmail.rayanral.index.benchmark

import com.gmail.rayanral.IndexGenerationRunner
import com.gmail.rayanral.index.util.Config
import org.openjdk.jmh.annotations._
import org.openjdk.jmh.infra.Blackhole

@BenchmarkMode(Array(Mode.AverageTime))
@Fork(value = 2)
@Warmup(iterations = 1)
@Measurement(iterations = 3)
@State(Scope.Benchmark)
class BenchmarkIndex {

  @Param(Array("1", "2", "4", "8"))
  var numThreads = 0

  @Benchmark
  def testIndexing(blackHole: Blackhole): Unit = {
    val index = IndexGenerationRunner.runIndexer(
      inputDir = Config.DEFAULT_INPUT_DIR,
      extension = Config.DEFAULT_EXTENSION,
      numThreads = numThreads
    )
    blackHole.consume(index)
  }

}
