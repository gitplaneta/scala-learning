def sayHello(implicit name: String) = s"Hello $name"

val nonImplicitName = "nonImplicitName"
sayHello(nonImplicitName)

implicit val implicitName = "implicit name val"
sayHello

//-------------- Implicit params
case class Person(val name: String)
object Person {
  implicit val person: Person = new Person("llllllImplicit user")
  implicit val maybePerson: Option[Person] = Some(Person("maybe user???"))
}

def sayHello2(implicit person: Person): String = s"Hello2 $person"
sayHello2


def sayHello3(implicit person: Option[Person]) = s"Hello maybe $person"
sayHello3

//------------------- Implicit conversions
case class Lady(name: String) {
  def greet: String= s"Helllo $name"
}
object Lady {
  implicit def stringToLady(name: String): Lady = Lady(name)
}
implicit class intToPerson(int: Int) {
  def greetInt: String = s"Hello $int"
}

"greet".greet
val l: Lady = "aaaa"

import Lady.stringToLady

import scala.reflect.ClassTag
"Margot".greet
intToPerson(10)

//---shorthand
case class Child(name: String) {
  def childStr = s"$name"
}
implicit class StringToChild(str: String) {
  def gggg: String = s"Hell named $str"
}
"gowno".gggg
val ic: StringToChild = "asdf"

//-------------- Type erasure:

def toArray[T](t: T)(implicit tag: ClassTag[T]): Array[T] = Array[T](t)
val x = toArray("lalala")

def shortToArray[T: ClassTag](t: T): Array[T] = Array[T](t)

//------------- Existentil types:
def printList(l: List[_]): String = l.foldLeft("")((a: String ,b: Any) => a + b.toString)

//------------- Type classes
trait InfoPrinter[T] {
  def toInfo(value: T):  String
}

object InfoPrinter {
  implicit val stringPrinter = new InfoPrinter[String] {
    override def toInfo(value: String): String = s"[String] --- $value"
  }
  implicit val intPrinter = new InfoPrinter[Int] {
    override def toInfo(value: Int): String = s"[Int] --- $value"
  }
}

implicit class PrettyPrint[T](value: T) {
  def prettyPrint()(implicit infoPrinter: InfoPrinter[T]): String = {
    infoPrinter.toInfo(value) + " ---Converter"
  }
}

"Dupka".prettyPrint()
42.prettyPrint()

val pp: PrettyPrint[String] = "aaaa"