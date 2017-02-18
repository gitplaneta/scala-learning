package scala.eu.busz.codility

object Solution extends App {
  def solution(a: Array[Int]): Int = {
    // write your code in Scala 2.12
    var rightSum: Long = a.foldLeft(0L)(_ + _)
    var leftSum: Long = 0
    for (i <- 0 until a.length) {
      rightSum -= a(i)
      if (rightSum == leftSum) return i
      leftSum += a(i)
    }
    -1
  }

  println(solution(Array(1082132608, 0, 1082132608)))
}