package com.gmail.rayanral

import com.gmail.rayanral.index.IndexGenerator
import com.gmail.rayanral.index.util.{Config, FileOps}
import org.apache.logging.log4j.scala.Logging

import scala.collection.parallel.CollectionConverters._

object IndexGenerationRunner extends Logging {

  def main(args: Array[String]): Unit = {
    val parser = new scopt.OptionParser[Config]("indexVerse") {}
    parser.parse(args, Config()) match {
      case Some(config) =>
        logger.info("Config parsed successfully. Starting indexer.")
        logger.info(s"""Threads: ${config.numberOfIndexerThreads}
                   |Input directory: ${config.inputDir}
                   |Input extension: ${config.fileExtension}
                   |Output path: ${config.outputPath}
                   |""".stripMargin)
        runIndexer(config.inputDir, config.fileExtension, config.numberOfIndexerThreads)
      case None =>
    }
  }

  private def runIndexer(inputDir: String, extension: String, numThreads: Int): Unit = {
    val filesToIndex = FileOps.getFilesToIndex(inputDir, extension)
    val groupSize = getGroupSize(filesToIndex, numThreads)
    val index = filesToIndex
      .grouped(groupSize)
      .toList
      .par
      .map { files =>
        val indexGenerator = new IndexGenerator(files)
        indexGenerator.generateIndex()
      }
      .reduce { (i1, i2) =>
        i1.mergeInPlace(i2)
      }
    logger.info(IndexDisplay.printTopWords(index))
  }

  private def getGroupSize(filesToIndex: List[String], numThreads: Int): Int = {
    val groupSize = filesToIndex.size / numThreads
    if (groupSize == 0) 1 else groupSize
  }

}
