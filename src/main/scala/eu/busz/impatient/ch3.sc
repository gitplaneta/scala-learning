import java.awt.datatransfer._
import java.util.TimeZone

import scala.collection.JavaConversions.asScalaBuffer
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object ch3 {

  def ex1(n: Int) = {
    val arr = new Array[Int](n)
    for (i <- 0 until n) arr(i) = (math.random * n).toInt
    arr
  }

  def ex2(a: Array[Int]): Unit = {
    for (i <- 0 until a.size by 2) {
      // a.swap
    }
  }

  def ex5(a: Array[Double]) = {
    a.sum / a.length
  }

  def ex6_a(a: Array[Int]) = a.reverse

  def ex6_b(a: ArrayBuffer[Int]) = a.reverse

  def ex7(a: Array[Int]) = a.distinct

  def ex8(a: ArrayBuffer[Int]): ArrayBuffer[Int] = {
    val neg = (for (i <- a.indices if a(i) < 0) yield i).drop(1)
    val x = for (i <- neg.indices.reverse) a.remove(neg(i))
    a
  }

  def ex9: Array[String] = {
    TimeZone.getAvailableIDs
      .filter(s => s.startsWith("America/"))
      .map(s => s.stripPrefix("America/"))
      .sorted
  }

  def ex10: mutable.Buffer[String] = {
    val flavors = SystemFlavorMap.getDefaultFlavorMap().asInstanceOf[SystemFlavorMap]
    flavors.getNativesForFlavor(DataFlavor.imageFlavor)
  }

  val x = for (a <- 0 until 3) yield 2 * a
  val y = for (a <- 0 until 10 if a > 3) yield a * 10
  (0 until 10).filter(_ > 3).map(_ * 10)

  ex1(10)


  new Array[Int](10)
  val s = Array("Dupa", "kot")
  s(0)

  val ab = new ArrayBuffer[Int]()
  ab += 10 += 11

  ex6_a(Array(1, 2, 3, 4, 5))
  ex6_b(ArrayBuffer(1, 2, 3, 4, 5))
  ex7(Array(1, 1, 3, 1, 2, 4, 4, 4, 5, 2))
  ex8(ArrayBuffer(2, -3, 3, 4, -20, -10, 1))
  ex9
  ex10
}