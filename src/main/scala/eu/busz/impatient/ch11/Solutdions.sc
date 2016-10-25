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
    new Fraction(that.num / this.num, that.den / this.den)
  }


}
