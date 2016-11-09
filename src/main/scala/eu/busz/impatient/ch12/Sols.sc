def largest(fun: Int => Int, range: Seq[Int]) = {
  range.map(fun(_)).max
}
largest(x => 10 * x - x * x, 1 to 10)

def largestAt(fun: Int => Int, range: Seq[Int]) = {
  range
    .map(fun(_))
    .zip(range)
    .max(Ordering.by((x: (Int, Int)) => x._1))
    ._2
}

def largestAt2(fun: Int => Int, range: Seq[Int]) = {
  range.reduceLeft((x,y) => if (fun(x) > fun(y)) x else y)
}

largestAt(x => 10 * x - x * x, 1 to 10)

//7
def adjustPair(fun: (Int, Int) => Int)(pair: (Int, Int)) = {
  val (l, r) = pair
  fun(l, r)
}
val pairs = (1 to 10) zip (11 to 20)
pairs.map(adjustPair(_+_))

//8
val a = Array("Hello", "World")
val b = Array(5, 5)
a.corresponds(b)(_.length == _)

//9
def corresponds[A,B](thiss: Seq[A], that: Seq[B], p: (A,B) => Boolean): Boolean = {
  val i = thiss.iterator
  val j = that.iterator
  while (i.hasNext && j.hasNext)
    if (!p(i.next(), j.next()))
      return false

  !i.hasNext && !j.hasNext
}

corresponds[String, Int](a,b, _.length == _)

//10
def unless(cond: Boolean)(block: => Unit) = if (!cond) block

unless(true) {
  println("")
}
unless(true)(() => println("asdf"))