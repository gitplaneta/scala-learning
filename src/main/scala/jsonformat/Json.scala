package jsonformat

import scalaz.{IList, \/}
import simulacrum.typeclass

class Json {

}

sealed abstract class JsValue
final case object JsNull extends JsValue
final case class JsObject(fields: IList[(String, JsValue)])
final case class JsArray(fields: IList[JsValue])
final case class JsBoolean(value: Boolean)
final case class JsString(value: String)
final case class JsDouble(value: Double)
final case class JsInteger(value: Integer)

@typeclass trait JsEncoder[A] {
  def toJson(obj: A): JsValue
}

@typeclass trait JsDecoder[A] {
  def fromJson(json: JsValue): String \/ A
}