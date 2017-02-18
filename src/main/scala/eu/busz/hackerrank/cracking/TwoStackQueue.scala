package scala.eu.busz.hackerrank.cracking

import java.util.Scanner

import scala.collection.mutable

object TwoStackQueue extends App {

  val queue = new MyQueue[Int]()

  val scan = new Scanner(System.in);
  val n = scan.nextInt();

  for (i <- 0 until n) {
    val operation = scan.nextInt();
    if (operation == 1) { // enqueue
      queue.enqueue(scan.nextInt());
    } else if (operation == 2) { // dequeue
      queue.dequeue();
    } else if (operation == 3) { // print/peek
      println(queue.peek());
    }
  }
  scan.close();

}

class MyQueue[T] {

  //enqueue -> S1.push
  //dequeue -> S1.push to S2 and take head


  var s1 = List[T]()
  var s2 = List[T]()

  def peek(): T = {
    if (s2.isEmpty) {
      while (!s1.isEmpty) {
        s2 = s1.head :: s2
        s1 = s1.tail
      }
    }
    s2.head
  }

  def dequeue(): T = {
    if (s2.isEmpty) {
      while (!s1.isEmpty) {
        s2 = s1.head :: s2
        s1 = s1.tail
      }
    }
    var result = s2.head
    s2 = s2.tail
    result
  }

  def enqueue(element: T) = {
    s1 = element :: s1
  }

}


object RunningMedian extends App {

  val minHeap = new mutable.PriorityQueue[Int]()(Ordering[Int])
  val maxHeap = new mutable.PriorityQueue[Int]()(Ordering[Int].reverse)

  val sc = new java.util.Scanner (System.in);
  var n = sc.nextInt();
  var a = new Array[Int](n);
  for(a_i <- 0 to n-1) {
    a(a_i) = sc.nextInt();
    println(runningMedian(a(a_i)))
  }

  def runningMedian(n: Int): Double = {
    if (maxHeap.length == minHeap.length) {
      maxHeap.enqueue(n)
      minHeap.enqueue(maxHeap.dequeue())
      maxHeap.enqueue(minHeap.dequeue())

      maxHeap.head
    } else {
      maxHeap.enqueue(n)
      minHeap.enqueue(maxHeap.dequeue())

      (maxHeap.head + minHeap.head) / 2.0
    }
  }

}