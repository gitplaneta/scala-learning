object implicit_conversion {
  class B {
    def bar = "This is the best method ever!"
  }
  class A {
    def aar = "Worst method ever"
  }
  implicit def aToB(in: A): B = new B()
  new A().bar
  new A().aar
}

trait Writer[T] {
  def something(t:T): String
}
class Something[T] {
  def extend(s: T)(implicit wr: Writer[T]): String = {
    wr.something(s)
  }
}

class Something2[T] {
  def extend[T: Writer](s: T): String = {
    implicitly[Writer[T]].something(s)
  }
}


object context_bounds {

  trait Writer[T] {
    def convertToString(t: T): String
  }
  class HtmlWriter[A: Writer](data: A) {
    def html = implicitly[Writer[A]].convertToString(data)
  }
}

object DrinkingKoolAid {
  implicit class OhYeah(i: Int) {
    def yeah =  (0 to i).foreach(_ => println("oh yeah"))
  }

  5.yeah
}

object string_type_class {
  implicit class BadLanguageAppender(str: String) {
    def apppendBadWord = str + "fuck"
  }

  "That is a mind".apppendBadWord
}

object creating_type_classes {
  case class Person(name: String, email: String)

  trait Equality[A] {
    def equal(a: A, b: A): Boolean
  }

  object PersonEmailEquality extends Equality[Person]{
    override def equal(a: Person, b: Person): Boolean = a.email == b.email
  }
  object PersonNameEquality extends Equality[Person]{
    override def equal(a: Person, b: Person): Boolean = a.name == b.name
  }
}

object implicit_packaging {

  final case class Order(units: Int, unitPrice: Double) {
    val totalPrice: Double = units * unitPrice
  }

  object Order {
    implicit val totalPriceOrdering = Ordering.fromLessThan[Order](_.totalPrice < _.totalPrice)
  }

  object OrderUnitPriceOrdering {
    implicit val ordering = Ordering.fromLessThan[Order](_.unitPrice < _.unitPrice)
  }

  object OrderUnitOrdeing {
    implicit val totalPriceOrdering = Ordering.fromLessThan[Order](_.units < _.units)
  }

}

object rational {
  implicit val orxd = Ordering.fromLessThan[Rational]((a,b) => a.numerator.toDouble / a.denominator.toDouble < b.numerator.toDouble / b.denominator.toDouble)

  final case class Rational(numerator: Int, denominator: Int)

  assert(List(Rational(1, 2), Rational(3, 4), Rational(1, 3)).sorted ==
    List(Rational(1, 3), Rational(1, 2), Rational(3, 4)))
}

object ord1 {
  implicit val absOrdering = Ordering.fromLessThan[Int]((a,b) => Math.abs(a) < Math.abs(b))
  List(-4, -1, 0, 2, 3).sorted(absOrdering)
  List(-4, -3, -2, -1).sorted(absOrdering)
  assert(List(-4, -1, 0, 2, 3).sorted == List(0, -1, 2, 3, -4))
  assert(List(-4, -3, -2, -1).sorted == List(-1, -2, -3, -4))
}

object ord {
  implicit val ord = Ordering.fromLessThan[Int](_ < _)

  List[Int](3, 100, 2, -100, 0, 10).sorted
}
