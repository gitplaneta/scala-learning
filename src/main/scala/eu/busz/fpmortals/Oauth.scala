package eu.busz.fpmortals

import eu.busz.fpmortals.fommil.fommil.http.encoding.UrlEncoded
import eu.timepit.refined.api.Refined
import eu.timepit.refined.string.Url
import scalaz.{IList, \/}
import simulacrum.typeclass

object Oauth extends App {
  println("asdf")

}

final case class AuthRequest(redirect_uri: String Refined Url,
                             scope: String,
                             clinet_id: String,
                             prompt: String = "consent",
                             response_type: String = "code",
                             access_type: String = "offline")

final case class AccessRequest(
                                code: String,
                                redirect_url: String Refined Url,
                                client_id: String,
                                client_secret: String,
                                scope: String = "",
                                grant_type: String = "authorization_code"
                              )

final case class AccessResponse(
                                 access_token: String,
                                 token_type: String,
                                 expires_in: Long,
                                 refresh_token: String
                               )

final case class RefreshRequest(
                                 client_secret: String,
                                 refresh_token: String
                               )

final case class RefreshResponse(
                                  access_token: String,
                                  token_type: String,
                                  expires_in: Long
                                )


import jsonformat._, JsDecoder.ops._

object AccessResponse {

  implicit class JsValueOps(j: JsValue) {
    def getAs[A: JsDecoder](key: String): String \/ A = ???
  }

  implicit val json: JsDecoder[AccessResponse] = j => for {
    acc <- j.getAs[String]("access_token")
    tpe <- j.getAs[String]("token_type")
    exp <- j.getAs[Long]("expires_in")
    ref <- j.getAs[String]("refresh_token")
  } yield AccessResponse(acc, tpe, exp, ref)
}

object RefreshResponse {

  implicit class JsValueOps(j: JsValue) {
    def getAs[A: JsDecoder](key: String): String \/ A = ???
  }

  implicit val json: JsDecoder[RefreshResponse] = j =>
    for {
      acc <- j.getAs[String]("access_token")
      tpe <- j.getAs[String]("access_token")
      exp <- j.getAs[Long]("access_token")
    } yield RefreshResponse(acc, tpe, exp)
}

final case class UrlQuery(params: List[(String, String)])

@typeclass trait UrlQueryWriter[A] {
  def toUrlQuery(a: A): UrlQuery
}

@typeclass trait UrlEncodedWriter[A] {
  def toUrlEncoded(a: A): String Refined UrlEncoded
}

import java.net.URLEncoder

object UrlEncodedWriter {
  implicit val encoded = UrlEncodedWriter[String Refined UrlEncoded] = identity

  implicit val string: UrlEncodedWriter[String] = (s => Refined.unsafeApply(URLEncoder.encode(s, "UTF-8")))

  implicit val long: UrlEncodedWriter[Long] = (s => Refined.unsafeApply(s.toString))

  implicit def ilist[K: UrlEncodedWriter, V: UrlEncodedWriter]: UrlEncodedWriter[IList[(K, V)]] = { m =>
    val raw = m.map {
      case (k, v) => k.toUrlEncoded.value + "=" + v.toUrlEncoded.value
    }.intercalate("&")
    Refined.unsafeApply(raw) // by deduction
  }
}