package com.gmail.rayanral.index

import com.gmail.rayanral.index.model.{GenericDocument, InvertedIndex}
import com.gmail.rayanral.index.util.FileOps.readFile
import com.gmail.rayanral.index.util.StringUtils
import com.gmail.rayanral.index.util.StringUtils.StringExt
import org.apache.logging.log4j.scala.Logging

import java.util.StringTokenizer

class IndexGenerator(filesToIndex: List[String]) extends Logging {

  private val index = InvertedIndex()

  def generateIndex(): InvertedIndex = {
    logger.info("Indexer started")
    filesToIndex.foreach { fileName =>
      val doc = readFile(fileName)
      processDocument(index, doc)
    }
    index
  }

  private def processDocument(index: InvertedIndex, document: GenericDocument): Unit = {
    val st = new StringTokenizer(document.text)
    while (st.hasMoreTokens) {
      processToken(st.nextToken).foreach(t => index.add(t, document.fileName))
    }
  }

  private def processToken(token: String): Option[String] = {
    val tokenLower = token.toLowerCase
    if (tokenLower.isEmpty || StringUtils.getStopWords.contains(tokenLower)) {
      None
    } else {
      Some(tokenLower.removeTags().removeNumbers())
    }

  }

}
