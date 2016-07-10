val m = Map("I" -> 1, "II" -> 2)

m("I")
m.get("I")
m.get("asdf")
m + ("I" -> "asdf")

class Poly(val terms0: Map[Int, Double]) {
  def this(bindings: (Int, Double)*) = this(bindings.toMap)
  val terms = terms0 withDefaultValue (0.0)
  def +(other: Poly) = new Poly(terms ++ (other.terms map adjust))
  override def toString: String =
    (for ((exp, coeff) <- terms.toList.sorted.reverse) yield coeff + "x^" + exp) mkString(" + ")
  def adjust(term: (Int, Double)): (Int, Double) = {
    val (exp, coeff) = term
    exp -> (coeff + terms(exp))
  }

}

val p1 = new Poly(Map(1 -> 2.0, 3 -> 4.0, 5 -> 6.2))
val p2 = new Poly(Map(0 -> 3.0, 3 -> 7.0))

p1 + p2

List('a','b','c','d').combinations(3).toList

