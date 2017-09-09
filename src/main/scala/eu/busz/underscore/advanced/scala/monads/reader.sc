import cats.data.Reader
import ex1.{Db, checkLogin}

object ex1 {

  import cats.syntax.applicative._

  case class Db(
                 usernames: Map[Int, String],
                 passwords: Map[String, String]
               )

  type DbReader[A] = Reader[Db, A]

  val store: Db = Db(Map(1 -> "user1", 2 -> "user2"),
    Map("user1" -> "pass1", "user2" -> "pass2"))

  def findUsername(userId: Int): DbReader[Option[String]] = {
    Reader(db => db.usernames.get(userId))
  }

  def checkPassword(username: String, password: String
                   ): DbReader[Boolean] = {
    Reader(db => db.passwords.get(username)
      .exists(_ == password))
  }

  def checkLogin(userId: Int, password: String): DbReader[Boolean] = for {
    username <- findUsername(userId)
    passMatch <- username.map(checkPassword(_, password))
      .getOrElse(false.pure[DbReader])
  } yield passMatch

}
val db = Db(
  Map(
    1 -> "dade",
    2 -> "kate",
    3 -> "margo"
  ),
  Map(
    "dade" -> "zerocool",
    "kate" -> "acidburn",
    "margo" -> "secret"
  )
)
checkLogin(1, "zerocool").run(db)
checkLogin(4, "davinci").run(db)

case class Cat(name: String, favoriteFood: String)

val catName: Reader[Cat, String] =
  Reader(cat => cat.name)

catName.run(Cat("Garfield", "lasagne"))

//map
val greetKitty: Reader[Cat, String] =
  catName.map("Hello " + _)

//flatMap:
val feedKitty: Reader[Cat, String] =
  Reader(cat => s"Have a nice bowl of ${cat.favoriteFood}")

val greetAndFeed: Reader[Cat, String] = for {
  gr <- greetKitty
  fd <- feedKitty
} yield s"$gr $fd"

greetAndFeed(Cat("Garfield", "lasagne"))

