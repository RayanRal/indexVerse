package com.gmail.rayanral.index.util

import com.gmail.rayanral.index.model.{DirectoryNotFoundException, GenericDocument, InvertedIndex}
import io.github.vigoo.desert._
import io.github.vigoo.desert.internal.JavaStreamBinaryOutput
import org.apache.logging.log4j.scala.Logging

import java.io.{ByteArrayOutputStream, File, FilenameFilter}
import java.nio.file.{Files, Paths}

object FileOps extends Logging {

  def readFile(filename: String): GenericDocument = {
    val text = Files.readString(Paths.get(filename))
    new GenericDocument(filename, text)
  }

  def getFilesToIndex(inputDirectory: String, extension: String): List[String] =
    getAllFiles(inputDirectory, extension, relativePath = false) match {
      case Left(v)         => throw v
      case Right(fileList) => fileList
    }

  /*
  Serializes index to disk
   */
  def writeIndexToFile(index: InvertedIndex, outputPath: String): Unit = {
    val stream = new ByteArrayOutputStream()
    val output = new JavaStreamBinaryOutput(stream)
    serializeToArray(index.getIndex) match {
      case Left(failure) =>
        logger.error(s"Failed to write index: ${failure.message}")
      case Right(bytes) =>
        output.writeBytes(bytes)
        stream.flush()
        Files.write(java.nio.file.Paths.get(outputPath), stream.toByteArray)
    }

  }

  /**
   * Look for files matching an extension in a given folder
   *
   * @param directoryPath
   *   path to look for
   * @param extension
   *   extension to filter for
   * @param relativePath
   *   if true will return the path of the file relative to the directoryPath.
   *   False will return a full path
   * @return
   *   array of string, one string per found file
   */
  private def getAllFiles(
      directoryPath: String,
      extension: String,
      relativePath: Boolean
  ): Either[DirectoryNotFoundException, List[String]] = {
    val dir = new File(directoryPath)
    val filter = new FilenameFilter {
      override def accept(dir: File, name: String): Boolean =
        !name.startsWith(".") && name.endsWith(extension)
    }
    val r = dir.list(filter)
    if (r != null) {
      if (!relativePath) for (i <- 0 until r.length) {
        r(i) = dir.getAbsolutePath + "/" + r(i)
      }
      Right(r.toList)
    } else Left(new DirectoryNotFoundException)
  }

}
