import cats.data.Validated
import cats.kernel.Semigroup
import cats.data.Validated._
import cats.syntax
import cats.syntax.semigroup._
import cats.data.Validated._
import cats.syntax.cartesian._
import cats.instances.string._

sealed trait Predicate[E, A] {

  def and(that: Predicate[E, A]): Predicate[E, A] = And(this, that)

  def or(that: Predicate[E, A]): Predicate[E, A] = Or(this, that)


  def apply(a: A)(implicit s: Semigroup[E]): Validated[E, A] = {
    this match {
      case Pure(f) => f(a)
      case And(left, right) => (left(a) |@| right(a)).map((_, _) => a)
      case Or(left, right) =>
        left(a) match {
          case Valid(a1)
          => Valid(a)
          case Invalid(e1) =>
            right(a) match {
              case Valid(a2)
              => Valid(a)
              case Invalid(e2) => Invalid(e1 |+| e2)
            }
        }
    }
  }
}

case class And[E, A](left: Predicate[E, A], right: Predicate[E, A]) extends Predicate[E, A]

case class Or[E, A](left: Predicate[E, A], right: Predicate[E, A]) extends Predicate[E, A]

case class Pure[E, A](f: A => Validated[E, A]) extends Predicate[E, A]


sealed trait Check[E, A, B] {
  def apply(a: A)(implicit s: Semigroup[E]): Validated[E, B]

  def map[C](func: B => C): Check[E, A, C] = Map(this, func)

  def flatMap[C](func: B => Check[E, A, C]): Check[E, A, C] = FlatMap(this, func)

  def andThen[C](that: Check[E, B, C]): Check[E, A, C] = AndThen(this, that)
}

object Check {
  def apply[E, A](pred: Predicate[E, A]): Check[E, A, A] =
    PureCheck(pred)
}

case class PureCheck[E, A](pred: Predicate[E, A]) extends Check[E, A, A] {
  override def apply(a: A)(implicit s: Semigroup[E]): Validated[E, A] = pred(a)
}

case class Map[E, A, B, C](c: Check[E, A, B], f: B => C) extends Check[E, A, C] {
  override def apply(a: A)(implicit s: Semigroup[E]): Validated[E, C] = c(a).map(f)
}

case class FlatMap[E, A, B, C](c: Check[E, A, B], f: B => Check[E, A, C]) extends Check[E, A, C] {
  override def apply(a: A)(implicit s: Semigroup[E]): Validated[E, C] = c(a).withEither(_.flatMap(b => f(b)(a).toEither))
}

case class AndThen[E, A, B, C](left: Check[E, A, B], right: Check[E, B, C]) extends Check[E, A, C] {
  override def apply(a: A)(implicit s: Semigroup[E]) = left(a).withEither(_.flatMap(b => right(b).toEither))
}

val pos = PureCheck(Pure((i: Int) => if (i > 0) Validated.valid(i) else Validated.invalid("Not  positive")))

pos.map(i => i + "dupa")(10)
pos(-10)