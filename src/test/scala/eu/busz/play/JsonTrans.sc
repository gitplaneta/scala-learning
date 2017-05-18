import play.api.libs
import play.api.libs.json.{JsPath, Json}
import libs.json._

val jsonString = """{
                   |  "key1" : "value1",
                   |  "key2" : {
                   |    "key21" : 123,
                   |    "key22" : true,
                   |    "key23" : [ "alpha", "beta", "gamma"],
                   |    "key24" : {
                   |      "key241" : 234.123,
                   |      "key242" : "value242"
                   |    }
                   |  },
                   |  "key3" : 234
                   |}""".stripMargin
val json = Json.parse(jsonString)

val transformer = (__ \ 'key2 \ 'key23).json.pick
json.transform(transformer) match {
  case JsSuccess(x, _) => x
}




