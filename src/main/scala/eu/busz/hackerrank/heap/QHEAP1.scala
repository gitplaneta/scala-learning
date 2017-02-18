package scala.eu.busz.hackerrank.heap

import scala.collection.mutable

object QHEAP1 extends App {
  val sc = new java.util.Scanner(System.in)
  val q = sc.nextInt
  val pq = scala.collection.mutable.PriorityQueue[Int]().reverse
  val input = (0 until q)
    .map(_ => {
      var arg: Int = 0
      val cmd = sc.nextInt
      if (cmd != 3) arg = sc.nextInt
      (cmd, arg)
    })
  input.foreach { case (cmd, arg) => {
    if (cmd == 3) println(pq.dequeue())
    else if (cmd == 2) pq.filterNot(_ == arg)
    else if (cmd == 1) pq.enqueue(arg)
  }
  }

}
