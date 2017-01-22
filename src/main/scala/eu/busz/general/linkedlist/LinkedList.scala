package eu.busz.general.linkedlist

/**
  * Created by Radek on 2017-01-22.
  */


trait LinkedList[T]
case class List[T](next: LinkedList[T], data: T) extends LinkedList[T]
case class NilEl[T]() extends LinkedList[T]

class LinkedListStack[T] extends Stack[T] {

  private var li: LinkedList[T] = new NilEl

  override def pop(): T = li match {
    case NilEl() => throw new IllegalStateException
    case l @ List(next, data: T) => {
      li = l.next
      l.data
    }
  }

  override def peek(): T = li match {
    case NilEl() => throw new IllegalStateException
    case List(_, data) => data
  }

  override def push(t: T): Unit = {
    li = List(li, t)
  }
}
