package eu.busz.coursera.third.week3

/**
  * Created by Radek on 2016-08-29.
  */
class DataParSum {

  def sum(xs: Array[Int]): Int = {
    xs.par.foldLeft(0)(_ + _)
  }

  def max(xs: Array[Int]): Int = {
    def recMax(left: Int, right: Int): Int = {
      if (left + 1 >= right) xs(left)
      else {
        val mid = left + (right - left)/2
        Math.max(recMax(left, mid), recMax(mid, right)) //calc in parallel
      }
    }
    recMax(0, xs.length)
  }

  def max2(xs: Array[Int]): Int = {
   // Array("a", "b").par.fold(0)((count,c) => )
    xs.par.fold(Int.MinValue)(math.max)
  }

}

object Runner extends App {
  println(new DataParSum().sum(Array[Int](1,2,3,4,5,6)))
  println("max: " + new DataParSum().max(Array[Int](1,2,3,4,5,6)))
}