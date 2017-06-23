object equality_cats {
  import cats.Eq
  import cats.instances.int._
  import cats.syntax.eq._

  val eqInt = Eq[Int]
  eqInt.eqv(100, 100)

  1000 === 1000
  1000 =!= 1000

  Some(10) != Some(10)
}

object meet_cats {

  import java.util.Date

  import cats.Show
  import cats.instances.int._
  import cats.instances.string._
  import cats.syntax.show.toShowOps

  val showInt: Show[Int] = Show.apply[Int]
  val showString: Show[String] = Show.apply[String]

  "sh".show

  implicit val dateShow: Show[Date] =
    Show.show(date => s"${date.getTime}ms since the epoch.")

  final case class Cat(name: String, age: Int, color: String)

  implicit val catShow: Show[Cat] = Show.show(c => s"${c.name} is a ${c.age} year-old ${c.color} cat")

  Cat("mruczek", 10, "black & yello").show
}

