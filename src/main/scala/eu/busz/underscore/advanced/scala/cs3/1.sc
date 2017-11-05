import cats.Semigroup
import cats.syntax.either._
// asLeft and asRight syntax
import cats.syntax.semigroup._

final case class CheckF[E, A](func: A => Either[E, A]) {

  def apply(a: A): Either[E, A] = func(a)

  def and(that: CheckF[E, A])(implicit s: Semigroup[E]): CheckF[E, A] = {
    CheckF(a => {
      (this(a), that(a)) match {
        case (Right(a1), Right(a2)) => a.asRight
        case (Left(l1), Right(a2)) => l1.asLeft
        case (Right(r1), Left(l2)) => l2.asLeft
        case (Left(l1), Left(l2)) => (l1 |+| l2).asLeft
      }
    })
  }
}


import cats.instances.list._ // Semigroup for List
val a: CheckF[List[String], Int] =
  CheckF { v =>
    if(v > 2) v.asRight
    else List("Must be > 2").asLeft
  }
val b: CheckF[List[String], Int] =
  CheckF { v =>
    if(v < -2) v.asRight
    else List("Must be < -2").asLeft
  }
val check = a and b
check(5)
check(0)
