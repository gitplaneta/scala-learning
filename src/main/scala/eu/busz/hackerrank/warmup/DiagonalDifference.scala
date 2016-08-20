package eu.busz.hackerrank.warmup

object DiagonalDifference {

  def main(args: Array[String]) {
    val sc = new java.util.Scanner (System.in);
    var n = sc.nextInt();
    var a = Array.ofDim[Int](n,n);
    for(a_i <- 0 to n-1) {
      for(a_j <- 0 to n-1){
        a(a_i)(a_j) = sc.nextInt();
      }
    }
    println(diagSum(a))
  }

  def diagSum(arr: Array[Array[Int]]) = {
    var sum = 0
    for (i <- 0 until arr.length) {
      sum += arr(i)(i) - arr(i)(math.abs(i-arr.length+1))
    }
    math.abs(sum)
  }


}
