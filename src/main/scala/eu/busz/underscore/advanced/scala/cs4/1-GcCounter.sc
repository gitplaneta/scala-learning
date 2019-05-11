final case class GCounterOld(counters: Map[String, Int]) {

  def increment(machine: String, amount: Int) = {
    counters.updated(machine, counters.getOrElse(machine, 0) + 1)
  }

  def get: Int = counters.values.sum

  def merge(that: GCounterOld): GCounterOld = {
    val newCounter = (that.counters.toSeq ++ counters.toSeq)
      .groupBy(_._1)
      .mapValues(_.map(_._2).max)
    GCounterOld(newCounter)
  }
}

//------------
import scala.language.higherKinds
import cats.Monoid
import cats.instances.map._
import cats.syntax.semigroup._
import cats.syntax.foldable._

trait BoundedSemiLattice[A] extends Monoid[A] {
  def combine(a1: A, a2: A): A
  def empty: A
}

object BoundedSemiLattice {
  implicit object intBoundedSemiLatticeInstance extends
    BoundedSemiLattice[Int] {
    def combine(a1: Int, a2: Int): Int =
      a1 max a2
    val empty: Int = 0
  }

  implicit def setBoundedSemiLatticeInstance[A]:
  BoundedSemiLattice[Set[A]] =
    new BoundedSemiLattice[Set[A]]{
      def combine(a1: Set[A], a2: Set[A]): Set[A] =
        a1 union a2
      val empty: Set[A] =
        Set.empty[A]
    }
}

trait GCounter[F[_,_],K, V] {
  def increment(f: F[K, V])(k: K, v: V)(implicit m: Monoid[V]):
  F[K, V]
  def total(f: F[K, V])(implicit m: Monoid[V]): V
  def merge(f1: F[K, V], f2: F[K, V])(implicit b: BoundedSemiLattice[V]): F[K, V]
}

object GCounter {
  implicit def mapGcounter[K, V] = new GCounter[Map, K, V] {

    override def increment(f: Map[K, V])(k: K, v: V)(implicit m: Monoid[V]): Map[K, V] = {
      f.updated(k, f.getOrElse(k, m.empty) |+| v)
    }

    override def total(f: Map[K, V])(implicit m: Monoid[V]): V = f.foldMap(a => a)

    override def merge(f1: Map[K, V], f2: Map[K, V])(implicit b: BoundedSemiLattice[V]) = f1 |+| f2

  }
  def apply[F[_,_],K, V](implicit g: GCounter[F, K, V]) = g
}

import cats.instances.int._
import GCounter.mapGcounter
val g1 = Map("a" -> 7, "b" -> 3)
val g2 = Map("a" -> 2, "b" -> 5)
GCounter[Map, String, Int]
println(s"Merged: ${GCounter[Map, String, Int].merge(g1,g2)}")
println(s"Total: ${GCounter[Map, String, Int].total(g1)}")