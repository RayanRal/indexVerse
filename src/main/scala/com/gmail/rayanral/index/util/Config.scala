package com.gmail.rayanral.index.util

import com.gmail.rayanral.index.util.Config.{DEFAULT_EXTENSION, DEFAULT_INPUT_DIR, DEFAULT_OUTPUT_PATH, INDEXER_RUN_MODE, NUMBER_OF_INDEXER_THREADS}

case class Config(
    runMode: String = INDEXER_RUN_MODE,
    query: String = "",
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
  val INDEXER_RUN_MODE = "indexer"
  val QUERY_RUN_MODE = "query"

}
