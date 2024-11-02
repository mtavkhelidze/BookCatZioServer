/*
 * © 2024 Misha Tavkhelidze. Some rights reserved.
 */

package ge.zgharbi.books
package api.auth

import api.errorVariant
import domain.{Email, JwtToken, Password}
import domain.config.{jsonIterConfig, schemaConfig}
import http.HttpError
import http.HttpError.*

import sttp.tapir.{Endpoint, Schema}
import sttp.tapir.json.jsoniter.jsonBody
import sttp.tapir.ztapir.*

case class LoginRequest(email: Email, password: Password)

case class LoginResponse(jwtToken: JwtToken)

object UserApi {
  import codecs.given

  val login: Endpoint[Unit, (Email, Password), HttpError, JwtToken, Any] =
    endpoint
      .post
      .tag("Auth")
      .in("auth" / "user" / "login")
      .in(jsonBody[LoginRequest])
      .mapIn(req => (req.email, req.password))(LoginRequest.apply)
      .out(jsonBody[LoginResponse])
      .mapOut(_.jwtToken)(token => LoginResponse(token))
      .errorOut(
        oneOf[HttpError](
          errorVariant[InvalidCredentialsError],
          errorVariant[JsonDecodeFailureError],
        ),
      )
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
