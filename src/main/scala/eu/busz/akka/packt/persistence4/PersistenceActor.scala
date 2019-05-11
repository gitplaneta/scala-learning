package eu.busz.akka.packt.persistence4

import akka.persistence._
import akka.actor.{ActorLogging, ActorSystem, FSM, Props, Stash}
import eu.busz.akka.packt.persistence4.Counter._

object PersistenceActor extends App {
  import Counter._

  val system = ActorSystem("persistent-actors")

  val counter = system.actorOf(Props[Counter])

  counter ! Cmd(Increment(3))

  counter ! Cmd(Increment(5))

  counter ! Cmd(Decrement(3))

  counter ! "print"

  Thread.sleep(1000)

  system.terminate()


}

object Counter {
  sealed trait Operation {
    val count: Int
  }

  case class Increment(override val count: Int) extends Operation
  case class Decrement(override val count: Int) extends Operation

  case class Cmd(op: Operation)
  case class Evt(op: Operation)

  case class State(count: Int)
}

class Counter extends PersistentActor with ActorLogging {
  var state: State = State(0)

  def updateState(evt: Evt): Unit = evt match {
    case Evt(Increment(count)) => state = State(state.count + count)
    case Evt(Decrement(count)) => state = State(state.count - count)
  }

  //needs to be unique, must be the same between actor incarnations
  override def persistenceId: String = "example-persistence-actor"

  override def receiveRecover: Receive = {
    case evt: Evt => println(s"Counter received $evt on recovering mode")
      updateState(evt)
    case SnapshotOffer(_, snapshot: State) =>
      println(s"Counter received snapshot with data: $snapshot on recovering mode")
      state = snapshot
  }

  override def receiveCommand: Receive = {
    case cmd @ Cmd(op) =>
      println(s"Counter received $cmd")
      persist(Evt(op)) { evt => updateState(evt)}
    case "print" =>
      println(s"The Current state of counter is ${state}")
  }
}