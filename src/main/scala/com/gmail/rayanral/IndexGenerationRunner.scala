package com.gmail.rayanral

import com.gmail.rayanral.index.IndexGenerator
import com.gmail.rayanral.index.model.InvertedIndex
import com.gmail.rayanral.index.util.Config.{INDEXER_RUN_MODE, QUERY_RUN_MODE}
import com.gmail.rayanral.index.util.{Config, FileOps}
import org.apache.logging.log4j.scala.Logging

import scala.collection.parallel.CollectionConverters._

object IndexGenerationRunner extends Logging {

  def main(args: Array[String]): Unit = {
    val parser = new scopt.OptionParser[Config]("indexVerse") {}
    parser.parse(args, Config()) match {
      case Some(config) if config.runMode == INDEXER_RUN_MODE =>
        logger.info("Config parsed successfully. Starting indexer.")
        logger.info(s"""Threads: ${config.numberOfIndexerThreads}
                       |Input directory: ${config.inputDir}
                       |Input extension: ${config.fileExtension}
                       |Output path: ${config.outputPath}
                       |""".stripMargin)
        val index = runIndexer(config.inputDir, config.fileExtension, config.numberOfIndexerThreads)
        logger.info(IndexDisplay.printTopWords(index))
      case Some(config) if config.runMode == QUERY_RUN_MODE =>
        logger.info("Config parsed successfully. Starting query.")
        logger.info(s"Received query word: ${config.query}")
        val index = loadIndexer(config.inputDir)
        val documents = index.getDocumentsForToken(config.query)
        logger.info(s"Found documents in index: ${documents.mkString(", ")}")
      case None =>
    }
  }

  private def loadIndexer(indexDir: String): InvertedIndex = {
    FileOps.loadIndexFromFile(indexDir).get
  }

  def runIndexer(inputDir: String, extension: String, numThreads: Int): InvertedIndex = {
    val filesToIndex = FileOps.getFilesToIndex(inputDir, extension)
    val groupSize = getGroupSize(filesToIndex, numThreads)
    filesToIndex
      .grouped(groupSize)
      .toList
      .par
      .map { files =>
        val indexGenerator = new IndexGenerator(files)
        indexGenerator.generateIndex()
      }
      .reduce { (i1, i2) =>
        i1.merge(i2)
      }
  }

  private def getGroupSize(filesToIndex: List[String], numThreads: Int): Int = {
    val groupSize = filesToIndex.size / numThreads
    if (groupSize == 0) 1 else groupSize
  }

}
