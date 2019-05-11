package eu.busz.akka.packt.supervisionmonitoring1

import akka.actor.{Actor, ActorRef, ActorSystem, Props, Terminated}

object Monitoring extends App {
  val system = ActorSystem("monitoring-test")
  val slave = system.actorOf(Props[Slave])
  val master = system.actorOf(Props(classOf[Master], slave))

  slave ! "die-die-die"

  Thread.sleep(500)

  system.terminate()
}

class Slave extends Actor {
  override def receive = {
    case msg => println(s"Received msg $msg")
      context.stop(self)
  }
}

class Master(slave: ActorRef) extends Actor {
  override def receive = {
    case Terminated(_) => context.stop(self)
  }

  override def preStart(): Unit = {
    context.watch(slave)
  }

  override def postStop(): Unit = {
    println("Master stopping...")
  }
}
