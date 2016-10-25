import scala.collection.JavaConversions.mapAsScalaMap

val scores = Map("Alice" -> 10)

scores.getOrElse("asdf", 10)

var s = ""
for ((k,v) <- scores) s = k+v
s

val abc: scala.collection.mutable.Map[String, Int] = new java.util.TreeMap[String, Int]