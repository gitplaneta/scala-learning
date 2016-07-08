object MergeSort {

  def msort(xs: List[Int])(lt: (Int, Int) => Boolean): List[Int] = {
    def merge(xl: List[Int], yl: List[Int]): List[Int] = (xl, yl) match {
      case (Nil, yl) => yl
      case (xl, Nil) => xl
      case (x :: xf, y :: yf) =>
        if (!lt(x, y)) y :: merge(xl, yf)
        else x :: merge(xf, yl)
    }
    val n = xs.length / 2
    if (n == 0) xs
    else {
      val (left, right) = xs.splitAt(n)
      merge(msort(left)(lt), msort(right)(lt))
    }
  }

  msort(List(1, 43, 10, -300, 1))(_ > _)

}

