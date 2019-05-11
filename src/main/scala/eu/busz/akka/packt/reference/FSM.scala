package eu.busz.akka.packt.reference

import akka.actor.{ActorSystem, FSM, Props, Stash}

object FiniteStateMachine extends App {
  import UserStorageFSM._

  val system = ActorSystem("Hotswap-FSM")

  val userStorage = system.actorOf(Props[UserStorageFSM], "userStorage-fsm")

  userStorage ! Connect

  userStorage ! Operation(DBOperation.Create, User("Admin"))

  userStorage ! Disconnect

  Thread.sleep(100)

  system.terminate()

}

object UserStorageFSM {
  trait Data
  case object EmptyData extends Data

  trait State
  case object Disconnected extends State
  case object Connected extends State

  case object Connect
  case object Disconnect

  trait DBOperation
  object DBOperation{
    case object Create extends DBOperation
    case object Update extends DBOperation
    case object Read extends DBOperation
    case object Delete extends DBOperation
  }

  case class Operation(op: DBOperation, user: User)

  case class User(name: String)
}

class UserStorageFSM extends FSM[UserStorageFSM.State, UserStorageFSM.Data] with Stash {
  import UserStorageFSM._

  startWith(Disconnected, EmptyData)

  when(Disconnected) {
    case Event(Connect, _) =>
      println("Connecting...")
      unstashAll()
      this.goto(Connected)
    case _ => stash()
      stay().using(EmptyData)
  }

  when(Connected) {
    case Event(Disconnect, _) =>
      println("Disconnecting...")
      goto(Disconnected).using(EmptyData)
    case Event(Operation(op, user), _) =>
      println(s"Performing opertaion $op $user")
      stay().using(EmptyData)
  }

  initialize()
}