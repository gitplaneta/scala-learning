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

