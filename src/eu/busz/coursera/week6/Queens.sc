object nqueens {
  def queens(n: Int): Set[List[Int]] = {
    def isSafe(col: Int, qs: List[Int]): Boolean = {
      val row = qs.length
      val queensWithRow = qs zip (row - 1 to 0 by -1)

      queensWithRow forall {
        case (c, r) => c != col && math.abs(col - c) != row - r
      }
    }
    def placeQeens(k: Int): Set[List[Int]] = {
      if (k == 0) Set(List())
      else
        for {
          queens <- placeQeens(k - 1)
          col <- 0 until n
          if isSafe(col, queens)
        } yield col :: queens
    }
    placeQeens(n)
  }

  queens(8)
}
