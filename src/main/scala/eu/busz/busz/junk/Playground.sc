object Main {
  def foo[T <: Any](x: Array[T]): String = ":)"

  def foo(x : Array[T] forSome { type T <: String}) = {
    x.foreach(y => println(y.length))
  }

  def foo2(x: Array[_ <: String]) = {
    x
  }

  def foo3(x: Array[T] forSome { type T }) = {
    x
  }
  def foo4(x: Array[T forSome { type T }]) = {
    x
  }
}

//Main.foo(Array[Nothing]())
//Main.foo(Array[String]())

"dupa"


var a: Map[Class[T forSome { type T}], String] = ???
var b: Map[Class[T] forSome { type T}, String]= ???
var c: Map[Class[T], String] forSome { type T}= ???



object Main2 {

  trait Buffer {
    type T
    val element: T
  }

  abstract class SeqBuffer extends Buffer {
    type U
    type T <: Seq[U]
    def length = element.length
  }

  abstract class IntSeqBuffer extends SeqBuffer {
    type U = Int
  }

  def foo = {
    def newIntSeqBuf(elem1: Int, elem2: Int): IntSeqBuffer =
      new IntSeqBuffer {
        type T = List[U]
        val element = List(elem1, elem2)
      }
    val buf = newIntSeqBuf(7, 8)
    println("length = " + buf.length)
    println("content = " + buf.element)
  }

  trait Buffer2[T] {
    var element: T
  }

  abstract class SeqBuffer2[U, T <: Seq[U]] extends Buffer2[T] {
    def length = element.length
  }

  abstract class IntSeqBuffer2 extends SeqBuffer2[Int, Seq[Int]] {
  }

  def foo2 = {
    def newIntSeqBuf2(elem1: Int, elem2: Int): IntSeqBuffer2 =
      new IntSeqBuffer2 {
        val element = List(elem1, elem2)
      }
    val buf = newIntSeqBuf2(7, 8)
    println("length = " + buf.length)
    println("content = " + buf.element)
  }

}