/*
 * © 2024 Misha Tavkhelidze. Some rights reserved.
 */

package ge.zgharbi.books
package services.live

import domain.{JwtToken, UserId}
import services.auth.{AuthCreds, AuthError, AuthService, AuthUser}

import zio.*

class DummyAuthService extends AuthService {
  override def authenticate(creds: AuthCreds): IO[AuthError, AuthUser] =
    ZIO.succeed(AuthUser(UserId(1), JwtToken("dummy-jwtToken")))
//    ZIO.fail(UserAlreadyExists)

  override def register(creds: AuthCreds): IO[AuthError, UserId] =
    ???
}

object DummyAuthService {
  val layer: ZLayer[Any, Nothing, AuthService] =
    ZLayer.succeed(new DummyAuthService)
}
