package main.scala.eu.busz.busz.hackerrank.day7

object Soultion7 {

  def main(args: Array[String]) {
    val sc = new java.util.Scanner (System.in);
    var n = sc.nextInt();
    var arr = new Array[Int](n);
    for(arr_i <- 0 to n-1) {
      arr(arr_i) = sc.nextInt();
    }

    for (i <- n - 1 to 0 by -1) print(arr(i) + " ")
  }

}
