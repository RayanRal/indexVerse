package com.gmail.rayanral.index

import com.gmail.rayanral.index.model.{GenericDocument, InvertedIndex}
import com.gmail.rayanral.index.util.FileOps.readFile
import com.gmail.rayanral.index.util.StringUtils
import com.gmail.rayanral.index.util.StringUtils.StringExt
import org.apache.logging.log4j.scala.Logging

class IndexGenerator(filesToIndex: List[String]) extends Logging {

  def generateIndex(): InvertedIndex = {
    logger.info("Indexer started")
    filesToIndex.map(readFile).foldLeft(InvertedIndex()) { case (idx, doc) =>
      val updIdx = processDocument(idx, doc)
      updIdx
    }
  }

  private def processDocument(index: InvertedIndex, document: GenericDocument): InvertedIndex =
    document.text
      .split(" ")
      .flatMap(processToken)
      .foldLeft(index)(_.add(_, document.fileName))

  private def processToken(token: String): Option[String] = {
    val tokenLower = token.toLowerCase
    if (tokenLower.isEmpty || StringUtils.getStopWords.contains(tokenLower)) {
      None
    } else {
      Some(tokenLower.removeTags().removeNumbers())
    }

  }

}
