package eu.busz.fpmortals

import refined.api.Refined
import refined.string.Url

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


import jsonformat._, JsDecoder.ops._

