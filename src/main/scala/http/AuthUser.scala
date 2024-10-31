package ge.zgharbi.books
package http

import control.AuthControl
import http.api.auth.User

import sttp.tapir.ztapir.*
import zio.*

object AuthUser {
  val login: ZServerEndpoint[AuthControl, Any] =
    User.login
      .zServerLogic(req =>
        ZIO
          .serviceWithZIO[AuthControl](_.userLogin(req.email, req.password))
          .map(token => User.LoginResponse(token)),
      )
}
