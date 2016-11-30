import scala.runtime.RichInt

//1
case class Pair[T,S](t: T, s: S) {

  def swap(): Pair[S, T] = {
    Pair(s, t)
  }
}

val p = new Pair("dupa", 10)
val p1 = p.swap()

//2
class Pair2[T](private var _t: T, private var _s: T) {

  def s = _s
  def t = _t

  def swap() = {
    val tmp = _s
    this._s = _t
    this._t = tmp
  }

  override def toString: String = {
    s"t=$t, s=$s"
  }
}

val p2 = new Pair2("left", "right")
p2.swap()
p2

//3
class Pair3[S,T](var s: S, var t: T) {

  def swap(p: Pair3[S, T]): Pair3[T, S] = {
    new Pair3(p.t, p.s)
  }

  def swap(): Pair3[T, S] = {
    new Pair3(t, s)
  }
}

//4
class Person {
  def toBe: Unit = {}
}
class Student extends Person {
  def toBeStudent: Unit = {}
}

class Pair4[T](val first: T, val second: T) {
  def replaceFirst(newFirst: T) = new Pair4[T](newFirst, second)
  def replaceFirstX[R >: T](newFirst: R) = {

    new Pair4[R](newFirst, second)
  }
}

new Pair4[Person](new Person, new Person).replaceFirst(new Student)
new Pair4[Person](new Person, new Person).replaceFirstX(new Student)
new Pair4[Student](new Student, new Student).replaceFirstX(new Person)

//5

val rc: Comparable[Int] = new RichInt(0)

//6
def middle[T](elems: Iterable[T]): T = {
  elems
    .drop(elems.size / 2)
    .take(1)
    .toList.head
}

middle("World")

//
class CovarianceTest[A](val a: A) {

  def smth(a: A): A = {
    a
  }

  def foldLeft[B](z: B)(op: (B, A) => B): B = {
    z
  }
}
//
//val cov = new CovarianceTest[Person](new Person)
//
//
//val c0: CovarianceTest[Student] = new CovarianceTest[Student](new Student)
//val c1: CovarianceTest[Person] = c0
//c1.smth(new Student)
//c1.smth(new Person)

//8
class Pair8[+T](val first: T, val second: T) {
  def replaceFirst[R >: T](newFirst: R) = {
    //first = newFirst //does not compile
  } //new Pair8[R](newFirst, second) // Error

}

//9


//10
