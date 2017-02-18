package scala.eu.busz.kata

class RomanNumberConverter {
  def convert(num: Int): String = {
    var res = ""
    var remaining = num

    def partialRoman(roman: RomanNumber): String = {
      var romanString = ""
      while (remaining - roman.arabic >= 0) {
        romanString += roman.roman
        remaining -= roman.arabic
      }
      romanString
    }

    res += partialRoman(M)
    res += partialRoman(D)
    res += partialRoman(C)
    res += partialRoman(L)
    res += partialRoman(X)
    res += partialRoman(V)
    res += partialRoman(I)
    res
  }

}

sealed abstract class RomanNumber(val arabic: Int, val roman: String)

case object I extends RomanNumber(1, "I")
case object V extends RomanNumber(5, "V")
case object X extends RomanNumber(10, "X")
case object L extends RomanNumber(50, "L")
case object C extends RomanNumber(100, "C")
case object D extends RomanNumber(500, "D")
case object M extends RomanNumber(1000, "M")
