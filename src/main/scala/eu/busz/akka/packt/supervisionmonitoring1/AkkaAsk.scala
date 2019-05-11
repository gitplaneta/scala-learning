package eu.busz.akka.packt.supervisionmonitoring1

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.util.Timeout

import scala.concurrent.Future
import akka.pattern.ask

import scala.concurrent.duration.DurationInt
import scala.util.{Failure, Success}

object RequestResponse extends App {
  val system = ActorSystem("system")
  val server = system.actorOf(Props(classOf[HeartServer]))
  val client = system.actorOf(Props(classOf[Client], server))

  client ! "Do you love me?"

  system.terminate()
}

case class Request(msg: String)
case class Response(msg: String)

class Client(server: ActorRef) extends Actor {
  override def receive = {
    case msg: String =>
      implicit val timeout: Timeout = 1.second
      implicit val ec = context.dispatcher
      Future.successful(10)
      val origin = sender
      server ? Request(msg) andThen {
        case Success(Response(s)) => println(s)
        case Failure(_) => println("Failed")
      }
  }
}

class HeartServer extends Actor {
  override def receive = {
    case Request(msg) => sender() ! Response(s"<3$msg<3")
  }
}
