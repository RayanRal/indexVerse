package com.gmail.rayanral.index.util

import java.util.regex.Pattern

object StringUtils {

  def getStopwords: Set[String] = {
    val stopwords = "a,the,and,of,to,is,in,/><br"
    stopwords.split(",").toSet
  }

  def removeTags(input: String): String = {
    val noTags = Pattern.compile("<.*?>")
    noTags.matcher(input).replaceAll("")
  }

  def removeNumbers(input: String): String = {
    val noNumber = Pattern.compile("[^a-z|A-Z]")
    noNumber.matcher(input).replaceAll("")
  }

}
