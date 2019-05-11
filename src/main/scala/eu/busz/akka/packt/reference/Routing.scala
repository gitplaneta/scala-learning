package eu.busz.akka.packt.reference

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.routing.{FromConfig, RandomGroup}

object Routing extends App {
  val system = ActorSystem("asdf")
  val router = system.actorOf(Props[PoolRouter])

  router ! Work()
  router ! Work()
  router ! Work()

  system.terminate()
}

object GroupRouting extends App {
  val system = ActorSystem("actor-sys")

  system.actorOf(Props[Worker], "w1")
  system.actorOf(Props[Worker], "w2")
  system.actorOf(Props[Worker], "w3")
  val workers = List("/user/w1", "/user/w2", "/user/w3")
  val rgroup = system.actorOf(Props(classOf[GroupRooter], workers))

  rgroup ! Work()
  rgroup ! Work()
  rgroup ! Work()

  Thread.sleep(100)
  system.terminate()
}

object RandomRouter extends App {
  val sys = ActorSystem("Random-Router")
  val routerPool = sys.actorOf(FromConfig.props(Props[Worker]), "random-router-pool")

  routerPool ! Work()
  routerPool ! Work()
  routerPool ! Work()
  routerPool ! Work()

  Thread.sleep(200)
  sys.terminate()
}

object RandomRouterFromCode extends App {
  val sys = ActorSystem("Random-Router")
  sys.actorOf(Props[Worker], "w1")
  sys.actorOf(Props[Worker], "w2")
  sys.actorOf(Props[Worker], "w3")
  val workers = List("/user/w1", "/user/w2", "/user/w3")
  val routerPoolAlt = sys.actorOf(RandomGroup(workers).props(), "random-router-group")

  routerPoolAlt ! Work()
  routerPoolAlt ! Work()
  routerPoolAlt ! Work()

  Thread.sleep(100)
  sys.terminate()

}

class PoolRouter extends Actor {

  var routees: List[ActorRef] = _

  override def preStart(): Unit = {
    routees = List.fill(2)(context.actorOf(Props[Worker]))
  }

  override def receive: Receive = {
    case msg: Work => println("Router received massage...")
      routees(util.Random.nextInt(routees.size)).forward(msg)
  }
}

class GroupRooter(actorSelections: List[String]) extends Actor {
  override def receive: Receive = {
    case w: Work => context.actorSelection(actorSelections(util.Random.nextInt(actorSelections.size))) forward w
  }
}


//Worker
class Worker extends Actor {
  override def receive: Receive = {
    case msg: Work => println(s"I received Work Message Ref=$self")
  }
}
case class Work()