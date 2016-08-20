package scala.eu.busz.coursera.third.week2

object runner extends App {
  val sorter = new MergeSort()
  val res = sorter.parallelMergeSort(Array(2000, -1, 44, 10, 101, 69, 444, 1, -2222222), 0)
  print(res.mkString)
}

class MergeSort {

  def parallelMergeSort(src: Array[Int], maxDepth: Int): Array[Int] = {
    val dst = new Array[Int](src.length)
    def mergeSort(left: Int, right: Int, depth: Int): Unit = {
      println(s"mergeSort $left $right")
      val mid = (left + right) / 2

      if (left < right) {
        if (false) //depth >= maxDepth)
          parallel(mergeSort(left, mid, depth + 1), mergeSort(mid + 1, right, depth + 1))
        else {
          mergeSort(left, mid, depth + 1)
          mergeSort(mid + 1, right, depth + 1)
        }
        println(s"Pre-Merge: $left $mid $right arr:" + src.slice(left, right).mkString(", "))
        merge(left, mid, right)
        println(s"Post-Merge: $left $mid $right arr:" + src.slice(left, right).mkString(", "))
        println("\n")
      }
    }

    def merge(left: Int, mid: Int, right: Int) = {
      var i = left
      var l = left
      var r = mid
      while (i < right && l <= mid || r < right) {
        if (r >= right || l <= mid && src(l) < src(r)) {
          dst(i) = src(l)
          l += 1
        } else {
          dst(i) = src(r)
          r += 1
        }
        i += 1
      }
      if (right - left > 0) Array.copy(dst, left, src, left, right - left)
    }

    mergeSort(0, src.length - 1, 0)
    dst
  }


}
