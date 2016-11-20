
//2
def swap(a: (Int, Int)): (Int, Int) = {
  a match {
    case (a, b) => (b, a)
  }
}

swap(10, 20)

//3
def swap2(a: Array[Int]): Array[Int] = {
  a match {
    case Array(a, b, p@_*) => Array(b, a) ++ p
  }
}
swap2(Array[Int](1, 2, 3, 4, 5)).mkString(", ")

//6
def leafSum(treeElements: List[Any]): Int = treeElements match {
  case List(x: Int, r@_*) => x + leafSum(r.toList)
  case List(x: List[Any], r@_*) => leafSum(x) + leafSum(r.toList)
  case _ => 0
}

//4
sealed abstract class Item

case class Multiple(num: Int, art: Item)

case class Article(name: String, price: Double) extends Item

Multiple(10, Article("Blackwell Toaster", 29.95))

//5
case class Bundles(name: String, discount: Double, item: Item*) {
  def price(it: Item): Double = it match {
    case Article(s, p) => p
    case Multiple(n, it) => n * price(it)
    case Bundles(_, dis, i@_*) => i.map(price(_)).sum - dis
  }
}

