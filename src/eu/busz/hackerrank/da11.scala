package eu.busz.hackerrank

import java.util.Scanner

object da11 {

  def sumHoughGlass(i: Int, j: Int, arr: Array[Array[Int]]): Int = {
    val mid = arr(j+1)(i+1)
    val up = arr(j)(i) + arr(j)(i+1) + arr(j)(i+2)
    val dwn = arr(j+2)(i) + arr(j+2)(i+1) + arr(j+2)(i+2)

    up + mid + dwn
  }

  def main(args: Array[String]) = {
    val sc = new Scanner(System.in)
    val arr = Array.ofDim[Int](6, 6)
    for (i <- 0 until 6; j <- 0 until 6) arr(i)(j) = sc.nextInt()

    var sum = Int.MinValue
    for (i <- 0 to 3; j <- 0 to 3) {
      sum = math.max(sumHoughGlass(i, j, arr), sum)
    }

    println(sum)
  }
}
