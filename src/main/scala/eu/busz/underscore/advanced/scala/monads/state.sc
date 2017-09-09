object state3 {
  import cats.data.State

  val step1 = State[Int, String] { state =>
    val ans = state +1
    (ans, s"Result of step1: $ans")
  }
  val getDemo = State.get[Int]

  getDemo.run(10).value
  step1.run(10).value

  val setDemo = State.set[Int](30)
  setDemo.run(10).value

  val pureDemo = State.pure[Int, String]("Result")
  pureDemo.run(10).value

  val inspectDemo = State.inspect[Int, String](_ + "!")
  inspectDemo.run(10).value
}

object state2 {

  import cats.data.State

  val step1 = State[Int, String] { state =>
    val ans = state +1
    (ans, s"Result of step1: $ans")
  }

  val step2 = State[Int, String] { state =>
    val ans = state * 2
    (ans, s"Result of step2: $ans")
  }

  val both = for {
    a <- step1
    b <- step2
  } yield (a, b)

  val (state, result) = both.run(20).value
    "end"

}
object state1 {
  import cats.data.State

  val a = State[Int, String] { state =>
    (state, s"The state is $state")
  }

  val (state, result) = a.run(10).value //(state, result)
  val st = a.runS(10).value //Get state ignore result
  val res = a.runA(10).value //Get result ignore State
  a.runF
}

