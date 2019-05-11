import scala.concurrent.Future

trait Termianl[C[_]] {
  def read: C[String]

  def write(t: String): C[Unit]
}

type Now[X] = X

object TerminalSync extends Termianl[Now] {
  override def read: String = ""

  override def write(t: String): Unit = ???
}

object TermianlAsync extends Termianl[Future] {
  override def read: Future[String] = ???

  override def write(t: String): Future[Unit] = ???
}

trait Execution[C[_]] {
  def chain[A, B](c: C[A])(f: A => C[B]): C[B]

  def create[B](b: B): C[B]
}

def echo[C[_]](t: Termianl[C], e: Execution[C]): C[String] = {
  e.chain(t.read)(s => {
    e.chain(t.write(s))(_ => {
      e.create(s)
    })
  })
}

object Execution {
  implicit class Ops[C[_], A](c: C[A]) {
    def flatMap[B](f: A => C[B])(implicit e: Execution[C]): C[B] = {
      e.chain(c)(f)
    }

    def map[B](f: A => B)(implicit e: Execution[C]): C[B] = {
//      e.chain(c)(a => e.create(f(a)))
      e.chain(c)(f andThen e.create)
    }
  }
}

def echo2[C[_]](implicit t: Termianl[C], e: Execution[C]): Unit = {
  for {
    in <- t.read
    _ <- t.write(in)
  } yield in
}


final class IO[A](val interpret: () => A) {
  def map[B](f: A => B) = IO[B](f(interpret()))

  def flatMap[B](f: A => IO[B]): IO[B] = IO(f(interpret()).interpret())
}

object IO {
  def apply[A](a: => A): IO[A] = new IO(() => a)
}


object TerminalIO extends Termianl[IO] {
  override def read: IO[String] = IO { io.StdIn.readLine }

  override def write(t: String): IO[Unit] = IO { println(t) }
}