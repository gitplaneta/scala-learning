package eu.busz.fp.io

object xxx extends App {

  trait Functor[F[_]]

  type F2 = Functor[List]
//  type F3 = Functor[Map]

//  val xxxx = _: Double
  val cube = Math.pow(_: Double, 3) // cube: Double => Double
  cube(2) // 8

  println("hello")

}
//sealed trait Async[A] {
//  def flatMap[B](f: A => Async[B]): Async[B] = FlatMap(this, f)
//
//  def map[B](f: A => B): Async[B] = flatMap(f andThen (Return(_)))
//}
//case class Return[A](a: A) extends Async[A]
//case class Suspend[A](resume: Par[A]) extends Async[A]
//case class FlatMap[A,B](sub: Async[A],
//                        k: A => Async[B]) extends Async[B]
