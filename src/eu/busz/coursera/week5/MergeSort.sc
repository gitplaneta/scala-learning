import math.Ordering

object MergeSort {

  def msort[T](xs: List[T])(implicit ord: Ordering[T]): List[T] = {
    def merge(xl: List[T], yl: List[T]): List[T] = (xl, yl) match {
      case (Nil, yl) => yl
      case (xl, Nil) => xl
      case (x :: xf, y :: yf) =>
        if (!ord.lt(x, y)) y :: merge(xl, yf)
        else x :: merge(xf, yl)
    }
    val n = xs.length / 2
    if (n == 0) xs
    else {
      val (left, right) = xs.splitAt(n)
      merge(msort(left)(ord), msort(right)(ord))
    }
  }

  msort(List(1, 43, 10, -300, 1, -1000))

}

