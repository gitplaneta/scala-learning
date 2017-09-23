import cats.data.NonEmptyList

object ex644 {
  import cats.syntax.either._
  import cats.instances.either._
  import cats.syntax.cartesian._
  import cats.data.Validated
  import cats.data.ValidatedNel

  type ErrorOr[A] = Either[String, A]
  type AllErrorsOr[A] = ValidatedNel[String, A]

  case class User(name: String, age: Int)

  def getValue(map:Map[String, String], key: String): ErrorOr[String] = {
    map.get(key).toRight("not specified")
//    val opt = map.get(key).flatMap(n => if (n.isEmpty) None else Some(n))
//    opt.toRight(List("some field was invalid")).toValidatedNel
  }

  def nonBlank(s: String): ErrorOr[String] = if (s.isEmpty) Left("Is empty") else Right(s)

  def nonNegative(i: Int): ErrorOr[Int] = if (i < 0) Left("Can't be negative") else Right(i)

  def parseInt(data: String): ErrorOr[Int] =
    Right(data)
    .flatMap(s => Either.catchOnly[NumberFormatException](s.toInt))
    .leftMap(_ => "Name must be integer")

  def readName(map: Map[String, String]): ErrorOr[String] = {
    getValue(map, "name")
      .flatMap(nonBlank)
    //    Either.fromOption(map.get("name"), "Missing value in map!")
    //      .
    //    Validated.fromOption(map.get("name"), "Missing value in map!").toEither
    //      .map()
  }

  def readAge(map: Map[String, String]): ErrorOr[Int] = {
    getValue(map, "age")
      .flatMap(parseInt)
      .flatMap(nonNegative)
  }

  def readUser(map: Map[String, String]): AllErrorsOr[User] = {
    (readName(map).toValidatedNel |@| readAge(map).toValidatedNel).map(User.apply)
  }


}
object v2 {
  import cats.data.Validated
  import cats.Cartesian
  import cats.instances.string._
  import cats.instances.vector._
  import cats.syntax.validated._
  import cats.syntax.cartesian._


  type AllErrorsOr[A] = Validated[String, A]
  Cartesian[AllErrorsOr].product(Validated.invalid("A"), Validated.invalid("B"))

  (
    Vector(404).invalid[Int] |@|
      Vector(500).invalid[Int]
    ).tupled
//???
//  (
//    Vector(404).valid[Int] |@|
//      Vector(500).valid[Int]
//    ).tupled

  import cats.data.NonEmptyVector
  (
    NonEmptyVector.of("Error 1").invalid[Int] |@|
      NonEmptyVector.of("Error 2").invalid[Int]
    ).tupled
}

object v1 {

  import cats.Cartesian
  import cats.data.Validated
  import cats.instances.list._

  type AllErrorsOr[A] = Validated[List[String], A]

  Cartesian[AllErrorsOr].product(
    Validated.invalid(List("dupa")),
    Validated.invalid(List("dupa2"))
  )

  Validated.fromEither[String, String](Right("dupa"))
  Validated.fromOption(None, "sadf")

  Validated.catchOnly[NumberFormatException]("foo".toInt)
  Validated.catchNonFatal(sys.error("Badness"))
  Validated.fromTry(scala.util.Try("foo".toInt))
  Validated.fromEither[String, Int](Left("Badness"))
  Validated.fromOption[String, Int](None, "Badness")
}