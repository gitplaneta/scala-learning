def fixedPoint(f: Double => Double): Int = {
  var v = 1.0
  for (i <- 0 until 100) {
    v = f(v)
    println(f(v))
  }

  1
}


fixedPoint(x => 2/x)