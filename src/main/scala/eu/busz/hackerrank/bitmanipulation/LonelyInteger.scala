package scala.eu.busz.hackerrank.bitmanipulation

import java.util.Scanner


object LonelyInteger {

  def main(args: Array[String]) {
    val sc = new Scanner(System.in)
    val lines = sc.nextInt()
    var tmpInt = 0

    for (i <- 0 until lines) tmpInt ^= sc.nextInt()

    println(tmpInt)
  }
}
