package com.gmail.rayanral

import com.gmail.rayanral.index.IndexGenerator
import com.gmail.rayanral.index.operations.FileOps
import scala.collection.parallel.CollectionConverters._


object IndexGenerationRunner {

  val NUMBER_OF_INDEXER_THREADS = 1 // Property.getInt("numOfThreads");
  val DEFAULT_INPUT_DIR         = "src/main/resources/"
  val DEFAULT_EXTENSION         = ".txt"
  val DEFAULT_OUTPUT_PATH       = "src/main/resources/output/"
  val DEFAULT_INDEX_OUTPUT_FILE = "tokenIndex.serialized"

  def main(args: Array[String]): Unit = {
    val filesToIndex   = FileOps.getFilesToIndex(DEFAULT_INPUT_DIR, DEFAULT_EXTENSION)
    val groupSize = filesToIndex.size / NUMBER_OF_INDEXER_THREADS
    val index = filesToIndex.grouped(groupSize).toList.par.map { files =>
      val indexGenerator = new IndexGenerator(files)
      val index = indexGenerator.generateIndex()
      index
    }.reduce { (i1, i2) =>
      i1.mergeInPlace(i2)
    }
    println(index.printableInvertedIndex())
  }

}
