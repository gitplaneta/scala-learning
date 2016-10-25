class Foo(z: Int) {
  val y = 20
  private[this] var x = 0

  // getter supplied for free, because i defined the explicit setter

  def x_= (xv: Int) {
    z = 10
    require(xv > 14)
    x = xv
  }
}

val foo = new Foo(10)
foo.y
foo.z
