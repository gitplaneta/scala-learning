package eu.busz.impatient.ch11

class BitSequence private(val binValue: Long) {

}

object BitSequence {
  def apply(bits: String): BitSequence = {
    var value: Long = 0
    for (i <- 0 until bits.length) {
      val bit: Int = bits.charAt(i).asDigit
      assert(bit == 0 || bit == 1)

      value |= bit << i
    }
    new BitSequence(value)
  }

  def unapply(bits: Long): Option[String] = {
    Some(bits.toBinaryString)
  }
}

object Main extends App {
  println(BitSequence("000111010101").binValue)
}