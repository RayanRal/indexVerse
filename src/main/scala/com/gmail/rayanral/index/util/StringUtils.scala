package com.gmail.rayanral.index.util

import java.util.regex.Pattern

object StringUtils {

  def getStopWords: Set[String] = {
    val stopWords = "a,the,and,of,to,is,in,/><br"
    stopWords.split(",").toSet
  }

  implicit class StringExt(input: String) {

    def removeTags(): String = {
      val noTags = Pattern.compile("<.*?>")
      noTags.matcher(input).replaceAll("")
    }

    def removeNumbers(): String = {
      val noNumber = Pattern.compile("[^a-z|A-Z]")
      noNumber.matcher(input).replaceAll("")
    }

  }

}
