package aianash.ml

import com.sun.jna.{Library, Native}

trait ModelTrainer extends Library {
  def train(inputFilePath: String, outputFilePath: String)
}

object ModelTrainer {

  val instance = Native.loadLibrary("libaiautil", classOf[ModelTrainer]).asInstanceOf[ModelTrainer]

}