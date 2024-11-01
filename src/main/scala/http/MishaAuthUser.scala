package ge.zgharbi.books
package http

import api.auth.*
import control.AuthControl

import sttp.tapir.ztapir.*
import zio.*

object MishaAuthUser {
  val login: ZServerEndpoint[AuthControl, Any] =
    UserLogin.userLogin
      .zServerLogic(req =>
        ZIO
          .serviceWithZIO[AuthControl](_.userLogin(req.email, req.password))
          .map(token => LoginResponse(token)),
      )
}
