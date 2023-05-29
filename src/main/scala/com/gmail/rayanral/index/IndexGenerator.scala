package com.gmail.rayanral.index

import com.gmail.rayanral.index.model.{GenericDocument, InvertedIndex}
import com.gmail.rayanral.index.util.FileOps.readFile
import com.gmail.rayanral.index.util.StringUtils

import java.util.StringTokenizer

class IndexGenerator(filesToIndex: List[String]) {

  private val index = new InvertedIndex()

  def generateIndex(): InvertedIndex = {
    System.out.println("Indexer started")
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
    if (tokenLower.isEmpty) return None
    if (StringUtils.getStopwords.contains(token)) return None
    val noTagsToken = StringUtils.removeTags(tokenLower)
    val cleanToken = StringUtils.removeNumbers(noTagsToken)
    Some(cleanToken)
  }

}
