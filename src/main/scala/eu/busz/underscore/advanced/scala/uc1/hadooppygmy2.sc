import cats.Monoid
import cats.syntax.semigroup._
import cats.instances.int._
import cats.instances.list._
import cats.instances.vector._
import cats.instances.future._
import cats.instances.string._
import cats.syntax.traverse._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationLong
import scala.concurrent.{Await, Future}

def foldMap[A, B: Monoid](v: Vector[A])(f: A => B): B = {
  v.foldLeft(Monoid[B].empty)(_ |+| f(_))
}

def parallelFoldMap[A, B: Monoid](values: Vector[A])(func: A => B): Future[B] = {
  val n = Runtime.getRuntime.availableProcessors
  val jobs = values.grouped(values.size / n).toVector
  jobs
    .traverse(b => Future(foldMap(b)(func)))
    .map(_.foldLeft(Monoid[B].empty)(_ |+| _))
}


foldMap(Vector(1, 2, 3))(_.toString + ":P")
foldMap("Hello world!".toVector)(_.toString.toUpperCase)

val future: Future[Int] =
  parallelFoldMap((1 to 1000).toVector)(_ * 1000)
Await.result(future, 1.second)