//1
def values(fun: (Int) => Int, low: Int, high: Int) = {
  for (i <- low to high) yield (i, fun(i))
}
values(x => x * x, -5, 5)

//1b
def values2(fun: (Int) => Int, low: Int, high: Int) = {

  (low to high).map(fun(_)).zip(low to high)
  for (i <- low to high) yield (i, fun(i))
}
values2(x => x * x, -5, 10)

//2
val arr = Array[Int](20, 1, -100, 10000, 40)
arr.reduceLeft(Math.max(_,_))

//3
def fact(n: Int) = {
  if (n < 0) 0
  (1 to n).reduceLeft(_ * _)
}
fact(4)

//4
def fact2(n: Int) = {
  (1 to n).foldLeft(1)(_ * _)
}

//5
def largest(fun: (Int) => Int, inputs: Seq[Int]) = {
  inputs.map(fun(_)).max
}
largest(x => 10 * x - x * x, 1 to 10)

//6
