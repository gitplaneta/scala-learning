package eu.busz.akka.packt.reference

import akka.actor.{Actor, ActorIdentity, ActorRef, ActorSystem, Identify, PoisonPill, Props}

object ActorPath extends App {

  val system = ActorSystem("actor-path")
  val counter1 = system.actorOf(Props[Counter], "Counter")
  println(s"Actor reference for counter1: ${counter1}")

  val counterSelection1 = system.actorSelection("counter")
  println(s"Actor Seletion for counter1: $counterSelection1")
  counter1 ! PoisonPill
  Thread.sleep(200)

  val counter2 = system.actorOf(Props[Counter], "counter")
  println(s"Actor reference for actor2 is: $counter2")

  val counterSelection2 = system.actorSelection("counter")
  println(s"Actor selection for counter2: $counterSelection2")
}

object ActorSelection extends App {

  val system = ActorSystem("asdf")
  val counter = system.actorOf(Props[Counter], "counter")
  val watcher = system.actorOf(Props[Watcher], "watcher")

  system.terminate()
}

class Counter extends Actor {
  override def receive: Receive = {
    case msg => println(msg)
  }
}

class Watcher extends Actor {

  var counterRef: ActorRef = _
  val selection = context.actorSelection("/user/counter")
  selection ! Identify(None)

  override def receive: Receive = {
    case ActorIdentity(_, Some(ref)) =>
      println(s"Actor ref is $ref")
    case _ => println("Actor is not alive")
  }
}