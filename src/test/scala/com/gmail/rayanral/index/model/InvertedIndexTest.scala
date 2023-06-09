package com.gmail.rayanral.index.model

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class InvertedIndexTest extends AnyFlatSpec {

  "invertedIndex" should "add a word" in {
    val wordToAdd = "word"
    val index = InvertedIndex().add(wordToAdd, "file")
    index.getTopWords(1).map(_._1) should contain(wordToAdd)
  }

  "invertedIndex" should "add several words with correct counts" in {
    val index = InvertedIndex()
    val duplicate = "word"
    val wordsToAdd = List(duplicate, duplicate, "anotherWord")
    val updatedIndex = wordsToAdd.foldLeft(index)(_.add(_, "file"))
    updatedIndex.getTopWords(1).head._1 shouldBe duplicate
  }

  "invertedIndex" should "lookup document given single word" in {
    val indexedWord = "word"
    val index = InvertedIndex().add(indexedWord, "file")
    val docs = index.getDocumentsForToken(indexedWord)
    docs.size shouldBe 1
    docs should contain("file")
  }

  "invertedIndex" should "lookup document given multiple words" in {
    var index = InvertedIndex()
    index = index.add("word", "file")
    index = index.add("word2", "file2")
    index = index.add("word3", "file")
    val docs = index.getDocumentsForTokens(Set("word", "word3"))
    docs.size shouldBe 1
    docs should contain("file")
  }

  "invertedIndex" should "return empty when word doesn't exist" in {
    val docs = InvertedIndex().getDocumentsForToken("nonExistingWord")
    docs.size shouldBe 0
  }

}
