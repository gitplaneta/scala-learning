package eu.busz.hackerrank.Day5

import scala.collection.mutable.ArrayBuffer

object Solution {

  def main(args: Array[String]) {
    val sc = new java.util.Scanner (System.in);
    val N = sc.nextInt();
    val list = new ArrayBuffer[String];

    for (n <- 1 to N) {
      val ln = sc.next()
      var first = ""
      var sec = ""

      for (j <- 0 until ln.length)
        if (j % 2 == 0) first += ln(j) else sec += ln(j)
      list += first + " " + sec

    }

    for (str <- list) println(str)
    sc.close()
  }
}