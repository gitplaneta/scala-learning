package eu.busz.impatient.ch5

/**
  * Created by Radek on 2016-09-17.
  */
class SomeClass {

  var i: Int = _

//  def i_ = (newVal: Int) {
//    println("gowno")
//  }

  def abc() = {

  }
}

class OtherClass {

  def test() = {
    val sm = new SomeClass()
    sm.i = 0
    sm.i

    new Per().age = 0
  }
}

class Per {
  private var privateAge = 0
  // Make private and rename
  def age = privateAge
  def age_=(newValue: Int) {
    if (newValue > privateAge) privateAge = newValue // Can't get younge
  }
}

class Stock(var _symbol: String) {

  // getter
  def symbol = _symbol

  // setter
  def symbol_=(s: String) {
    this.symbol = s
    println(s"symbol was updated, new value is $symbol")
  }

}

class C(val x: Int)

class D(x: Int) {

  def f = {
    println(x)
  }
}

class Father {
  val x = get()

  def get(): Int = 10
}

class Son extends Father {
  override def get(): Int = {
    20
  }
}

object Main extends App{

  new C(210).x
  //new D(10).x
  val f = new Per()
  f.age
  f.age_=(20)
  f.age = 12

  def printlnUnit(x: Unit): Unit = {
    println(x)
  }

  printlnUnit("asdf")

  println(new Son().x)

}

