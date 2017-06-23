object implicitfun {

  implicit val x: Int = 0

  def smth(y: Int) = y

 // smth
}

val len: String => Int = _.length
val res3 = List("scala", "cats").map(s => s -> len(s))
res3.toMap