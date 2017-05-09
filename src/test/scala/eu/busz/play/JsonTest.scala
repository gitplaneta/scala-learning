package eu.busz.play

import org.junit.runner.RunWith
import org.scalatest.{BeforeAndAfter, FunSuite, Matchers}
import org.scalatest.junit.JUnitRunner
import play.api.libs.json.{JsPath, Json, Writes}
import play.api.libs.json._
import play.api.libs.functional.syntax._

@RunWith(classOf[JUnitRunner])
class JsonTest extends FunSuite with BeforeAndAfter with Matchers {

  val jsonString: String =
    """{
      |  "name" : "Watership Down",
      |  "location" : {
      |    "lat" : 51.235685,
      |    "long" : -1.309197
      |  },
      |  "residents" : [ {
      |    "name" : "Fiver",
      |    "age" : 4,
      |    "role" : null
      |  }, {
      |    "name" : "Bigwig",
      |    "age" : 6,
      |    "role" : "Owsla"
      |  } ]
      |}""".stripMargin
  val json = Json.parse(jsonString)

  test("simple writer based on case classes, without custom field names") {
    implicit val locationWrites: Writes[Location] = Json.writes[Location]
    println(Json.toJson(Location(2.3, 2.0)))
  }

  test("combinator pattern writes") {
    implicit val residentsWriter: Writes[Resident] = (
      (JsPath \ "name").write[String] and
        (__ \ "age").write[Int] and
        (__ \ "role").writeNullable[String]
      ) (unlift(Resident.unapply))

    println(Json.toJson(Resident("name", 10, Option.empty))(residentsWriter))
  }

  /** **
    * Traversing / Reading json to model
    */

  test("traversing json structure") {
    println((json \ "residents") (1) \ "name" get)
    println(json \ "name" get)

    println("done")
  }

  test("basic readers") {
    println((json \ "name").as[String])
    println((json \ "name").asOpt[String])

    println("basic readers done")
  }

  test("reading and validating") {
    val nameResult: JsResult[String] = (json \ "name").validate[String]

    // Pattern matching
    nameResult match {
      case s: JsSuccess[String] => println("Name: " + s.get)
      case e: JsError => println("Errors: " + JsError.toJson(e).toString())
    }

    // folding - applying invalid or valid function depending on the validation result
    val x: Option[String] = nameResult.fold({ fieldErrors => fieldErrors.foreach(x => println(x._1)); None }, name => Some(name))
  }

  test("Converting to model by using validation") {
    val residdentReads: Reads[Resident] = (
      (__ \ "name").read[String] and
        (__  \ "age").read[Int] and
        (__  \ "role").readNullable[String]
      ) (Resident.apply _)

    println(json.validate[Seq[Resident]]((JsPath \ "residents").read[Seq[Resident]](Reads.seq(residdentReads))))
    println((json \ "residents")(1).validate[Resident](residdentReads))

    val locationReads: Reads[Location] = (
      (JsPath \ "location" \ "lat").read[Double] and
        (JsPath \ "location" \ "long").read[Double]
      ) (Location.apply _)

    val fullLocation: Reads[Location] = ((JsPath \ "location").read[Location]) (locationReads)

    println(json.validate[Location](locationReads))
  }


}

case class Location(lat: Double, long: Double)

case class Resident(name: String, age: Int, role: Option[String])

case class Place(name: String, location: Location, residents: Seq[Resident])

case class NewString(str: String)