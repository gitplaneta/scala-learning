package eu.busz.coursera.second.week1

import scala.util.Random

/**
  * Created by Radek on 2016-08-06.
  */
object RandomGenerator {

  val integers = new Generator[Int] {
    val rnd = new Random()

    def generate: Int = rnd.nextInt
  }

  val booleans = integers.map(_ >= 0)

  def leafs: Generator[Leaf] = {
    integers.map(Leaf(_))
  }

  def inners: Generator[Inner] =
    for {
      r <- trees
      l <- trees
    } yield Inner(r, l)


  def trees: Generator[Tree] = for {
    isLeaf <- booleans
    tree <- if (isLeaf) leafs else inners
  } yield tree

}

trait Generator[+T] {

  self =>

  def generate: T

  def map[S](f: T => S): Generator[S] = new Generator[S] {
    override def generate: S = f(self.generate) //or f(Generator.this.generate)
  }

  def flatMap[S](f: T => Generator[S]): Generator[S] = new Generator[S] {
    override def generate: S = f(self.generate).generate
  }
}

trait Tree

case class Inner(left: Tree, right: Tree) extends Tree

case class Leaf(x: Int) extends Tree

