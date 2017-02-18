import scala.None
import scala.annotation.tailrec

class MyQueue[T] {

  private var f: List[T] = List[T]()
  private var s: List[T] = List[T]()

  def enqueue(e: T) = ???

  def dequeue(): T = ???

}

case class Node[T](data: T, var next: Node[T], var prev: Node[T], var child: Option[Node[T]]) {

}

def flatten[T](n: Node[T]): Node[T] = {
  val flatChild = n.child match {
    case Some(ch) =>
    case None => n
  }
}

/*
unflaten() = {
if (n.child != null) {
  unflatten(n.child)
  n.child.prev.next = null
  n.child.prev = null
}

if (n.next != null) unflatten(n.next)

}

 */

def isCyclic(n: Node[Int]): Boolean = {

  @tailrec
  def traverse(s: Node[Int], f: Node[Int]): Boolean = {
    if (s.next == null || f.next == null || f.next.next == null) false
    else if (s == f) true
    else {
      traverse(s.next, f.next.next)
    }
  }
  traverse(n, n)
}