import scala.concurrent.Await
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.duration.Duration

object ex53 {

  import cats.data.EitherT
  import cats.instances.future._

  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.Future

  //  type Response[A] = Future[Either[String,A]]
  type Response[A] = EitherT[Future, String, A]
  //  type Response[A] = ResponseEither[Int]

  val powerLevels = Map(
    "Jazz" -> 6,
    "Bumblebee" -> 8,
    "Hot Red" -> 10
  )

  def getPowerLevel(autobot: String): Response[Int] = {
    powerLevels.get(autobot) match {
      case Some(v) => EitherT.right(Future(v))
      case None => EitherT.left(Future(s"$autobot not available"))
    }
  }

  def canSpecialMove(ally1: String, ally2: String): Response[Boolean] = {
    val a = getPowerLevel(ally1)
    val b = getPowerLevel(ally2)

    for {
      a1 <- a
      b1 <- b
    } yield a1 + b1 > 15
  }

  def tacticalReport(ally1: String, ally2: String): String = {
    Await.result(canSpecialMove(ally1, ally2).map(b => if (b) "can" else "can't")
      .value, 10.seconds) match {
      case Left(msg) => msg
      case Right(msg)=> msg
    }
  }

  tacticalReport("Jazz", "Bumblebee")
  // res25: String = Jazz and Bumblebee need a recharge.
  tacticalReport("Bumblebee", "Hot Rod")
  // res26: String = Bumblebee and Hot Rod are ready to roll out!
  tacticalReport("Jazz", "Ironhide")
  // res27: String = Comms error: Ironh

}