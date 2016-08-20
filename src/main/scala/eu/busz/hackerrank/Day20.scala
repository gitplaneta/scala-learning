package main.scala.eu.busz.busz.hackerrank

object Solution {

  def main(args: Array[String]) {
    val sc = new java.util.Scanner(System.in);
    var n = sc.nextInt();
    var a = new Array[Int](n);
    for (a_i <- 0 to n - 1) {
      a(a_i) = sc.nextInt();
    }

    bubbleSort(a)
  }

  def bubbleSort(arr: Array[Int]) = {
    var globalSwaps = 0
    var nrSwaps = 1
    while (nrSwaps > 0) {
      nrSwaps = 0
      for (i <- 0 until arr.length - 1) {
        if (arr(i) > arr(i + 1)) {
          val tmp = arr(i)
          arr(i) = arr(i + 1)
          arr(i + 1) = tmp
          nrSwaps += 1
          globalSwaps += 1
        }
      }
    }

    var last = arr(arr.length -1)
    var head = arr.head
    println(s"Array is sorted in $globalSwaps swaps.")
    println(s"First Element: $head")
    println(s"Last Element: $last")

    arr
  }
}
