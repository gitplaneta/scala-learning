package eu.busz.hackerrank.day25

import java.util.Scanner

object Solution {

  def main(args: Array[String]) {
    val sc = new Scanner(System.in)
    val t = sc.nextInt()
    val input = new Array[Int](t)
    for (i <- 0 until t) input(i) = sc.nextInt()

    if (input.length > 0) {
      val notPrimes = notPrimeSieve(input.max)
      for (value <- input) {
        if (notPrimes(value)) println("Not prime")
        else println("Prime")
      }
    }
  }

  def notPrimeSieve(maxVal: Int) = {
    val notPrime = new Array[Boolean](maxVal + 1)
    notPrime(0) = true
    notPrime(1) = true
    for (i <- 2 to Math.sqrt(notPrime.length).toInt) {
      if (notPrime(i) == false) {
        for (j <- i*i to maxVal by i) notPrime(j) = true
      }
    }
    notPrime
  }
}
