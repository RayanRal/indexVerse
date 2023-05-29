package com.gmail.rayanral.index.util

import com.gmail.rayanral.index.util.Config.{
  DEFAULT_EXTENSION,
  DEFAULT_INPUT_DIR,
  DEFAULT_OUTPUT_PATH,
  NUMBER_OF_INDEXER_THREADS
}

case class Config(
    numberOfIndexerThreads: Int = NUMBER_OF_INDEXER_THREADS,
    inputDir: String = DEFAULT_INPUT_DIR,
    fileExtension: String = DEFAULT_EXTENSION,
    outputPath: String = DEFAULT_OUTPUT_PATH
)

object Config {

  val NUMBER_OF_INDEXER_THREADS = 4
  val DEFAULT_INPUT_DIR = "src/main/resources/indexData"
  val DEFAULT_EXTENSION = ".txt"
  val DEFAULT_OUTPUT_PATH = "src/main/resources/output/tokenIndex.serialized"

}
