package eu.busz.impatient.ch5

import scala.beans.BeanProperty

/**
  * Created by Radek on 2016-10-01.
  */
class Student(@BeanProperty var name: String,  @BeanProperty var id: Long) {
}

case class MyCaseClass(param: String, param2: Int)

class ParamConstructor(val param: String)

class ParamInside {
  val param: String = ""
}