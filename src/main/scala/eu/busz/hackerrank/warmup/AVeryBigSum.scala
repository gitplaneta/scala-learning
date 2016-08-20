package main.scala.eu.busz.busz.hackerrank.warmup

class AVeryBigSum {

  def main(args: Array[String]) {
    val sc = new java.util.Scanner (System.in);
    var n = sc.nextInt();
    var arr = new Array[Int](n);
    for(arr_i <- 0 to n-1) {
      arr(arr_i) = sc.nextInt();
    }

    var result = BigInt(0)
    arr.foreach(i => result += BigInt(i))
  }

  def diagSum(arr: Array[Int]) = {
    var sum = 0
    //for (i <- 0 until arr.length) sum += arr(i)(i)
  }
}
