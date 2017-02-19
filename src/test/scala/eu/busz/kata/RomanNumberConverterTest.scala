package scala.eu.busz.kata

import org.junit.runner.RunWith
import org.scalatest.{BeforeAndAfter, FunSuite, Matchers}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class RomanNumberConverterTest extends FunSuite with BeforeAndAfter with Matchers {

  var rn: RomanNumberConverter = _
  before {
    rn = new RomanNumberConverter
  }

  test("Returns roman number for '1'") {
    rn.convert(1) should be ("I")
  }

  test("Returns roman number from 1 to 3") {
    rn.convert(1) should be ("I")
    rn.convert(2) should be ("II")
    rn.convert(3) should be ("III")
  }

  test("Returns roman number for '5'") {
    rn.convert(5) should be ("V")
  }

  test("Returns roman number from 6 to 8") {
    rn.convert(6) should be ("VI")
    rn.convert(7) should be ("VII")
    rn.convert(8) should be ("VIII")
  }

  test("Returns roman number for larger input which does not require subtraction") {
    rn.convert(26) should be ("XXVI")
    rn.convert(56) should be ("LVI")
    rn.convert(56) should be ("LVI")
    rn.convert(171) should be ("CLXXI")
    rn.convert(671) should be ("DCLXXI")
    rn.convert(1671) should be ("MDCLXXI")
  }

  test("Returns roman number for 4 which requires subtraction") {
    rn.convert(4) should be ("IV")
  }

  test("Returns roman number with subtraction notation") {
    rn.convert(4) should be("IV")
    rn.convert(9) should be("IX")
    rn.convert(40) should be("XL")
    rn.convert(90) should be("XC")
    rn.convert(400) should be("CD")
    rn.convert(900) should be("CM")
  }

  test("Returns roman number for large examples") {
    rn.convert(2014) should be ("MMXIV")
    rn.convert(1990) should be ("MCMXC")
    rn.convert(1954) should be ("MCMLIV")
    rn.convert(1989) should be ("MCMLXXXIX")
  }
    //  test("Returns roman number for '4'") {
//    rn.convert(4) should be ("IV")
//  }

//  test("Returns roman number for for: 5, 10, 50, 100, 1000") {
//    rn.convert(5) should be ("V")
//    rn.convert(10) should be ("X")
//    rn.convert(50) should be ("L")
//    rn.convert(100) should be ("C")
//    rn.convert(1000) should be ("M")
//  }

}
