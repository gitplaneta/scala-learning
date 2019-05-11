package eu.busz.akka.other

import akka.actor.Actor

import collection.immutable.Set

class MyActor extends Actor {
  def receive = active(Set.empty)

  def active(isInSet: Set[String]): Receive = {
    case Add(key) =>
      context become active(isInSet + key)

    case Contains(key) =>
      sender() ! isInSet(key)
  }
}
// Messages
case class Add(key: String)
case class Contains(key: String)