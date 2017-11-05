import cats.Semigroup
import cats.syntax.either._
import cats.syntax.semigroup._


sealed trait Check[E, A] {

  def apply(a: A)(implicit s: Semigroup[E]): Either[E, A] = {
    this match {
      case Pure(f) => f(a)
      case And(l, r) =>
        (l(a), r(a)) match {
          case (Right(a), Right(a2)) => a.asRight
          case (Left(l1), Right(a2)) => l1.asLeft
          case (Right(r1), Left(l2)) => l2.asLeft
          case (Left(l1), Left(l2)) => (l2 |+| l2).asLeft
        }
    }
  }

  def and(that: Check[E, A])(implicit semigroup: Semigroup[E]): Check[E, A] = {
    And(that, this)
  }
}

case class And[E, A](left: Check[E, A], right: Check[E, A]) extends Check[E, A]
case class Pure[E,A](func: A => Either[E,A]) extends Check[E, A]