import java.util.Calendar._

import scala.collection.{JavaConversions, SortedMap, mutable}

def ex1(): Map[String, Double] = {
  val gizmos = Map[String, Double]("gizmo1" -> 10.0, "slap" -> 0, "hammer" -> 12.99, "washing powder" -> 10.01)
  gizmos.map({ case (k, v) => (k, v * 0.9) })
}

def ex2() = {
  val wordsCounter = collection.mutable.Map[String, Int]().withDefaultValue(0)
  val in = new java.util.Scanner(new java.io.File("/tmp/myfile.txt"))
  while (in.hasNext) {
    val word = in.next.replaceAll("!|,|\\.|;", "")
    wordsCounter += (word -> (wordsCounter(word) + 1))
  }
  wordsCounter
}

def ex3() = {
  var wordsCounter = collection.immutable.Map[String, Int]().withDefaultValue(0)
  val in = new java.util.Scanner(new java.io.File("/tmp/myfile.txt"))
  while (in.hasNext) {
    val word = in.next.replaceAll("!|,|\\.|;", "")
    wordsCounter = wordsCounter + (word -> (wordsCounter(word) + 1))
  }
  wordsCounter
}

def ex4(): SortedMap[String, Int] = {
  //  ex2().toSeq.sortBy(_._2).reverse
  var sortedMap = SortedMap[String, Int]()
  sortedMap ++= ex3()
  sortedMap
}

def ex5(): java.util.TreeMap[String, Int] = {
  val treeMap = new java.util.TreeMap[String, Int]()
  ex4.foreach(pair => treeMap.put(pair._1, pair._2))

  treeMap
}

def ex6() = {
  mutable.LinkedHashMap("Monday" -> MONDAY, "Tuesday" -> TUESDAY,
    "Wednesday" -> WEDNESDAY, "Thursday" -> THURSDAY, "Friday" -> FRIDAY)
}

def ex7() = {
  val properties = JavaConversions.mapAsScalaMap(System.getProperties)
  val maxLength = properties.keys.map(_.toString).maxBy(_.length).length

  println(s"Max length is $maxLength")
  for (p <- properties) println(p._1 + " -> " + p._2)
}

def ex8_minmax(values: Array[Int]) = {
  (values.min, values.max)
}

def ex9_lteqgt(arr: Array[Int], v: Int) = {
  (arr.count(_ < v), arr.count(_ == v), arr.count(_ > v))
}

def ex10() = {
  "Hello".zip("World")
}


ex1()
ex2()
ex3()
ex4()
ex5()
ex6()
ex7()
ex8_minmax(Array(1, 2, 3, -10, 3, 111, 101, 0))
ex10()