import TrafficLight.Green

object TrafficLight extends Enumeration {
  type TrafficLight = Value

  val Red, Yellow, Green = Value
}

val light = Green

var b: Boolean = _
b = true