//type class
trait Printable[A] {
  def format(a: A): String
}

//type class instances:
object PrintableInstances {
  implicit val StringPrintable = new Printable[String] {
    override def format(a: String): String = a.toString
  }

  implicit val IntPrintable = new Printable[Int] {
    override def format(a: Int): String = a.toString
  }

  implicit val CatPrintable = new Printable[Cat] {
    override def format(c: Cat): String = s"${c.name} is a ${c.age} year-old ${c.color} cat"
  }
}

object Printable {
  def format[A: Printable](a: A): String = {
    implicitly[Printable[A]].format(a)
  }

  def print[A: Printable](a: A): Unit = {
    println(implicitly[Printable[A]].format(a))
  }
}

final case class Cat(name: String, age: Int, color: String)

//implicit class allowing to do "cat.format"
object PrintableSyntax {

  implicit class PrintOps[A](a: A) {
    def format(implicit p: Printable[A]): String = p.format(a)

    def print(implicit p: Printable[A]): Unit = println(format)
  }

  Cat("Mruczek", 20, "black&white").print
}

import PrintableInstances._

val cat = Cat("Mruczek", 20, "black&white")
Printable.print(cat)



