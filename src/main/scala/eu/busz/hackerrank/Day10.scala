package main.scala.eu.busz.busz.hackerrank

import scala.math.max

object Day10 {

  def main(args: Array[String]): Unit = {
    val sc = new java.util.Scanner(System.in)
    val n = sc.nextInt()
    val binStr = n.toBinaryString

    var ones = 0L
    var tmpOnes = 0L
    for (ch <- binStr)
      if (ch == '1') tmpOnes += 1
      else {
        ones = max(ones, tmpOnes)
        tmpOnes = 0L
      }
  }
}
