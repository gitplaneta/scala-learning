package scala.eu.busz.fru

object WikiRoom extends App {
  //12:00

  println(paint(Array(5, 8)))
  println(paint(Array(1,3,2,1,2,1,5,3,3,4,2)))

  println("Decimal Zip:")
  println(decimalZip(12, 56))
  println(decimalZip(12345, 678))
  println(decimalZip(123, 67890))
  println(decimalZip(1234, 0))

  println("Elevator:")
  println(elevator(Array(60, 80, 40), Array(2,3,5), 5, 2, 200)) //5
  println(elevator(Array(40, 40, 100, 80, 20), Array(3, 3, 2, 2, 3), 3, 5, 200)) //6

  def paint(A: Array[Int]): Int = {
    //if first element counter += h
    //if higher than prev counter +=(h(n-1) - h(n))
    //if counter > 1.000.000.000 return -1
    var counter: Long = 0
    for (i <- 0 until A.length) {
      if (i == 0) counter = A(i)
      else if (A(i) > A(i - 1)) {
        counter += A(i) - A(i - 1)
      }

      if (counter > 1000000000) return -1
    }
    counter.toInt
  }

  def decimalZip(A: Int, B: Int): Int = {
    var res: String = ""
    val Astr = A.toString
    val Bstr = B.toString

    for (i: Int <- 0 until Math.max(Astr.length, Bstr.length)) {
      if (i < Astr.length) res += Astr.charAt(i)
      if (i < Bstr.length) res += Bstr.charAt(i)
    }
    if (res.length > 9) return -1

    val intRes = Integer.parseInt(res)
    if (intRes > 100000000) return -1
    return intRes
  }

  def elevator(A: Array[Int], B: Array[Int], M: Int, X: Int, Y: Int): Int = {
    //A - person weight
    //B - target floor
    //X - capacity
    //Y - weight limit
    //M - num of floors

    //iterate over i <- peopleNum -> sum weight && people until X = Max or Y = Max
    //if can't fit single person, return current counter
    // when maxed -> count unique floors + 1
    val N = A.length
    var counter = 0
    var weightAcc = 0
    var peopleCount = 0
    var uniqueFloors = Set[Int]()

    if (M == 0) return 0
    for (i <- 0 until N) {
      if (weightAcc > Y || peopleCount > X) return counter //someone is too fat or elevator too small
      if (weightAcc + A(i) > Y || peopleCount + 1 > X) { //capacity reached
        counter += uniqueFloors.size + 1

        uniqueFloors = Set[Int]()
        weightAcc = 0
        peopleCount = 0
      }

      peopleCount += 1
      weightAcc += A(i)
      uniqueFloors = uniqueFloors + B(i)
    }
    counter + (uniqueFloors.size + 1)
  }
}
