import cats.Semigroup
import cats.data.{Validated, ValidatedNel}
import cats.syntax.semigroup._
import cats.syntax.cartesian._
import cats.data.Validated._
// Valid and Invalid
import cats.Monoid
import cats.instances.boolean._
import cats.instances.int._
import cats.instances.list._
import cats.instances.string._
import cats.syntax.cartesian._

sealed trait Check[E, A] {

  def apply(a: A)(implicit s: Semigroup[E]): Validated[E, A] = {
    this match {
      case Pure(f) => f(a)
      case And(l, r) => (l(a) |@| r(a)).map((_,_) => a)
      case Or(l, r) => l(a) match {
        case Valid(r1) => Valid(r1)
        case Invalid(e1) => r(a) match {
          case Valid(r2) => Valid(r2)
          case Invalid(e2) => Invalid(e1 |+| e2)
        }
      }
    }
  }

  def and(that: Check[E, A])(implicit semigroup: Semigroup[E]): Check[E, A] = {
    And(that, this)
  }
}

case class And[E, A](left: Check[E, A], right: Check[E, A]) extends Check[E, A]
case class Or[E, A](left: Check[E, A], right: Check[E, A]) extends Check[E, A]
case class Pure[E,A](func: A => Validated[E,A]) extends Check[E, A]

val positive: Check[List[String], Int] = Pure(i => if (i > 0) Valid(i) else Invalid(List(s"$i is not positive")))
val negative: Check[List[String], Int] = Pure(i => if (i > 0) Valid(i) else Invalid(List(s"$i is not negative")))

positive(-10)

val x = (And(positive, negative))

x(-1)