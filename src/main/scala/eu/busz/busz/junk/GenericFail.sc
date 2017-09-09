import scala.concurrent.Future

object GenTest {

  case class ~[A, B](a: A, b: B)

  def dothis[A](first: A, sec: Future[A]): Future[A] = {
    Future.successful(new ~("", "").asInstanceOf[A])
  }
}