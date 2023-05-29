package com.gmail.rayanral.index.operations

import com.gmail.rayanral.index.model.{GenericDocument, InvertedIndex}
import com.gmail.rayanral.index.util.StringUtils

import java.util.StringTokenizer

object DocumentOps {

  def processDocument(index: InvertedIndex, document: GenericDocument): Unit = {
    val st = new StringTokenizer(document.text)
    while (st.hasMoreTokens) {
      processToken(st.nextToken).foreach(t => index.add(t, document.fileName))
    }
  }

  def processToken(token: String): Option[String] = {
    val tokenLower = token.toLowerCase
    if (tokenLower.isEmpty) return None
    if (StringUtils.getStopwords.contains(token)) return None
    val noTagsToken = StringUtils.removeTags(tokenLower)
    val cleanToken  = StringUtils.removeNumbers(noTagsToken)
    Some(cleanToken)
  }

}
