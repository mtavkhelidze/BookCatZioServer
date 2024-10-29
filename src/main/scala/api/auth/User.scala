/*
 * © 2024 Misha Tavkhelidze. Some rights reserved.
 */

package ge.zgharbi.books
package api.auth

import api.*
import domain.{DomainError, Email, JwtToken, Password}
import domain.DomainError.*

import sttp.model.StatusCode
import sttp.tapir.json.jsoniter.jsonBody
import sttp.tapir.ztapir.*
import sttp.tapir.Endpoint

object User {
  import api.auth.user.given

  val login: Endpoint[Unit, LoginRequest, DomainError, LoginResponse, Any] =
    endpoint
      .tag("Auth")
      .in("auth" / "user" / "login")
      .in(jsonBody[LoginRequest])
      .out(jsonBody[LoginResponse])
      .errorOut(
        oneOf[DomainError](
          errorVariant(StatusCode.Unauthorized, InvalidCredentials()),
          errorVariant(StatusCode.UnprocessableEntity, JsonDecodeFailure()),
        ),
      )

  final case class LoginRequest(email: Email, password: Password)

  final case class LoginResponse(jwtToken: JwtToken)
}
