import cats.Eval

object fold {

  import cats.Eval

  def foldRight[A, B](as: List[A], acc: B)(fn: (A, B) => B): B =
    as match {
      case head :: tail =>
        fn(head, foldRight(tail, acc)(fn))
      case Nil =>
        acc
    }

  def safeFoldRight[A, B](as: List[A], acc: Eval[B])(fn: (A, Eval[B]) => Eval[B]): Eval[B] = {
    as match {
      case head :: tail =>
        Eval.defer(fn(head, safeFoldRight(tail, acc)(fn)))
      case Nil =>
        acc
    }
  }
}

fold.foldRight(List(1,2,4,5,6), 0)(_ + _)
fold.safeFoldRight(List(1,2,4,5,6), Eval.now(0))((a,b) => b.map(_ + a))

object nonBlowUp {

  import cats.Eval

  def odd(n: Int): Eval[String] = Eval.defer {
    even(n - 1)
  }

  def even(n: Int): Eval[String] =
    if (n <= 0) Eval.now("done")
    else Eval.defer(odd(n - 1))

}

object blowUp {

  def odd(n: Int): String = even(n - 1)

  def even(n: Int): String = if (n <= 0) "done" else odd(n - 1)
}

//blowUp.even(10000)
//nonBlowUp.even(1000000000).value

Eval.now(10)
Eval.always(10)
Eval.later(10)
Eval.defer(Eval.now(10))