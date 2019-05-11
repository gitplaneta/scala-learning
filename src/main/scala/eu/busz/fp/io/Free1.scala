package eu.busz.fp.io


object freeMonadzzz extends App {

  sealed trait Free[F[_],A] {
    def flatMap[B](f: A => Free[F,B]): Free[F,B] =
      FlatMap(this, f)
    def map[B](f: A => B): Free[F,B] =
      flatMap(f andThen (Return(_)))
  }
  case class Return[F[_],A](a: A) extends Free[F, A]
  case class Suspend[F[_],A](s: F[A]) extends Free[F, A]
  case class FlatMap[F[_],A,B](s: Free[F, A],
                               f: A => Free[F, B]) extends Free[F, B]


  def freeMonad[F[_]]: Monad[({type f[a] = Free[F,a]})#f] = { //Monad[Free[F, ?]]
    new Monad[({type f[a] = Free[F,a]})#f] {
      override def flatMap[A, B](a: Free[F, A])(f: A => Free[F, B]): Free[F, B] = a.flatMap(f)

      override def unit[A](a: => A): Free[F, A] = Return(a)
    }
  }

  @annotation.tailrec
  def runTrampoline[A, B](a: Free[Function0,A]): A = a match {
    case Return(a: A) => a
    case Suspend(x) => x()
    case FlatMap(x: A, f: (A => Free[Function0,B])) => x match {
      case Return(x2: A) => runTrampoline(f(x2))
      case Suspend(x2) => runTrampoline(f(x2()))
      case FlatMap(a0,g) => ??? // runTrampoline { a0 flatMap { a0 => g(a0) flatMap f } }
    }
  }

}


object AnotherFreething extends App {
  trait ~>[F[_], G[_]] {
    def apply[A](f: F[A]): G[A]
  }

  trait Dupa[F[_], G[_], H[_]] {
    def apply[A](f: F[A]): G[A]
  }


  val y: List ~> Set = ???
//  val y2: Set Dupa List = ???

  val x = new ~>[Set, List] {
//    override def apply[A](f: String): Int[A] = ???
    override def apply[A](f: Set[A]): List[A] = ???
  }
}