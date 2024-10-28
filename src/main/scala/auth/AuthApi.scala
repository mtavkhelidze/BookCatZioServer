/*
 * © 2024 Misha Tavkhelidze. Some rights reserved.
 */

package ge.zgharbi.books
package auth

import common.BaseEndpoints
import domain.DomainError

import sttp.model.StatusCode
import sttp.tapir.*
import sttp.tapir.json.jsoniter.jsonBody

import scala.reflect.ClassTag
trait AuthApi extends BaseEndpoints {

  import codecs.given
  import DomainError.*

  val loginEndpoint
    : Endpoint[Unit, LoginRequest, DomainError, LoginResponse, Any] = {
    publicEndpoint
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
  }
}
