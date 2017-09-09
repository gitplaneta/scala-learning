object WriterExercise {

  import scala.concurrent._
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.duration._
  import cats.data.Writer
  import cats.instances.vector._

  def slowly[A](body: => A) =
    try body finally Thread.sleep(100)

  def factorial(n: Int): Int = {
    val ans = slowly(if (n == 0) 1 else n * factorial(n - 1))
    println(s"fact $n $ans")
    ans
  }

  def factorialW(n: Int): Writer[Vector[String], Int] = {
    slowly(
      if (n == 0) Writer(Vector(n.toString), 1)
      else factorialW(n - 1).mapBoth((log, res) => (log :+ n.toString , res * n))
    )
  }

  Await.result(Future.sequence(Vector(
    Future(factorialW(3)),
    Future(factorialW(3))
  )), 5.seconds)
}


object Writer1 {

  import cats.data.Writer
  import cats.instances.vector._

  Writer(Vector("bla1", "bla2"), 123)

  import cats.syntax.applicative._

  type Logged[A] = Writer[Vector[String], A]
  123.pure[Logged]

  import cats.syntax.writer._

  Vector("mes1", "mes2", "mes3").tell

  val writer1 = for {
    a <- 10.pure[Logged]
    _ <- Vector("A", "B").tell
    b <- 32.writer(Vector("x", "y", "z"))
  } yield a + b
  writer1.run

  Writer(Vector("Abb", "ZZZZ"), 10)
    .mapWritten(_.map(_.toUpperCase()))

}

object extraction {

  import cats.data.Writer
  import cats.syntax.writer._

  val a = 123.writer(Vector("msg1", "msg2"))
  val b = Writer(Vector("1", "2"), 321)

  val (log, result) = b.run
  val l1 = a.written
  val r1 = a.value

}
