val list1: List[Int] = List(1, 2, 3, 4, 5)
val list2: List[Int] = List(6, 7, 8, 9, 10)
val seq1: Seq[Int] = Seq(11, 12, 13)

list1 :: list2
list1 :+ list2

list1 ::: list2
list1 ++: list2

seq1 :+ 0
0 +: seq1

//adding elements
list1 :+ 0
0 +: list1
list1.+("")

//Set Map
val set1 = Set(1,2,3,4)
val set2 = Set(2,4)
set1 ++ Set(10,11,12) //add coll
set1 + 1
set1 - 1 //rem element
set1 - (1,2,3,4,5)

set1 & set2
set1 | set2
set1 &~ set2


val map1 = Map[String, String]("a" -> "A", "b" -> "B")

map1 + ("c" -> "C")
map1 - ("a")

Map[String, String]("c" -> "C") ++ map1

//mutable
var ab = scala.collection.mutable.ArrayBuffer[Int](10, 11, 12, 13)

ab - 10 //new coll
ab -= 10 //mutates