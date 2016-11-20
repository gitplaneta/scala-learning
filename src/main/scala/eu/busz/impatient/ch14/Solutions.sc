
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

case class Multiple(num: Int, art: Item) extends Item

case class Article(name: String, price: Double) extends Item

Multiple(10, Article("Blackwell Toaster", 29.95))

//5
case class Bundles(name: String, discount: Double, item: Item*) extends Item {
  def price(it: Item): Double = it match {
    case Article(s, p) => p
    case Multiple(n, it2) => n * price(it2)
    case Bundles(_, dis, i@_*) => i.map(price(_)).sum - dis
    case _ => 0
  }
}

//6
sealed abstract class BinaryTree
case class Leaf(value: Int) extends BinaryTree
case class Node(left: BinaryTree, right: BinaryTree) extends BinaryTree
case class MultiNode(node: List[BinaryTree]) extends BinaryTree
case class MultiOpNode(nodes: List[BinaryTree], op: String) extends BinaryTree

def binSum(bt: BinaryTree): Int = {
  bt match {
    case Leaf(v) => v
    case Node(l, r) => binSum(l) + binSum(r)
    case _ => 0
  }
}

//7
def multiSum(bt: BinaryTree): Int = {
  bt match {
  case Leaf(v) => v
  case Node(l, r) => multiSum(l) + multiSum(r)
  case MultiNode(n :: tail) => multiSum(n) + multiSum(MultiNode(tail))
  case _ => 0
  }
}

//8
def multiOpEval(bt: BinaryTree): Double = {
  bt match {
    case Leaf(v) => v
//    case Node(l, r) => multiSum(l) + multiSum(r)
//    case MultiNode(n :: tail) => multiSum(n) + multiSum(MultiNode(tail))
    case MultiOpNode(nodes, "+") => nodes.map(multiOpEval(_)).sum
    case MultiOpNode(nodes, "-") => - nodes.map(multiOpEval(_)).sum
    case MultiOpNode(nodes, "*") => nodes.map(multiOpEval(_)).product
    case MultiOpNode(nodes, "/") => nodes.map(multiOpEval(_)).reduce(_ / _)
    case _ => 0
  }
}

//9
def sumOpt(opts: List[Option[Int]]): Int = {
  opts.flatMap(o => o).sum
}

val listOpt = List[Option[Int]](Some(1), None, Some(10), Some(11))
val res9 = sumOpt(listOpt)

//10
def compose(left: Double => Option[Double], right: Double => Option[Double]): Double => Option[Double] = {
  (x: Double) => left(x) match {
    case Some(x) => right(x)
    case None => None
  }

}