import main.scala.eu.busz.busz.coursera.second.week1.{JObj, JSON}
{ case (key, value) => key + ":" + value} : (String, String) => String

{ case (key, value) => key + ":" + value} : Function1[(String, String), String]

val f: PartialFunction[String, String] = { case "pong" => "ping"}
f.isDefinedAt("ping") //false
f.isDefinedAt("pong") //true

val data: List[JSON] = ???

for {
  JObj(bindings) <- data
} yield ""