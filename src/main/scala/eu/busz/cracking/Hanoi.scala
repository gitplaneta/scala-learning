package scala.eu.busz.cracking
import scala.collection.mutable

object Hanoi extends App {

  val A = mutable.ListBuffer(1,2,3,4,5)
  val B = mutable.ListBuffer[Int]()
  val C = mutable.ListBuffer[Int]()

  def hanoi(source: mutable.ListBuffer[Int], helper: mutable.ListBuffer[Int], target: mutable.ListBuffer[Int], n: Int): Unit = {
    if (n <= 0) {
      return
    }
    hanoi(source, target, helper, n - 1)
    source.head +=: target
    source.remove(0)
    println(s"source : " + A.mkString(", "))
    println(s"helper : " + B.mkString(", "))
    println(s"target : " + C.mkString(", "))
    println("")

    hanoi(helper, source, target, n - 1)

  }

  hanoi(A, B, C, 5)

}
