//1
object Conversions {
  def inchesToCentimeters(inches: Int) = inches * 2.54
  def gallonsToLiters(gallons: Int) = gallons * 4.54
  def milesToKilometers(miles: Int) = miles / 1.5
}

//2
class UnitConversion(val unit: Double) {
  private def convert(value: Double): Double = {
    value * unit
  }
}

class Unit2C(unit: Double) extends UnitConversion(unit) {

}

object InchesToCentimeters extends UnitConversion(2.54) {

}

//3
object Origin extends java.awt.Point {

}

case class Point(x: Int, y: Int)
Point(0,0)

object Main extends App {
  this.args.reverse.foreach(println(_))
}