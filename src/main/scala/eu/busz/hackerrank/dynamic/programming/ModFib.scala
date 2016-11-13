package scala.eu.busz.hackerrank.dynamic.programming

import java.util.Scanner

object ModFib {

  def main(args: Array[String]) {
    val sc = new Scanner(System.in);
    val t1 = sc.nextInt
    val t2 = sc.nextInt
    val n = sc.nextInt

    println(fib(t1, t2, n))
  }

  def fib(t1: Int, t2: Int, n: Int): BigInt = {
    val buff = new Array[BigInt](n+1)
    buff(0) = BigInt(0)
    buff(1) = BigInt(t1)
    buff(2) = BigInt(t2)
    def febRec(i: Int): BigInt = {
      if (buff(i) != null) {
        buff(i)
      } else {
        buff(i) = febRec(i - 2) + febRec(i-1) * febRec(i-1)
        buff(i)
      }
    }

    febRec(n)
  }
}
