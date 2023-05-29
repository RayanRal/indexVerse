package com.gmail.rayanral.index

import com.gmail.rayanral.index.model.InvertedIndex
import com.gmail.rayanral.index.operations.DocumentOps.processDocument
import com.gmail.rayanral.index.operations.FileOps.readFile

class IndexGenerator(filesToIndex: List[String]) {

  private val index = new InvertedIndex()

  def generateIndex(): InvertedIndex = {
    System.out.println("Indexer started")
    filesToIndex.foreach { fileName =>
      val doc = readFile(fileName)
      processDocument(index, doc)
    }
    index
  }

}
