package eu.busz.akka.packt.supervisionmonitoring1

import akka.actor.SupervisorStrategy._
import akka.actor.{Actor, ActorContext, ActorRef, ActorSystem, AllForOneStrategy, Props}

import scala.concurrent.duration._

object HierarchyLifecycleMonitoringActors extends App {
  println("Hello world!")

  val system = ActorSystem("supervision")
  val unfair = system.actorOf(Props[UnfairActor], "UnfairActor")

  unfair ! "pee"
  unfair ! "poo"
  unfair ! "NotHungry"


}

//Actors

class UnfairActor extends Actor {
  import ChildActor._

  var childRef: ActorRef = _
  
  override def preStart(): Unit = {
    println(s"preStart of UnfairActor")
    childRef = ChildActor(context)
    super.preStart()
  }

  override def postStop(): Unit = {
    println(s"postStop of UnfairActor")
    super.postStop()
  }

  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    println(s"preRestart of UnfairActor due to $reason")
    super.preRestart(reason, message)
  }

  override def postRestart(reason: Throwable): Unit = {
    println(s"postRestart of UnfairActor due to $reason")
    super.postRestart(reason)
  }


  def receive = {
    case msg =>
      println(s"ChildActor received ${msg}")
      childRef ! msg
      Thread.sleep(100)
  }

  override def supervisorStrategy = AllForOneStrategy(10, 1 second, true) {
    case PeeException => Resume
    case PooException => Restart
    case NotHungryException => Stop
    case _: Exception => Escalate
  }
}

object ChildActor {
  case object PooException extends Exception
  case object PeeException extends Exception
  case object NotHungryException extends Exception

  def apply(context: ActorContext): ActorRef = context.actorOf(Props[ChildActor], "ChildActor")
}

class ChildActor extends Actor {
  import ChildActor._

  override def preStart(): Unit = {
    println(s"preStart of ChildActor")
    super.preStart()
  }

  override def postStop(): Unit = {
    println(s"postStop of ChildActor")
    super.postStop()
  }

  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    println(s"preRestart of ChildActor due to $reason")
    super.preRestart(reason, message)
  }

  override def postRestart(reason: Throwable): Unit = {
    println(s"postRestart of ChildActor due to $reason")
    super.postRestart(reason)
  }

  override def receive = {
    case "Pee" => throw PeeException
    case "Poo" => throw PooException
    case "NotHungry" => throw NotHungryException
  }
}