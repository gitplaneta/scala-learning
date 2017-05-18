trait Monoid[A] {
  def op(a1: A, a2: A): A
  def zero: A
}

//ex 10.1
val intAddition: Monoid[Int] = new Monoid[Int] {
  override def op(a1: Int, a2: Int): Int = a1+a2
  override def zero: Int = 0
}
val intMultiplication: Monoid[Int]  = new Monoid[Int] {
  override def op(a1: Int, a2: Int): Int = a1*a2
  override def zero: Int = 1
}
val booleanOr: Monoid[Boolean] = new Monoid[Boolean] {
  override def op(a1: Boolean, a2: Boolean): Boolean = a1 || a2
  override def zero: Boolean = false
}
val booleanAnd: Monoid[Boolean] = new Monoid[Boolean] {
  override def op(a1: Boolean, a2: Boolean): Boolean = a1 && a2
  override def zero: Boolean = true
}

//ex 10.2
def optionMonoid[A]: Monoid[Option[A]] = new Monoid[Option[A]] {
  override def op(a1: Option[A], a2: Option[A]): Option[A] = a1.orElse(a2)

  override def zero: Option[A] = Option.empty
}

//ex 10.3
def endoMonoid[A]: Monoid[A => A] = new Monoid[(A) => A] {
  override def op(a1: (A) => A, a2: (A) => A): (A) => A = a1.andThen(a2)

  override def zero: (A) => A = a => a
}

//ex 10.5
def foldMap[A,B](as: List[A], m: Monoid[B])(f: A => B): B = {
  as.map(f).foldLeft(m.zero)(m.op)
}

//ex 10.6

def foldLeft[B](list: List[B])(zero: B)(op: (B,B) => B) = {
  foldMap(list, endoMonoid[B])(op.curried)(zero)
}

//ex 10.7
def foldMapV[A,B](v: IndexedSeq[A], m: Monoid[B])(f: A => B): B = {
  val splitPoint = v.size/2
  if (v.size > 1) {
    val (left, right) = v.splitAt(splitPoint)
    m.op(foldMapV(left, m)(f), foldMapV(right, m)(f))
  }
  else if (v.size == 1) f(v(0))
  else m.zero
}

//ex 10.10
sealed trait WC
case class Stub(chars: String) extends WC
case class Part(lStub: String, words: Int, rStub: String) extends WC

val wcMonoid: Monoid[WC] = new Monoid[WC] {
  override def op(a1: WC, a2: WC): WC = (a1, a2) match {
    case (Stub(a), Stub(b)) => Stub(a +b)
    case (Stub(a), Part(ls, c, rs)) => Part(a + ls, c, rs)
    case (Part(ls, c, rs), Stub(b)) => Part(ls, c, rs + b)
    case (Part(l1, w1, r1), Part(l2, w2, r2)) =>
      Part(l1, w1 + (if ((r1 + l2).isEmpty) 0 else 1) + w2, r2)
  }

  override def zero: WC = Stub("")
}

//10.12
trait Foldable[F[_]] {
  def foldRight[A,B](as: F[A])(z: B)(f: (A,B) => B): B
  def foldLeft[A,B](as: F[A])(z: B)(f: (B,A) => B): B
  def foldMap[A,B](as: F[A])(f: A => B)(mb: Monoid[B]): B = {
    foldLeft(as)(mb.zero)((b,a) => mb.op(b, f(a)))
  }
  def concatenate[A](as: F[A])(m: Monoid[A]): A =
    foldLeft(as)(m.zero)(m.op)
}

object ListFoldable extends Foldable[List] {
  override def foldRight[A, B](as: List[A])(z: B)(f: (A, B) => B) =
    as.foldRight(z)(f)
  override def foldLeft[A, B](as: List[A])(z: B)(f: (B, A) => B) =
    as.foldLeft(z)(f)
  override def foldMap[A, B](as: List[A])(f: A => B)(mb: Monoid[B]): B =
    foldLeft(as)(mb.zero)((b, a) => mb.op(b, f(a)))

}