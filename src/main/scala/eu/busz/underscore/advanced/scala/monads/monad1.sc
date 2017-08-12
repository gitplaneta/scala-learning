object monadwrappedcalc {
  import scala.language.higherKinds
  import cats.Monad
  import cats.syntax.functor._
  import cats.syntax.flatMap._

  def sumSquare[M[_] : Monad](a: M[Int], b: M[Int]): M[Int] = {

    //(implicit Monad[M[_]]
    a.flatMap(x => b.map(y => x * x + y * y))
  }

  def sumSquare2[M[_]: Monad](a: M[Int], b: M[Int]): M[Int] =
    for {
      x <- a
      y <- b
    } yield x * x + y * y

  def sumSquare3[M[_]](a: M[Int], b: M[Int])(implicit im: Monad[M[Int]]): M[Int] = {
    a.flatMap(x => b.map(y => x*x + y*y))
  }

  import cats.instances.option._

  sumSquare(Option(4), Option(10))

  sumSquare(1,2)
}

object monadsyntax {
  import cats.syntax.applicative._
  import cats.instances.option._
  import cats.instances.list._

  1.pure[Option]

  1.pure[List]
}


object catFutureMonad {
  import cats.instances.future._
  import cats.Monad
  import scala.concurrent._
  import scala.concurrent.duration._
  import scala.concurrent.ExecutionContext.Implicits.global

  val fm = Monad[Future]
}

object catMonad{
  import cats.Monad
  import cats.instances.option._
  import cats.instances.list._

  val opt1 = Monad[Option].pure(3)

  val opt2 = Monad[Option].flatMap(opt1)(a => Some(a + 2))

  val opt3 = Monad[Option].map(opt2)(_ * 100)

  val list1 = Monad[List].pure(3)

  val list2 = Monad[List].flatMap(List(1,2,3))(a => List(a, a*10))

  val list3 = Monad[List].map(list2)(_ + 123)
}

object monadFunctor {

  import scala.language.higherKinds

  trait Monad[F[_]] {
    def pure[A](a: A): F[A]

    def flatMap[A, B](value: F[A])(func: A => F[B]): F[B]

    def map[A, B](value: F[A])(func: A => B): F[B] = {
      flatMap(value)(func.andThen(pure))
    }
  }

}