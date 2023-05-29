package com.gmail.rayanral.index.model

import scala.collection.mutable

class InvertedIndex(private val tokenIndex: mutable.Map[String, mutable.Set[String]]) {

  def this() =
    this(
      new mutable.HashMap[String, mutable.Set[String]]()
        .withDefaultValue(mutable.Set.empty[String])
    )

  def add(token: String, filename: String): Unit = {
    if (token == null) return
    tokenIndex.updateWith(token) {
      case Some(set) =>
        set.add(filename)
        Some(set)
      case None =>
        Some(mutable.Set.empty[String])
    }
  }

  def size: Int = tokenIndex.size

  def getTopWords(count: Int): List[String] =
    tokenIndex.toList.sortBy(_._2.size).reverse.map(_._1).take(count)

  def getFilesForToken(token: String): Set[String] = tokenIndex.getOrElse(token, Set.empty[String]).toSet

  def mergeInPlace(other: InvertedIndex): InvertedIndex = {
    other.tokenIndex.flatMap { case (k, set) =>
      set.map { v => (k, v) }
    }.foreach { case (token, filename) =>
      add(token, filename)
    }
    this
  }

  def printableInvertedIndex(): String = {
    getTopWords(3).mkString("InvertedIndex top 3 words:\n<", ", ", ">")
  }

//  def writeToFile(outputPath: String): Unit = {
//    try {
//      val oos = new Nothing(new Nothing(outputPath))
//      try {
//        oos.writeObject(tokenIndex)
//        oos.flush
//      } catch {
//        case e: Nothing =>
//          e.printStackTrace
//      } finally if (oos != null) oos.close()
//    }
//  }

}
