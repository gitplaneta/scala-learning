import scala.collection.mutable

//1 & 2
def strToMap(str: String): Map[Char, List[Int]] = {
  str
    .zipWithIndex
    .foldLeft(Map[Char, List[Int]]().withDefaultValue(List[Int]()))((a, b) => {
      a + (b._1 -> (b._2 :: a(b._1)))
    })
}

Map[Char, Int]() + ('c' -> 0) + ('c' -> 1)
strToMap("Mississippi")

//3
def removeZeroes(list: mutable.LinkedList[Int]): mutable.LinkedList[Int] = {
  list.filter(_ != 0)
}

val mutList = mutable.LinkedList[Int](1,2,3,0,10,44,0,100)
removeZeroes(mutList)

//4
def stringMapper(strings: Array[String], mappings: Map[String, Int]): Array[Int] = {
  strings
    .filter(mappings.contains(_))
    .map(mappings(_))
}

def stringMapper2(strings: Array[String], mappings: Map[String, Int]): Array[Int] = {
  strings.flatMap(mappings.get(_))
}

stringMapper(Array("Tom", "Fred", "Harry"), Map("Tom" -> 3, "Dick" -> 4, "Harry" -> 5)).mkString(",")

"aa".flatMap(a => None)
val y = 0
val x = List[List[Int]]().flatMap(a => a)
x.toString()
"X".flatMap(a => Some(Some("aaa")))

//5
def mkString2[T](list: List[T], op: String): String = {
  list.map(_.toString).reduceLeft((el1, el2) => el1 + op + el2)
}

mkString2(List[Int](1,2,3,4,5), ":")

//6
val lst = List[Int](12, 344, 1, 0, 9)
(List[Int]() :\ lst)(_ +: _)
(List[Int]() :\ lst)((a,b) => a +: b)
(List[Int]() :\ lst)((a,b) => b :+ a)

(List[Int]() :\ lst)((a,b) => b :+ a)

(lst /: List[Int]())(_ :+ _)
(List[Int]() /: lst)((x,y) => y +: x)

//lst.foldRight(List[Int])((a,b) => a +: b)

List(1,2,3,4).foldLeft(List(5,6,7,8))((a,b) => b +: a)
List(1,2,3,4).foldRight(List(5,6,7,8))((a,b) => b :+ a)