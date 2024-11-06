/*
 * © 2024 Misha Tavkhelidze. Some rights reserved.
 */

package ge.zgharbi.books
package api.auth

import api.{ApiError, InvalidCredentialsError}
import domain.{Email, JwtToken, Password}
import domain.config.{jsonIterConfig, schemaConfig}

import sttp.model.StatusCode
import sttp.tapir.{Endpoint, Schema}
import sttp.tapir.json.jsoniter.jsonBody
import sttp.tapir.ztapir.*

import scala.reflect.classTag

case class LoginRequest(email: Email, password: Password)

case class LoginResponse(jwtToken: JwtToken)

object UserApi {
  import codecs.given

  val login: Endpoint[Unit, LoginRequest, ApiError, LoginResponse, Any] =
    endpoint.post
      .tag("Auth")
      .in("auth" / "user" / "login")
      .in(jsonBody[LoginRequest])
      .out(jsonBody[LoginResponse])
      .errorOut(
        oneOf(
          oneOfVariantClassMatcher(
            statusCode(StatusCode.UnprocessableEntity)
              .and(
                jsonBody[ApiError]
                  .default(ApiError.exmapleOf[InvalidCredentialsError]),
              ),
            classTag[InvalidCredentialsError].runtimeClass,
          ),
        ),
      )
      .mapErrorOut(x => x)(x => x)
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
