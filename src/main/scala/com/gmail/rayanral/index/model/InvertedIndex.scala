package com.gmail.rayanral.index.model

import scala.collection.immutable.HashMap

case class InvertedIndex(
    tokenIndex: Map[Token, Set[Filename]] = HashMap[Token, Set[Filename]]()
) {

  def add(token: Token, filename: Filename): InvertedIndex = {
    if (token == null) return this
    tokenIndex.get(token) match {
      case Some(set) =>
        InvertedIndex(tokenIndex.updated(token, set + filename))
      case None =>
        InvertedIndex(tokenIndex.updated(token, Set(filename)))
    }
  }

  def getTopWords(count: Int): List[(Token, Int)] =
    tokenIndex.toList
      .sortBy(_._2.size)
      .reverse
      .map { case (token, docs) =>
        token -> docs.size
      }
      .take(count)

  def containsWord(token: Token): Boolean = tokenIndex.contains(token)

  def getDocumentsForToken(token: Token): Set[Filename] = tokenIndex.getOrElse(token, Set.empty[String])

  def getDocumentsForAllTokens(tokens: Set[Token]): Set[Filename] =
    tokens.map(getDocumentsForToken).reduce((d1: Set[String], d2: Set[String]) => d1.intersect(d2))

  def getDocumentsForAnyTokens(tokens: Set[Token]): Set[Filename] =
    tokens.map(getDocumentsForToken).reduce((d1: Set[String], d2: Set[String]) => d1.union(d2))

  def merge(other: InvertedIndex): InvertedIndex = {
    val unionTokens = this.tokenIndex.keys.toSet.union(other.tokenIndex.keys.toSet)
    val unionMap: Map[Token, Set[Filename]] = unionTokens.map { token =>
      val unionSet = this.tokenIndex.getOrElse(token, Set.empty[Filename])
        .union(other.tokenIndex.getOrElse(token, Set.empty[Filename]))
      token -> unionSet
    }.toMap
    InvertedIndex(unionMap)
  }

}
