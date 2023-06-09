package com.gmail.rayanral.index.model

import scala.collection.immutable.HashMap

case class InvertedIndex(
    tokenIndex: Map[String, Set[String]] =
      HashMap[String, Set[String]]()
) {

  def add(token: String, filename: String): InvertedIndex = {
    if (token == null) return this
    tokenIndex.get(token) match {
      case Some(set) =>
        val newSet = set + filename
        InvertedIndex(tokenIndex.updated(token, newSet))
      case None =>
        InvertedIndex(tokenIndex.updated(token, Set(filename)))
    }
  }

  def getTopWords(count: Int): List[(String, Int)] =
    tokenIndex.toList.sortBy(_._2.size).reverse.map { case (token, docs) => token -> docs.size }.take(count)

  def doesWordExist(token: String): Boolean = tokenIndex.contains(token)

  def getDocumentsForToken(token: String): Set[String] = tokenIndex.getOrElse(token, Set.empty[String])

  def getDocumentsForTokens(tokens: Set[String]): Set[String] =
    tokens.map(getDocumentsForToken).reduce((d1: Set[String], d2: Set[String]) => d1.intersect(d2))

  def mergeInPlace(other: InvertedIndex): InvertedIndex = {
    other.tokenIndex.flatMap { case (k, set) =>
      set.map(v => (k, v))
    }.foreach { case (token, filename) =>
      add(token, filename)
    }
    this
  }

}
