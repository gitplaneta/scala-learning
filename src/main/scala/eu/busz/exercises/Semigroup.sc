import cats.implicits._
import cats.kernel.Semigroup

Semigroup[Int].combine(1, 2) == (3)
Semigroup[List[Int]].combine(List(1, 2, 3), List(4, 5, 6)) ==(List(1,2,3,4,5,6))

Semigroup[Option[Int]].combine(Option(1), Option(2)) == Some(3)

Semigroup[Option[Int]].combine(Option(1), None) == Some(1)
val xxx = Semigroup[Int ⇒ Int]
  .combine({ (x: Int) ⇒
    x + 1
  }, { (x: Int) ⇒
    x * 10
  })
  .apply(6)


import cats.implicits._

val aMap = Map("foo" → Map("bar" → 5))
val anotherMap = Map("foo" → Map("bar" → 6))
val combinedMap = Semigroup[Map[String, Map[String, Int]]].combine(aMap, anotherMap)

"asdf" -> "asdf"