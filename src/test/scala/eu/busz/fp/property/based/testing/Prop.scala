package eu.busz.fp.property.based.testing

import eu.busz.fp.property.based.testing.Prop.{FailedCase, SuccessCount}

trait Prop {

  def check: Either[(FailedCase, SuccessCount), SuccessCount]

  def &&(p: Prop): Prop = {
    new Prop {
      override def check: Either[(FailedCase, SuccessCount), SuccessCount] = {
        for {
          l <- Prop.this.check
          r <- p.check
        } yield l + r
      }
    }
  }
}

object Prop {

  type SuccessCount = Int
  type FailedCase = String

  def forAll[A](a: Gen[A])(f: A => Boolean): Prop = ???
}


//GEN
case class Gen[A](sample: State[RNG, A])

object Gen {

  def choose(start: Int, stopExclusive: Int): Gen[Int] = {
    Gen[Int](State(RNG.nonNegativeInt).map(a => start + a % (stopExclusive - start)))
  }

  def boolean: Gen[Boolean] =
    Gen(State(RNG.boolean))

  def unit[A](a: => A): Gen[A] = Gen(State(seed => (a, seed)))

  def listOfN[A](n: Int, g: Gen[A]): Gen[List[A]] = {
    val states: List[State[RNG, A]] = List.fill[State[RNG, A]](n)(g.sample)
    val s: State[RNG, List[A]] = State.sequence(states)
    Gen(s)
  }

  def listOf[A](a: Gen[A]): Gen[List[A]] = ???
}


object Main extends App {
  println("OK")
}
