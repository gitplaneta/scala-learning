package eu.busz.fp.io

sealed trait TailRec[A] {
  def flatMap[B](f: A => TailRec[B]): TailRec[B] = FlatMap(this, f)

  def map[B](f: A => B): TailRec[B] = flatMap(f andThen (Return(_)))
}

case class Return[A](a: A) extends TailRec[A]

case class Suspend[A](resume: () => A) extends TailRec[A]

case class FlatMap[A, B](sub: TailRec[A], k: A => TailRec[B]) extends TailRec[B]

object Main extends App {

//  val f = (x: Int) => Return(x)
//  val f = (x: Int) => x
//  val l = List.fill(100000)(f).foldLeft(f){ (a,b) => a.compose(b) }

  val f: Int => TailRec[Int] = (i: Int) => Return(i)

  val g: Int => TailRec[Int] =
    List.fill(10000)(f).foldLeft(f){
      (a: (Int) => TailRec[Int],
       b: (Int) => TailRec[Int]) => {
//        (x: Int) => FlatMap(Return(x), xy => a(xy).flatMap(b))
        (x: Int) => Suspend(() => ()).flatMap { _ => a(x).flatMap(b) }
      }
    }
//
//  val l: Int => TailRec[Int] = List.fill(100000)(f).foldLeft(f){
//    (a,b) => FlatMap(a, )
//  }
//  l(10)

}