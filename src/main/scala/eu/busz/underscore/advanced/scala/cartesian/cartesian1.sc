object ex2 {

  import scala.concurrent._
  import scala.concurrent.duration._
  import scala.concurrent.ExecutionContext.Implicits.global

  lazy val timestamp0 = System.currentTimeMillis

  def getTimestamp: Long = {
    val timestamp = System.currentTimeMillis - timestamp0
    Thread.sleep(100)
    timestamp
  }

  val timestamps = for {
    a <- Future(getTimestamp)
    b <- Future(getTimestamp)
    c <- Future(getTimestamp)
  } yield (a, b, c)
  Await.result(timestamps, 1.second)
}

object task1 {
  import scala.language.higherKinds
  import cats.Monad
  import cats.syntax.flatMap._
  import cats.syntax.functor._

  def product[M[_]: Monad, A, B](
                                  fa: M[A],
                                  fb: M[B]
                                ): M[(A, B)] = {
    // implicitly[Monad[M]].flatMap(fa)(a => implicitly[Monad[M]].map(fb)(b => (a,b)))
    // fa.flatMap(a => fb.map(b => (a, b)))
    for {
    a <- fa
      b <- fb
    } yield (a,b)

  }
}

object privEx {

  import scala.concurrent._
  import scala.concurrent.duration._
  import scala.concurrent.ExecutionContext.Implicits.global
  import cats.Cartesian
  import cats.instances.future._
  import cats.instances.list._

  lazy val timestamp0 = System.currentTimeMillis

  def getTimestamp: Long = {
    val timestamp = System.currentTimeMillis - timestamp0
    Thread.sleep(100)
    timestamp
  }

  val ts = Cartesian[Future].product(Future(getTimestamp), Future(getTimestamp))
  Await.result(ts, 1.second)

  Cartesian[List].product(List(1, 2), List(3, 4))
}

object ex5 {
  import cats.Monoid
  import cats.instances.boolean._
  import cats.instances.int._
  import cats.instances.list._
  import cats.instances.string._
  import cats.syntax.cartesian._
  case class Cat(
                  name: String,
                  yearOfBirth: Int,
                  favoriteFoods: List[String]
                )
  def catToTuple(cat: Cat) =
    (cat.name, cat.yearOfBirth, cat.favoriteFoods)
  implicit val catMonoid = (
    Monoid[String] |@|
      Monoid[Int] |@|
      Monoid[List[String]]
    ).imap(Cat.apply)(catToTuple)


  import cats.syntax.monoid._
  Monoid[Cat].empty |+| Cat("Garfield", 1998, List("Lasagna"))
  Cat("Garfield", 1998, List("Lasagna")) |+| Cat("Garfield", 1998, List("Lasagna"))
}

object ex4 {

  import cats.instances.option._
  import cats.syntax.cartesian._

  (Option(23) |@| Option(2) |@| Option("asdf")).tupled

  case class Cat(name: String, paws: Int, colour: String)

  (
    Option("Jack") |@|
    Option(4) |@|
    Option("Black")
    ).map(Cat.apply)
}


object ex3 {

  import cats.Cartesian
  import cats.instances.option._

  val c = Cartesian[Option].product(Some(1), Some("dupa"))
  Cartesian.tuple3(Option(1), Option("xxx"), Option(2.2))
  Cartesian.tuple3(Option(1), None, Option(""))

  Cartesian.map3(Option("a"), Option(2), Option(2.2))((a, b, c) => c)

}



object ex1 {

  import cats.syntax.either._


  def parseInt(str: String): Either[String, Int] =
    Either.catchOnly[NumberFormatException](str.toInt).
      leftMap(_ => s"Couldn't read $str")

  for {
    a <- parseInt("a")
    b <- parseInt("b")
    c <- parseInt("c")
  } yield (a + b + c)
}

