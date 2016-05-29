import scala.collection.mutable.ArrayBuffer

object ch3 {

  def ex1(n: Int) = {
    val arr = new Array[Int](n)
    for (i <- 0 until n) arr(i) = (math.random * n).toInt
    arr
  }

  def ex2(a: Array[Int]): Array[Int] = {
    for (i <- 0 until a.size by 2) {
      a.swa
    }
  }

  val x = for(a <- 0 until 3) yield 2 * a
  val y = for(a <- 0 until 10 if a > 3) yield a * 10
  (0 until 10).filter(_ > 3).map(_ * 10)

  ex1(10)


  new Array[Int](10)
  val s = Array("Dupa", "kot")
  s(0)

  val ab = new ArrayBuffer[Int]()
  ab += 10 += 11

}