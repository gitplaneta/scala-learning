

///////////////



trait Functor[F[_]] {
  def fmap[A, B](f: A => B, a: F[A]): F[B]
}

trait Applicative[F[_]] extends Functor[F] {
  def ap[A, B](f: F[A => B], a: F[A]): F[B]

  def point[A](a: A): F[A]

  override final def fmap[A, B](f: A => B, a: F[A]) =
    ap(point(f), a)
}

def FunctorCompose2[M[_], N[_]](implicit mx: Functor[M], nx: Functor[N]):
Functor[M[N[String]]] = ???

def FunctorCompose[M[_], N[_]](implicit mx: Functor[M], nx: Functor[N]):
  Functor[({type λ[α]=M[N[α]]})#λ] =

new Functor[({type λ[α]=M[N[α]]})#λ] {
  def fmap[A, B](f: A => B, a: M[N[A]]) =
    mx.fmap((na: N[A]) => nx.fmap(f, na), a)

  def fmap2[A, B](f: A => B, a: M[N[A]]) =
    mx.fmap((na: N[A]) => nx.fmap(f, na), a)
}

implicit val lFunctor : Functor[List] = new Functor[List] {
  override def fmap[A, B](f: A => B, a: List[A]): List[B] = a.map(f)
}
implicit val sFunctor : Functor[Set] = new Functor[Set] {
  override def fmap[A, B](f: A => B, a: Set[A]): Set[B] = a.map(f)
}

val x = FunctorCompose(lFunctor, sFunctor)
val y = FunctorCompose(sFunctor, lFunctor)
