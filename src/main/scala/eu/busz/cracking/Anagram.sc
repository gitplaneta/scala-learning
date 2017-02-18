import scala.collection.mutable

val map = mutable.Map[Char, Int]()
val c = 'd'

val sc = new java.util.Scanner(System.in);
var a = sc.next();
var b = sc.next();

a.foreach(c =>
  map.get(c) match {
    case Some(v) => map + (c -> (v + 1))
    case None => map + (c -> 1)
  })
b.foreach(c =>
  map.get(c) match {
    case Some(v) => map + (c -> (v - 1))
    case None => map + (c -> -1)
  })
map.foldLeft(0)((acc, ci) => acc + Math.abs(ci._2))