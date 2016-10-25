package eu.busz.impatient.ch10

import java.io.{BufferedInputStream, FileInputStream}

trait BufferedTrait extends java.io.BufferedInputStream {

  val buffer = new Array[Byte](1024)
  var offset: Int = 0
  var length: Int = 0

  override def read(): Int = {
    if (offset == length) {
      super.read(buffer, offset, 1024)
      length += 1024
    }

    buffer(offset % 1024)
  }
}

trait TrueLogger {
  def log(msg: String)
}

trait BufferedFileStream extends TrueLogger {
  this: FileInputStream =>
  val bufferedStream = new BufferedInputStream(this)
  override def read(b: Array[Byte]): Int = {
    bufferedStream.read(b)
  }

  override def read() : Int = {
    val b = bufferedStream.read()
    this.log(b.toChar.toString)
    b
  }

  def log(msg: String): Unit = {
    print(msg)
  }
}

class IterableInputStream(bufferedFileStream: BufferedFileStream) extends java.io.InputStream with Iterable[Byte] {

  override def read(): Int = bufferedFileStream.read()

  override def iterator: Iterator[Byte] =  Iterator.continually(read().toByte).takeWhile(_ != -1)
}

object ex8 extends App {

  val f = new FileInputStream("C:\\tmp\\example\\src\\main\\scala\\common\\package.scala") with BufferedFileStream

  Iterator.continually(f.read()).takeWhile(_ != -1).map(_.toChar).mkString

}