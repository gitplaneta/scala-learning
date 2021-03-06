import scala.annotation.tailrec

def fib(n: Int): BigInt = {
  @tailrec def fibTail(x: Int, prev: BigInt = 0, next: BigInt = 1): BigInt = {
  x match {
  case 0 => prev
  case 1 => next
  case _ => fibTail (x - 1, next, next + prev)
}
}
  fibTail (n)
}