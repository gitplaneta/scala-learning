import cats.data.State

import scala.util.Try

type CalcState[A] = State[List[Int], A]

def evalOne(sym: String): CalcState[Int] = {
  def op(a: Int, b: Int) = sym match {
    case "+" => a + b
    case "-" => a - b
    case "*" => a * b
    case "/" => a / b
  }

  Try(sym.toInt).toOption match {
    case Some(i) => State[List[Int], Int] { st =>
      (i :: st, i)
    }
    case None => State[List[Int], Int] {
      case h1 :: h2 :: tail => (op(h1, h2) :: tail, op(h1, h2))
    }
  }
}

def evalAll(input: List[String]): CalcState[Int] = {
  input.foldLeft(State.pure[List[Int], Int](0)) { (a, b) => a flatMap (_ => evalOne(b))}
//  input.map(evalOne
//    .reduce((a,b) => a.flatMap(a => b))
}

val program = for {
  _ <- evalOne("1")
  _ <- evalOne("2")
  ans <- evalOne("+")
} yield ans

program.runA(Nil).value

val program2 = evalAll(List("1", "2", "+", "3", "*"))
program2.runA(Nil).value

//import cats.syntax.applicative._
//State.pure[List[Int], Int](0).runA(Nil).value
//0.pure[CalcState]