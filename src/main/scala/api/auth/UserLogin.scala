/*
 * © 2024 Misha Tavkhelidze. Some rights reserved.
 */

package ge.zgharbi.books
package api.auth

import domain.{Email, JwtToken, Password}
import domain.config.{jsonIterConfig, schemaConfig}
import http.HttpError
import http.HttpError.*
import api.errorVariant

import sttp.tapir.{Endpoint, Schema}
import sttp.tapir.json.jsoniter.jsonBody
import sttp.tapir.ztapir.*

case class LoginRequest(email: Email, password: Password)

case class LoginResponse(jwtToken: JwtToken)

object UserLogin {
  import codecs.given

  val userLogin: Endpoint[Unit, LoginRequest, HttpError, LoginResponse, Any] =
    endpoint
      .tag("Auth")
      .in("auth" / "user" / "login")
      .in(jsonBody[LoginRequest])
      .out(jsonBody[LoginResponse])
      .errorOut(
        oneOf[HttpError](
          errorVariant[InvalidCredentials],
          errorVariant[JsonDecodeFailure],
        ),
      )
  val routes = List(userLogin)
}

private object codecs {
  given Schema[LoginRequest] =
    Schema.derived[LoginRequest](using schemaConfig)

  given Schema[LoginResponse] =
    Schema.derived[LoginResponse](using schemaConfig)

  import com.github.plokhotnyuk.jsoniter_scala.core.JsonValueCodec
  import com.github.plokhotnyuk.jsoniter_scala.macros.JsonCodecMaker

  given JsonValueCodec[LoginRequest] =
    JsonCodecMaker.make[LoginRequest](jsonIterConfig)

  given JsonValueCodec[LoginResponse] =
    JsonCodecMaker.make[LoginResponse](jsonIterConfig)

}
