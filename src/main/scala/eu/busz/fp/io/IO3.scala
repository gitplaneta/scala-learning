package eu.busz.fp.io

import eu.busz.fp.io.IO2.Monad
import eu.busz.fp.io.IO3._
import eu.busz.fp.io.IO3.IO
import eu.busz.fp.io.IO3.Return

import scala.annotation.tailrec

object IO3 {

  sealed trait IO[A] {

    def flatMap[B](f: A => IO[B]): IO[B] = {
      FlatMap(this, f)
    }

    def map[B](f: A => B): IO[B] = {
      flatMap(f andThen (Return(_)))
    }
  }

  case class Return[A](a: A) extends IO[A]
  case class Suspend[A](resume: () => A) extends IO[A]
  case class FlatMap[A, B](value: IO[A], f: A => IO[B]) extends IO[B]


  object IO extends Monad[IO] {
    override def unit[A](a: => A): IO[A] = new IO[A] {

    }

    override def flatMap[A, B](a: IO[A])(f: A => IO[B]): IO[B] = flatMap(a)(f)

    @tailrec
    def run[A](io: IO[A]): A = io match {
      case Return(a) => a
      case Suspend(r) => r()
//      case FlatMap(x, f) => run(f(run(x)))
      case FlatMap(x, f) => x match {
        case Return(a) => run(f(a))
        case Suspend(r) => run(f(r()))
        case FlatMap(y, g) => run(y flatMap (a => g(a) flatMap f))
      }
    }
  }

}


object test extends App {
//  def printLine(s: String): IO[Unit] =
//    Suspend(() => Return(println(s)))
//
//  val p = IO.forever(printLine("Still going..."))
}