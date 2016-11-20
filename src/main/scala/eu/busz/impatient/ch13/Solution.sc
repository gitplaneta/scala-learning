import scala.io.Source
import scala.collection.immutable.HashMap

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

//7
val prices = List(1,2,3,4)
val quantities = List(10,20,30,40)


val fun = new ((Int, Int) => Int) {
  def apply(a: Int,b: Int): Int = a * b
}

val fun2 = () => throw new RuntimeException()

val asdf = fun2 _

val xxxxx = fun

val tupFun = ()

val meth3: Tuple3[Int, Int, Int] => String = ((a: Int, b: Int, c: Int)=> "").tupled
val meth2: Tuple2[Int, Int] => String = ((a: Int, b: Int)=> "").tupled

//(prices zip quantities) map { fun.tupled }
(prices zip quantities) map{ ((_: Int) * (_: Int)).tupled }
(prices zip quantities) map( ((a: Int, b: Int) =>  (a*b)).tupled )
//(prices zip quantities).map( ((_: Double) * (_ :Int)).tupled )

//8
def to2DArray(col: Int, arr: Array[Double]): Array[Array[Double]] = {
  arr.grouped(col).toArray
}
to2DArray(3,  Array(1, 2, 3, 4, 5, 6)).map(_.mkString(",")).mkString("\n")

// 9
//Code is not thread safe
//He could replace it with an atomic Objects (with get & set as atomic op) in his collection

// 10
val str = "happy days forever!"
val frequencies = new scala.collection.mutable.HashMap[Char, Int]
for (c <- str.par) frequencies(c) = frequencies.getOrElse(c, 0) + 1
frequencies

def mergeMaps(a: scala.collection.mutable.HashMap[Char, Int], b: scala.collection.mutable.HashMap[Char, Int]): scala.collection.mutable.HashMap[Char, Int] = {
  a.foldLeft(b)( (col, kv) => {
    b(kv._1) = b.getOrElse(kv._1, 0) + kv._2
    b
  })
}

str.par.aggregate(Map[Char, Int]())((f, c) => f + (c -> (f.getOrElse(c, 0) + 1)),
  (m1, m2) => m1 ++ m2.map{case (k,v) => k -> (v + m1.getOrElse(k, 0))})


(new scala.collection.mutable.HashMap[Char, Int]).getOrElse('c', 0) + 1



//4
def mapNames(names: Array[String], nameMap: Map[String, Int]): Array[Int] = {
  names.flatMap(nameMap.get(_))
  //or
  names.map(nameMap.get(_))
    .flatMap(a => a)
}

mapNames(Array("Tom", "Fred", "Harry"), Map("Tom" -> 3, "Dick" -> 4, "Harry" -> 5)).mkString(", ")

//5
def reduceMkString[T](a: Array[T], sep: String): String = {
  a.map(_.toString)
    .reduceLeft(_ + sep + _)
}

reduceMkString(Array(1, 2, 3, 4, 5, 6), ": ")