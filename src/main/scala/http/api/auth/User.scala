/*
 * © 2024 Misha Tavkhelidze. Some rights reserved.
 */

package ge.zgharbi.books
package http.api.auth

import domain.{Email, JwtToken, Password}
import http.HttpError
import http.HttpError.*
import http.api.errorVariant

import sttp.model.StatusCode
import sttp.tapir.json.jsoniter.jsonBody
import sttp.tapir.ztapir.*
import sttp.tapir.Endpoint

object User {

  val login: Endpoint[Unit, LoginRequest, HttpError, LoginResponse, Any] =
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

  final case class LoginRequest(email: Email, password: Password)

  final case class LoginResponse(jwtToken: JwtToken)
}
