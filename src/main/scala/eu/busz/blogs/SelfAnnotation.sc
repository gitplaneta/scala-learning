


trait LogX {
  this: {def getMessage: String} =>

  def log(): Unit = {
    println(getMessage)
  }
}

trait MessageGenerator {
  def getMessage: String = "Some message"
}

object LogX extends LogX with MessageGenerator

LogX.log()