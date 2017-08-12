
trait Printable[A] {
  def format(value: A): String

  def contramap[B](func: B => A): Printable[B] = {
    val self = this
    (value: B) => self.format(func(value))
  }
}

def formatX[A](value: A)(implicit p: Printable[A]): String =
  p.format(value)

implicit val stringPrintable = new Printable[String] {
    override def format(value: String): String = "\"" + value + "\""
  }

implicit val boolPrintable = new Printable[Boolean] {
  override def format(value: Boolean): String = if (value) "yes" else "no"
}

formatX("hello")
formatX(true)

// Task:
//box
final case class Box[A](value: A)
implicit def unboxFormat[A](implicit p: Printable[A]): Printable[Box[A]] = {
  p.contramap[Box[A]](box => box.value)
}


formatX(Box("hello there, mr box"))

//INVARIANT FUNCTORS:
trait Codec[A] {
  def encode(value: A): String
  def decode(value: String): Option[A]

  def imap[B](dec: A => B, enc: B => A): Codec[B] = {
    val self = this
    new Codec[B] {
      override def encode(value: B): String = self.encode(enc(value))

      override def decode(value: String): Option[B] = self.decode(value).map(dec)
    }
  }
}

def encode[A](value: A)(implicit c: Codec[A]): String =
  c.encode(value)
def decode[A](value: String)(implicit c: Codec[A]): Option[A] =
  c.decode(value)

implicit val intCodec =
  new Codec[Int] {
    def encode(value: Int): String =
      value.toString
    def decode(value: String): Option[Int] =
      scala.util.Try(value.toInt).toOption
  }

//Codec for boxes task:
implicit def boxToCodec[A](implicit c: Codec[A]): Codec[Box[A]] =
  c.imap[Box[A]](Box(_), _.value)

encode(Box(123))
decode[Box[Int]]("1234")


object catsContravariant {
  import cats.Show
  import cats.functor.Contravariant
  import cats.instances.string._

  val showString = Show[String]

  val showSymbol = Contravariant[Show].
    contramap(showString)((sym: Symbol) => s"'${sym.name}")


  showSymbol.show(Symbol("Dave"))
}
//JUNK:

//implicit def boxPrintable2[A <: Printable[A]]: Printable[Box[A]] = (value: Box[A]) => {
//  value.value.format(value.value)
//}
//
//formatX(Box("gowno"))(boxPrintable2[String])

//implicit val boxPrintable: Printable[Box[_ <: Printable[_]]] = new Printable[Box[_ <: Printable[_]]] {
//  override def format(value: Box[_ <: Printable[_]]): String = {
//    val boxed = value.value
//    boxed.format(boxed.)
//  }
//}
//
//formatX(Box("hello yall"))
//formatX(Box(true))