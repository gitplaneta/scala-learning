package scala.eu.busz.hackerrank.cracking

object MergeSortInversions {

  def main(args: Array[String]) {
    val sc = new java.util.Scanner (System.in);
    var t = sc.nextInt();
    var a0 = 0;
    while(a0 < t){
      var n = sc.nextInt();
      var arr = new Array[Int](n);
      for(arr_i <- 0 to n-1) {
        arr(arr_i) = sc.nextInt();
      }
      a0+=1;
      println(inversions2(arr))
    }
  }

  //wrong
  def inversions(arr: Array[Int]): Int = {
    val sorted = arr.sorted
    var invs = 0
    for (i <- 0 until arr.length) if (arr(i) != sorted(i)) invs += 1
    invs
  }

  //too slow
  def inversions2(arr: Array[Int]): Int = {
    var invs = 0
    for (i <- 0 until arr.length; j <- i until arr.length) {
      if (arr(i) > arr(j)) invs += 1
    }
    invs
  }

  
}
