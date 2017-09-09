object exercise2 {
  import cats.Id
  def pure[A](value: A): Id[A] = value

  def map[A, B](initial: Id[A])(func: A => B): Id[B] = func(initial)
  def flatMap[A, B](initial: Id[A])(func: A => Id[B]): Id[B] = func(initial)


  flatMap(3)(_ + 2)
}

object identity {
  import scala.language.higherKinds
  import cats.Monad
  import cats.syntax.functor._
  import cats.syntax.flatMap._

  def sumSquare[M[_]: Monad](a: M[Int], b: M[Int]): M[Int] =
  for {
           x <- a
           y <- b
    } yield x * x + y * y

  import cats.Id

  sumSquare(3: Id[Int],4: Id[Int])


  "Dave" : Id[String]

  def dupa(data: Id[_]): String = ""

  dupa("asdf")

  val m = Monad[Id].pure(3)
  val b = Monad[Id].flatMap(m)(_ + 3)
}

//
//object exercise {
//
//  import cats.Id
//
//  trait Monad[Id[A]] {
//    def pure[A](a: A): Id[A] = a.asInstanceOf[Id[A]]
//
//    def flatMap[A, B](value: Id[A])(func: A => Id[B]): Id[B] = {
//      func(value)
//    }
//
//    def map[A, B](value: Id[A])(func: A => B): Id[B] = {
//      flatMap(value)(func.andThen(pure))
//    }
//  }
//}

