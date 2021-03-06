package main.scala.eu.busz.busz.coursera.second.week2

class Pouring(capacity: Vector[Int]) {

  //States
  type State = Vector[Int]
  val itialState = capacity.map(_ => 0)

  //Moves
  trait Move

  case class Empty(glass: Int) extends Move

  case class Fill(glass: Int) extends Move

  case class Pour(from: Int, to: Int) extends Move

  val glasses = 0 until capacity.length

  val moves =
    (for (g <- glasses) yield Empty(g)) ++
      (for (g <- glasses) yield Fill(g)) ++
      (for (to <- glasses; from <- glasses if (from != to)) yield Pour(from, to))

}
