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
        Some(mutable.Set(filename))
    }
  }

  def getTopWords(count: Int): List[(String, Int)] =
    tokenIndex.toList.sortBy(_._2.size).reverse.map { case (token, docs) => token -> docs.size }.take(count)

  def doesWordExist(token: String): Boolean = tokenIndex.contains(token)

  def getDocumentsForToken(token: String): Set[String] = tokenIndex.getOrElse(token, Set.empty[String]).toSet

  def getDocumentsForTokens(tokens: Set[String]): Set[String] =
    tokens.map(getDocumentsForToken).reduce { (d1: Set[String], d2: Set[String]) => d1.intersect(d2) }

  /*
   returns immutable copy of underlying index, for serialization / backup
   */
  def getIndex: Map[String, Set[String]] = tokenIndex.view.mapValues(s => s.toSet).toMap

  def mergeInPlace(other: InvertedIndex): InvertedIndex = {
    other.tokenIndex.flatMap { case (k, set) =>
      set.map(v => (k, v))
    }.foreach { case (token, filename) =>
      add(token, filename)
    }
    this
  }

}
