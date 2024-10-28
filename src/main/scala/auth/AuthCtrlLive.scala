/*
 * © 2024 Misha Tavkhelidze. Some rights reserved.
 */

package ge.zgharbi.books
package auth

import common.*
import domain.DomainError.InvalidCredentials
import services.auth.{AuthCreds, AuthService, AuthUser}

import sttp.tapir.ztapir.*
import zio.*

extension (au: AuthUser)
  def asResponse: LoginResponse = LoginResponse(au.jwtToken)
extension (req: LoginRequest)
  def asCreds: AuthCreds = AuthCreds(req.email, req.password)

private class AuthCtrlLive(authService: AuthService)
    extends BaseController
    with AuthApi {

  val login: ZServerEndpoint[Any, Any] =
    loginEndpoint
      .zServerLogic { req =>
        authService
          .authenticate { req.asCreds }
          .map(_.asResponse)
          .mapError { case x =>
//            JsonDecodeFailure()
            InvalidCredentials()
          }
          .flatMap(ZIO.succeed)
      }

  def routes: List[ZServerEndpoint[Any, Any]] = List(login)
}

object AuthCtrlLive {
  val live: ZIO[AuthService, Nothing, AuthCtrlLive] =
    for {
      authService <- ZIO.service[AuthService]
      controller = new AuthCtrlLive(authService)
    } yield controller
}
