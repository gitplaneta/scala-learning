package eu.busz.hackerrank

class Day18 {

  var stack = List[Char]()
  var queue = List[Char]()

  def pushCharacter(ch: Char) = ch :: stack

  def popCharacter() = {
    val res = stack.head
    stack = stack.tail
    res
  }

  def enqueueCharacter(ch: Char) = {
    queue = ??? //List[Char]() :: ch :: queue
  }

  def dequeueCharacter() = ??? //queue.dequeue()

}