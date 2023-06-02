package com.gmail.rayanral

import com.gmail.rayanral.index.model.InvertedIndex

object IndexDisplay {

  def printTopWords(invertedIndex: InvertedIndex, wordsCount: Int = 3): String =
    invertedIndex.getTopWords(wordsCount).mkString(s"InvertedIndex top ${wordsCount} words:\n<", ", ", ">")


}
