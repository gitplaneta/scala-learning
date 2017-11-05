import cats.{Applicative, Id, Monad}

import scala.language.higherKinds
import scala.concurrent.Future
import cats.instances.future._
import cats.instances.list._
import cats.instances.option._
import cats.syntax.traverse._
import cats.syntax.functor._

import scala.concurrent.ExecutionContext.Implicits.global


trait UptimeClient[F[_]] {
  def getUptime(hostname: String): F[Int]
}

class UptimeService[F[_]: Applicative](client: UptimeClient[F]) {
  def getTotalUptime(hostnames: List[String]): F[Int] = {
//    val x = Future.traverse(hostnames)(client.getUptime).map(_.sum)
//    val y = hostnames.sequence
//    hostnames.traverse(Option(""))
    hostnames.traverse(client.getUptime).map(_.sum)
  }
}

class TestUptimeClient(hosts: Map[String, Int]) extends UptimeClient[Id] {
  def getUptime(hostname: String): Id[Int] = hosts.getOrElse(hostname, 0)
}

trait RealUptimeClient extends UptimeClient[Future] {
  def getUptime(hostname: String): Future[Int]
}


def testTotalUptime() = {
  val hosts = Map("host1" -> 10, "host2" -> 6)
  val client = new TestUptimeClient(hosts)
  val service = new UptimeService(client)
  val actual = service.getTotalUptime(hosts.keys.toList)
  val expected = hosts.values.sum
  assert(actual == expected)
}

List(1, 2, 3).traverse[Id, Int] { (x: Int) => x + 1 }
testTotalUptime()

