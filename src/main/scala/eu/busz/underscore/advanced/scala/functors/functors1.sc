object weee_treee {

  import cats.Functor
  import cats.syntax.functor._

  sealed trait Tree[+A]

  final case class Branch[A](left: Tree[A], right: Tree[A])
    extends Tree[A]

  final case class Leaf[A](value: A) extends Tree[A]

  def branch[A](left: Tree[A], right: Tree[A]): Tree[A] =
    Branch(left, right)

  def leaf[A](value: A): Tree[A] =
    Leaf(value)

  implicit val treeFunctor = new Functor[Tree] {
    override def map[A, B](tree: Tree[A])(f: (A) => B): Tree[B] = tree match {
      case Branch(l, r) => Branch(map(l)(f), map(r)(f))
      case Leaf(v) => Leaf(f(v))
    }
  }

  val ex: Tree[String] = leaf(10).map(i => s"val: $i")

}


val m: Map[String, Any] = Map[String, String]()