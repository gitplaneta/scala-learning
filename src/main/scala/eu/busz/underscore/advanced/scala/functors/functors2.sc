import cats.Functor
import cats.syntax.functor._
import cats.instances.list._
import cats.instances.option._

import cats.instances.function._

val list = List(1,2,3)
val list2 = Functor[List].map(list)(_ * 3)

val option1 = Option(123)
val option2 = Functor[Option].map(option1)(_.toString)

//lift
val func = (x: Int) => x + 1
val lifted = Functor[Option].lift(func)


lifted(Option(1))

//mapping of functions
val func1 = (a: Int) => a+1
val func2 = (a: Int) => a*2

val func3 = func1.map(func2)

func3(10)

//definign functors:
implicit val optionFunctor = new Functor[Option] {
  override def map[A, B](fa: Option[A])(f: (A) => B): Option[B] = fa.map(f)
}