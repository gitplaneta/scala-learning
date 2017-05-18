package eu.busz.play

import org.scalatest.{FunSpec, FunSuite}
import play.api.libs.json.Json

class JsonTransformer extends FunSuite {

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

  test("Case 1: Pick JSON value in JsPath"){
    
  }

}
