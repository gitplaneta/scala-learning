val a = List("a", "b", "c")
val b = List("d", "e", "f")

val x = a.flatMap(a1 => b.map(a1 + _))
x.mkString(";")


trait Functor[F[_]] {
  def map[A,B](fa: F[A])(f: A => B): F[B]

  def distribute[A,B](fab: F[(A, B)]): (F[A], F[B]) =
    (map(fab)(_._1), map(fab)(_._2))

  def codistribute[A,B](e: Either[F[A], F[B]]): F[Either[A, B]] = e match {
    case Left(fa) => map(fa)(Left(_))
    case Right(fb) => map(fb)(Right(_))
  }
}

object Functor {
  val listFunctor = new Functor[List] {
    def map[A,B](as: List[A])(f: A => B): List[B] = as map f
  }
}

trait Monad[F[_]] extends Functor[F] {
  def unit[A](a: => A): F[A]

  def flatMap[A, B](ma: F[A])(f: A => F[B]): F[B] =
    join(map(ma)(f))

  def map[A, B](ma: F[A])(f: A => B): F[B] =
    flatMap(ma)(a => unit(f(a)))

  def map2[A, B, C](ma: F[A], mb: F[B])(f: (A, B) => C): F[C] =
    flatMap(ma)(a => map(mb)(b => f(a, b)))

  def join[A](mma: F[F[A]]): F[A] = flatMap(mma)(ma => ma)

  def sequence[A](lma: List[F[A]]): F[List[A]] = {
    lma.foldRight(unit(List[A]()))((f,b) => map2(f,b)((x,y) => x :: y))
  }

  def traverse[A,B](la: List[A])(f: A => F[B]): F[List[B]] = {
    la.foldLeft(unit(List[B]()))((flb,a1) => map2(f(a1), flb)(_ :: _))
  }

  //kleisli
  def compose[A,B,C](f: A => F[B], g: B => F[C]): A => F[C] = {
    (a: A) => flatMap(f(a))(g)
  }

  def flatMap2[A, B](ma: F[A])(f: A => F[B]): F[B] = {
    compose((_: Unit) => ma, f)()
  }

}

