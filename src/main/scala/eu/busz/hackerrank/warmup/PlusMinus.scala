package eu.busz.hackerrank.warmup

object PlusMinus {

  def main(args: Array[String]) {
    val sc = new java.util.Scanner (System.in);
    var n = sc.nextInt();
    var arr = new Array[Int](n);
    for(arr_i <- 0 to n-1) {
      arr(arr_i) = sc.nextInt();
    }

    val pos = arr.filter(_ > 0).length / arr.length.toDouble
    val neg = arr.filter(_ < 0).length / arr.length.toDouble
    val zero = arr.filter(_ == 0).length / arr.length.toDouble

    println(pos)
    println(neg)
    println(zero)
  }


}
