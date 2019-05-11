
trait Monad[M[_]] {
  def point[A](a: A): M[A]
  def bind[A, B](m: M[A])(f: A => M[B]): M[B]
}


class EitherMonad[A] extends Monad[({type λ[α] = Either[A, α]})#λ] {
  def point[B](b: B): Either[A, B] = ???
  def bind[B, C](m: Either[A, B])(f: B => Either[A, C]): Either[A, C] = ???
}

//new EitherMonad[Int].point("")

//
trait Functor[A, F[_]] {
  def map[B](x: F[A])(f: A => B): F[B]
}

implicit def OptionIsFunctor[A]: Functor[A, Option] = new Functor[A, Option] {
  override def map[B](x: Option[A])(f: A => B) = x.map(f)
}

implicitly[Functor[Int, Option]].map(Option(5))(_ + 10)


def Tuple2FunctorTest[X, A] = {
  type Alias[A] = Tuple2[X, A]
  new Functor[A, Alias] {
    override def map[B](x: Alias[A])(f: A => B): Alias[B] = (x._1, f(x._2))
  }
}

implicit def Tuple2Functor[X, A] = {
  type Alias[A] = Tuple2[X, A]
  new Functor[A, Alias] {
    override def map[B](x: Alias[A])(f: A => B):Alias[B] =  (x._1, f(x._2))
  }
}