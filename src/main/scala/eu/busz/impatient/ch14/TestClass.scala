package scala.eu.busz.impatient.ch14

class Covariant[+T](t: T) {
  def get: T = t
}

class Contravariant[-A]


object Main extends App {
  println("Hello")

  val cv: Contravariant[String] = new Contravariant[AnyRef]

}