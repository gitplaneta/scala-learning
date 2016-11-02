//7
1 << 0
class BitSequence private(var binValue: Long) {
  var bVal: Int = _

  def apply(bitsxxx: String) = ???
}

object BitSequence {
  def apply(bits: String): BitSequence = {
    var value: Long = 0
    for (i <- 0 until bits.length) {
      val bit: Int = bits.charAt(i).asDigit
      assert(bit == 0 || bit == 1)

      value |= bit << (i)
    }
    new BitSequence(value)
  }

  def update(mask: Int, v: Boolean): BitSequence = {

    binValue = if (v) (binValue | (1 << mask))
  }

  def unapply(bits: Long): Option[String] = {
    Some(bits.toBinaryString)
  }
}

BitSequence("000111010101").binValue
BitSequence("1011").binValue
BitSequence("1011").binValue
val BitSequence(a) = 13L
