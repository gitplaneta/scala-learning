import cats.Semigroup
import cats.data.{Kleisli, NonEmptyList, Validated}
import cats.data.Validated._
import cats.syntax.cartesian._
import cats.syntax.semigroup._
import cats.syntax.validated._
import cats.instances.either._
// Valid and Invalid patterns
type Errors = NonEmptyList[String]
type Result[A] = Either[Errors, A]
type Check[A, B] = Kleisli[Result, A, B]
// Create a check from a function:
def check[A, B](func: A => Result[B]): Check[A, B] =
  Kleisli(func)
// Create a check from a Predicate:
def checkPred[A](pred: Predicate[Errors, A]): Check[A, A] =
  Kleisli[Result, A, A](pred.run)


sealed trait Predicate[E, A] {

  import Predicate._

  def and(that: Predicate[E, A]): Predicate[E, A] = And(this, that)

  def or(that: Predicate[E, A]): Predicate[E, A] = Or(this, that)

  def apply(a: A)(implicit s: Semigroup[E]): Validated[E, A] =
    this match {
      case Pure(func) =>
        func(a)
      case And(left, right) =>
        (left(a) |@| right(a)).map((_, _) => a)
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

  def run(implicit s: Semigroup[E]): A => Either[E, A] = a => this(a).toEither
}

object Predicate {

  final case class And[E, A](left: Predicate[E, A], right: Predicate[E, A]) extends Predicate[E, A]

  final case class Or[E, A](left: Predicate[E, A], right: Predicate[E, A]) extends Predicate[E, A]

  final case class Pure[E, A](func: A => Validated[E, A]) extends Predicate[E, A]

  def apply[E, A](f: A => Validated[E, A]): Predicate[E, A] = Pure(f)

  def lift[E, A](error: E, func: A => Boolean): Predicate[E, A] = Pure(a => if (func(a)) a.valid else error.invalid)
}

//sealed trait Check[E, A, B] {
//  import Check._
//
//  def apply(in: A)(implicit s: Semigroup[E]): Validated[E, B]
//  def map[C](f: B => C): Check[E, A, C] = Map[E, A, B, C](this, f)
//  def flatMap[C](f: B => Check[E, A, C]) = FlatMap[E, A, B, C](this, f)
//  def andThen[C](next: Check[E, B, C]): Check[E, A, C] = AndThen[E, A, B, C](this, next)
//}
//
//object Check {
//
//  final case class Map[E, A, B, C](check: Check[E, A, B], func: B => C) extends Check[E, A, C] {
//    def apply(a: A)(implicit s: Semigroup[E]): Validated[E, C] =
//      check(a) map func
//  }
//
//  final case class FlatMap[E, A, B, C](check: Check[E, A, B], func: B => Check[E, A, C]) extends Check[E, A, C] {
//    def apply(a: A)(implicit s: Semigroup[E]): Validated[E, C] =
//      check(a).withEither(_.flatMap(b => func(b)(a).toEither))
//  }
//
//  final case class AndThen[E, A, B, C](check: Check[E, A, B], next: Check[E, B, C]) extends Check[E, A, C] {
//    def apply(a: A)(implicit s: Semigroup[E]): Validated[E, C] =
//      check(a).withEither {
//        _.flatMap(b => next(b).toEither)
//      }
//  }
//
//  final case class Pure[E, A, B](func: A => Validated[E, B]) extends Check[E, A, B] {
//    def apply(a: A)(implicit s: Semigroup[E]): Validated[E, B] =
//      func(a)
//  }
//
//  final case class PurePredicate[E, A](pred: Predicate[E, A]) extends Check[E, A, A] {
//    def apply(a: A)(implicit s: Semigroup[E]): Validated[E, A] =
//      pred(a)
//  }
//
//  def apply[E, A](pred: Predicate[E, A]): Check[E, A, A] = PurePredicate(pred)
//
//  def apply[E, A, B](func: A => Validated[E, B]): Check[E, A, B] = Pure(func)
//}

//--------------------------------
import cats.data.{NonEmptyList, OneAnd, Validated}
import cats.instances.list._
import cats.syntax.cartesian._
import cats.syntax.validated._
import cats.syntax.either._
def error(s: String): NonEmptyList[String] =
  NonEmptyList(s, Nil)
def longerThan(n: Int): Predicate[Errors, String] =
  Predicate.lift(
    error(s"Must be longer than $n characters"),
    str => str.size > n)
val alphanumeric: Predicate[Errors, String] =
  Predicate.lift(
    error(s"Must be all alphanumeric characters"),
    str => str.forall(_.isLetterOrDigit))
def contains(char: Char): Predicate[Errors, String] =
  Predicate.lift(
    error(s"Must contain the character $char"),
    str => str.contains(char))
def containsOnce(char: Char): Predicate[Errors, String] =
  Predicate.lift(
    error(s"Must contain the character $char only once"),
    str => str.filter(c => c == char).size == 1)

val splitEmail: Check[String, (String, String)] = check(a => a.split('@') match {
  case Array(left, right) => (left, right).asRight
  case _ => error("Could not split string").asLeft
})

val checkLeft: Check[String, String] = checkPred(longerThan(0))
val checkRight: Check[String, String] = check((longerThan(3) and contains('.')).run)

val joinEmail: Check[(String, String), String] = check[(String, String), String] {
  case (l, r) => (checkLeft(l) |@| checkRight(r)).map(_+"@"+_)
}

val checkEmail = splitEmail andThen joinEmail
//----------------------

val checkUsername: Check[String, String] = checkPred(longerThan(3) and alphanumeric)
checkUsername("Nick1")
checkUsername("<-=")

checkEmail("rad@rad.com")
checkEmail("rad")
