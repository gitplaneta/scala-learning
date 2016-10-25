package eu.busz.impatient.ch10

/**
  * Created by Radek on 2016-10-17.
  */
class TraitLayering extends EmptyLogger with TimestampLogger {
  override def log(msg: String): Unit = super.log(msg)
  val dupa: String = ""
}

trait Logger {
  def log(msg: String)
}

trait TimestampLogger extends Logger {
  val dupa: String

  abstract override def log(msg: String): Unit = {
    super.log(new java.util.Date() + " " + msg)
  }
}

trait EmptyLogger extends Logger {
  val dupa: String

  override def log(msg: String) = {

  }
}

trait Engine {
  val typeOfGasoline: String
  def startEngine(): Unit
}

trait DieselEngine extends Engine {
  this: Car =>
  override val typeOfGasoline = "Diesel"
}

//trait Rim extends RoundThing{
//  def spin(): Unit = {
//    println("Rim is spinning")
//  }
//}

object MainObj extends App {
  println("Hello")
}


trait Foo {
  def foo()
}

trait M extends Foo {
  abstract override def foo() {println("M"); super.foo()}
}

class FooImpl1 extends Foo {
  override def foo() {println("Impl")}
}

///
trait RoundThing {
  def spin()
}

trait Wheel extends RoundThing{
  abstract override def spin() {
    println("Wheel is spinnin'")
    super.spin()
  }
}

trait Rim extends RoundThing {
  override def spin(): Unit = {
    println("Rim")
  }
}

class Car extends Rim with Wheel {

  override def spin() {

  }
}

