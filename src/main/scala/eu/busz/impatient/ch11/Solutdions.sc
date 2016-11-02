//1
// ((3 + 4) -(> 5))

//2
// Problem with operator precedence 5 * 4 ** 5 would be (5 * 4) ** 5
//3
class Fraction(n: Int, d: Int) {
  private val num: Int = if (d == 0) 1 else n * sign(d) / gcd(n, d);
  private val den: Int = if (d == 0) 0 else d * sign(d) / gcd(n, d);

  override def toString = num + "/" + den

  def sign(a: Int) = if (a > 0) 1 else if (a < 0) -1 else 0

  def gcd(a: Int, b: Int): Int = {
    if (b == 0) Math.abs(a)
    else gcd(b, a % b)
  }

  def *(that: Fraction): Fraction = {
    new Fraction(that.num * this.num, that.den * this.den)
  }

  def /(that: Fraction): Fraction = {
    new Fraction(that.den * this.num, that.num * this.den)
  }

  def +(that: Fraction): Fraction = {
    new Fraction(that.den * this.num + that.num * this.den,
      this.den * that.den)
  }

  def -(that: Fraction): Fraction = {
    new Fraction(that.den * this.num - that.num * this.den,
      this.den * that.den)
  }
}

//4
object Money {
  def apply(a: Int, r: Int): Money = {
    new Money(a, r)
  }

  def apply(f: Double): Money = {
    new Money(f.toInt, (f - f.toInt).toInt * 100)
  }
}

class Money(a: Int, r: Int) {

  def +(that: Money): Money = {
    Money.apply(this.toFloat() + that.toFloat())
  }

  def -(that: Money): Money = {
    Money.apply(this.toFloat() - that.toFloat())
  }

  def ==(that: Money) = that.toFloat() == this.toFloat()
  def < (that: Money) = this.toFloat() < that.toFloat()

  private def toFloat(): Double = this.a + this.r / 100.0

}

//5
class TableBuilder {

  var value = ""

  def |(element: String): TableBuilder = {
    if (value.isEmpty) value += "<tr>"
    value += s"<td>$element</td>"
    this
  }

  def ||(element: String): TableBuilder = {
    value += s"</tr><tr><td>$element</td>"
    this
  }

  override def toString: String = {
    s"<table>$value</tr></table>"
  }
}

object TableBuilder {
  def apply(): TableBuilder = {
    new TableBuilder
  }
}

TableBuilder() | "Java" | "Dupa" || "dupa2" | "jazda"

class ImmutableTable(table: List[List[String]]) {
  def |(element: String): ImmutableTable = {
    new ImmutableTable(table.init :+ (table.last :+ element))
  }

  def ||(element: String): ImmutableTable = {
    new ImmutableTable(table :+ (element :: Nil))
  }

  override def toString: String = {
    table.flatMap(row => ("<tr>" + row.map("<td>" + _ + "</td>").mkString("") + "</tr>")).mkString("")
  }
}

object ImmutableTable {
  def apply(): ImmutableTable = new ImmutableTable(List(List()))
}

ImmutableTable() | "Java" | "Dupa" || "dupa2" | "jazda"

class AsciiArt(val art: String) {

  def +(asciiArt: AsciiArt): AsciiArt = {
    val left = art.split("\n")
    val right = asciiArt.art.split("\n")

    left.zip(right).map((l: Array[Char],r: Array[Char]) => "")
  }

  def |(asciiArt: AsciiArt): AsciiArt = {
    new AsciiArt(asciiArt.art + this.art)
  }

}
