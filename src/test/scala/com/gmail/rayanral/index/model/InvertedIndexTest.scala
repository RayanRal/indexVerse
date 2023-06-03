package com.gmail.rayanral.index.model

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class InvertedIndexTest extends AnyFlatSpec {

  "invertedIndex" should "add a word" in {
    val index = new InvertedIndex()
    val wordToAdd = "word"
    index.add(wordToAdd, "file")
    index.getTopWords(1).map(_._1) should contain(wordToAdd)
  }

  "invertedIndex" should "add several words with correct counts" in {
    val index = new InvertedIndex()
    val duplicate = "word"
    val wordsToAdd = List(duplicate, duplicate, "anotherWord")
    wordsToAdd.foreach(index.add(_, "file"))
    index.getTopWords(1).head._1 shouldBe duplicate
  }

}
