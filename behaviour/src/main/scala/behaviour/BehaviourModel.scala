package aianash.behaviour

import com.sun.jna.{Library, Native}


private trait BehaviourJNA extends Library {

  def aianash_behaviourTrain(inputFilePath: String, outputFilePath: String)

}


private object BehaviourJNA {

  val instance = Native.loadLibrary("libaianash", classOf[BehaviourJNA]).asInstanceOf[BehaviourJNA]

}


class BehaviourModel {

  import BehaviourJNA.instance

  def train(inputFilePath: String, outputFilePath: String) {
    instance.aianash_behaviourTrain(inputFilePath, outputFilePath)
  }

}