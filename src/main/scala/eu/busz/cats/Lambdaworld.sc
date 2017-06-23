import cats.kernel.Monoid
import cats.instances.list._
import cats.instances.future._
import cats.syntax.foldable._

import scala.concurrent.{ExecutionContext, Future}
import ExecutionContext.Implicits.global

case class Result(success: Int, errors: Int)
val rm = new Monoid[Result] {
  override def empty: Result = Result(0,0)
  override def combine(x: Result, y: Result): Result = Result(x.success + y.success, x.errors + y.errors)
}

class Page[T]
class Item
def processPageOfItems(page: Page[Item]): Result = ???
val pages: List[Page[Item]] = List[Page[Item]]()

val overallResultV: Result = pages.foldMap(processPageOfItems)(rm)

//vs
def combine2(x: Result, y: Result): Result = Result(x.success + y.success, x.errors + y.errors)
pages.foldLeft(Result(0,0))((r, pi) => combine2(processPageOfItems(pi), r))


val f1 = Future.successful(9)
val f2 = Future.successful("")


for {
  one <- f1
  _ <- "gowno"
  two <- f2
} two
