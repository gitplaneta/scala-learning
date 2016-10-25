package eu.busz.coursera.third.week3

import scala.collection.parallel.{Splitter, Task}
import scala.eu.busz.coursera.third.week2

/**
  * Created by Radek on 2016-08-30.
  */
//class Combiners(iter: Iterator[Int]) {
//
//  def foldLeft[B](z: B)(f: (B, Int) => B): B = {
//    if (iter.hasNext)
//      foldLeft(f(z, iter.next()))(f)
//    else z
//  }
//}
//
//trait Splitter[T] {
//  def split: Seq[Splitter[T]]
//  def remaining: Int
//}

//class SplitterFold[T] extends Splitter[T] {
//
//  val threshold: Int
//  def remaining: Int
//
//  def fold(z: T)(f: (T, T) => T): T = {
//    if (remaining < threshold) foldLeft(z)(f)
//    else {
//      val children: Seq[Task[T, T]] = for(child <- split) yield task { child.fold(z)(f)}
//      children.map(_.join()).foldLeft(z)(f)
//    }
//  }

//  def foldLeft[T](z: T)(f: (T, T) => T): T = {
//    if (hasNext)
//      foldLeft(f(z, this.next())(f)
//    else z

//  }
//}

