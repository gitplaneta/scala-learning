import cats.data.Validated
import cats.data.ValidatedNel
import cats.instances.list.catsStdInstancesForList
import cats.syntax.cartesian._
import cats.instances.string._

val val1: ValidatedNel[String, String] = Validated.invalidNel("Error 1")
val val3: ValidatedNel[String, String] = Validated.valid("Success1")
val val2: ValidatedNel[String, String] = Validated.invalidNel("Error 2")
val val4: ValidatedNel[String, String] = Validated.valid("Success2")

(val1 |@| val3 |@| val2).map(_ + _ + _)

(val3 |@| val4).map(_ + _)