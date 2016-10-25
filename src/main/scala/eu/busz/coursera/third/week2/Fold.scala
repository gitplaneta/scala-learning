package eu.busz.coursera.third.week2

/**
  * Created by Radek on 2016-08-22.
  */
class Fold {

  def reduceSeq[T](arr: Array[T], f: (T, T) => T): T = {
    def fold(left: Int, right: Int): T = {
      if (right > left) {
        val mid = left + (right - left)/2
        f(fold(left, mid), fold(mid, right))
      } else {
        arr(left)
      }
    }
    fold(0, arr.length)
  }
}
