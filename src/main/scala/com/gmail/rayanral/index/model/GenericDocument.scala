package com.gmail.rayanral.index.model

import com.gmail.rayanral.index.model.GenericDocument.DELIMITER

case class GenericDocument(
  filePath: String,
  fileName: String,
  text: String
) {

  def this(filePath: String, text: String) =
    this(
      filePath = filePath,
      fileName = filePath.substring(filePath.lastIndexOf(DELIMITER) + 1),
      text = text
    )

}

object GenericDocument {
  val DELIMITER: String = "/"
}
