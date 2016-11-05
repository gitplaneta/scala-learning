class RichFile(val path: String) {
  private val dotSplitted = path.split("\\.")
  private val slashSplitted = path.split("\\/")

  val ext = dotSplitted(dotSplitted.length - 1)
  val fileName = slashSplitted(slashSplitted.length - 1)
  val filePath = slashSplitted.dropRight(1).reduce(_ + "/" + _)
}

object RichFile {
//  def unapply(arg: RichFile) = {
//    Some((arg.ext, arg.fileName, arg.filePath))
//  }


  def unapplySeq(arg: RichFile): Option[Seq[String]] = {
    Some(arg.slashSplitted)
  }
}

val RichFile(a, b,c,d, _*) = new RichFile("C:/file/that/might/exist/text.txt")