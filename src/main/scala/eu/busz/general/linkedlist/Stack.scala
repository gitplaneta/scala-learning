package eu.busz.general.linkedlist

/**
  * Created by Radek on 2017-01-22.
  */
trait Stack[T] {

  def pop(): T
  def push(t: T)
  def peek(): T
}
