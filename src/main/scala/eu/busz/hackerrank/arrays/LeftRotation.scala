package scala.eu.busz.hackerrank.arrays

import java.util.Scanner

//https://www.hackerrank.com/challenges/array-left-rotation
object LeftRotation extends App {

  def rotate(n: Int, d: Int, arr: Array[Int]): Array[Int] = {
    val shift = n % d

    (for (i <- d until (d + n)) yield arr(i % n)).toArray
  }

  val sc = new Scanner(System.in)
  val n = sc.nextInt
  val d = sc.nextInt

  val a = for (i <- 0 until n) yield sc.nextInt

  rotate(n, d, a.toArray).foreach(el => print(el + " "))
}
