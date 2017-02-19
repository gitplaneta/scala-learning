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
    if (remaining >= D.arabic + C.arabic * 4) {
      res += C.roman + M.roman
      remaining -= M.arabic - C.arabic
    }
    res += partialRoman(D)
    if (remaining >= C.arabic * 4) {
      res += C.roman + D.roman
      remaining -= D.arabic - C.arabic
    }
    res += partialRoman(C)
    if (remaining >= L.arabic + X.arabic * 4) {
      res += X.roman + C.roman
      remaining -= C.arabic - X.arabic
    }
    res += partialRoman(L)
    if (remaining >= X.arabic * 4) {
      res += X.roman + L.roman
      remaining -= L.arabic - X.arabic
    }
    res += partialRoman(X)
    if (remaining == (V.arabic + I.arabic * 4)) {
      res += I.roman + X.roman
      remaining -= X.arabic - I.arabic
    }
    res += partialRoman(V)
    if (remaining == I.arabic * 4) {
      res += I.roman + V.roman
      remaining -= V.arabic - I.arabic
    }
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
