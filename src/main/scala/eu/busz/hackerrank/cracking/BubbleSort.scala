package scala.eu.busz.hackerrank.cracking

object BubbleSort {

  def main(args: Array[String]) {
    val sc = new java.util.Scanner(System.in);
    var n = sc.nextInt()
    var a = new Array[Int](n)
    for (a_i <- 0 to n - 1) {
      a(a_i) = sc.nextInt()
    }

    var swaps = 0
    var firstElement = 0
    var lastElement = 0
    bubbleSort(a)

    println(s"Array is sorted in $swaps swaps.")
    println(s"First Element: $firstElement")
    println(s"Last Element: $lastElement")

    def bubbleSort(arr: Array[Int]) = {
      for (i <- 0 until arr.length; j <- arr.length - 1 to 1 by -1) if (arr(j) < arr(j - 1)) swap(arr, j, j - 1)
      firstElement = arr(0)
      lastElement = arr.last
    }

    def swap(arr: Array[Int], idx1: Int, idx2: Int): Unit = {
      val tmp = arr(idx1)
      arr(idx1) = arr(idx2)
      arr(idx2) = tmp
      swaps += 1
    }
  }
}
