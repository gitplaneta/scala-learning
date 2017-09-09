import cats.data.OptionT
import cats.Monad
import cats.instances.list._
import cats.syntax.applicative._

object ex3 {
  import scala.concurrent.Future
  import cats.data.{EitherT, OptionT}
  import scala.concurrent.ExecutionContext.Implicits.global
  import cats.instances.future._

  type Error = String
  type FutureEither[A] = EitherT[Future, String, A]
  type FutureEitherOption[A] = OptionT[FutureEither, A]

  val answer: FutureEitherOption[Int] = for {
    a <- 20.pure[FutureEitherOption]
    b <- 30.pure[FutureEitherOption]
  } yield a+b

  answer.value.value.value
}

type ListOption[A] = OptionT[List, A]
val a = 10.pure[ListOption]

val result: ListOption[Int] = 42.pure[ListOption]
OptionT[List, Int](List[Option[Int]]())
//40.pure[OptionT[List[Int], Int]]
OptionT.pure[List, Int](0)

val b = 20.pure[ListOption]
val c: ListOption[Int] = OptionT[List, Int](List(None))

a.flatMap(a => b)
"res:"
for {
  a1 <- a
  c1 <- c
  b1 <- b
} yield b1 + a1 + c1

object ex2{
  import cats.data.OptionT
  import cats.instances.either._
  import cats.syntax.applicative._

  type Error = String
  type EitherOr[A] = Either[Error, A]
  type EitherOptionOr[A] = OptionT[EitherOr, A]
  val result1 = 41.pure[EitherOptionOr]
  val result2 = result1.flatMap(x => (x+1).pure[EitherOptionOr])
}

object ex4 {

  import cats.data.Writer

  type Logged[A] = Writer[List[String], A]
  // Example method that returns nested monads:
  def parseNumber(str: String): Logged[Option[Int]] =
    util.Try(str.toInt).toOption match {

      case Some(num) => Writer(List(s"Read $str"), Some(num))
      case None
      => Writer(List(s"Failed on $str"), None)
    }
  // Example combining multiple calls to parseNumber:
  def addNumbers(
                  a: String,
                  b: String,
                  c: String
                ): Logged[Option[Int]] = {
    import cats.data.OptionT
    // Transform the incoming stacks to work on them:
    val result = for {
      a <- OptionT(parseNumber(a))
      b <- OptionT(parseNumber(b))
      c <- OptionT(parseNumber(c))
    } yield a + b + c
    // Return the untransformed monad stack:
    result.value
  }
  // This approach doesn't force OptionT on other users' code:
  val result1 = addNumbers("1", "2", "3")
  val result2 = addNumbers("1", "a", "3")
}