sealed trait TrafficLight
final case object Red extends TrafficLight
final case object Green extends TrafficLight
final case object Yellow extends TrafficLight


object TrafficLight {
  def nextLight(light: TrafficLight): TrafficLight = light match {
    case Red => Green
    case Green => Yellow
    case Yellow => Red
  }
}


object OO {

  trait TrafficLight {
    def nextLight: TrafficLight
  }

  final case object Red extends TrafficLight {
    override def nextLight: TrafficLight = Green
  }

  final case object Green extends TrafficLight {
    override def nextLight: TrafficLight = Yellow
  }

  final case object Yellow extends TrafficLight {
    override def nextLight: TrafficLight = Green
  }

}