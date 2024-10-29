package ge.zgharbi.books
package routes

import api.auth.User
import control.AuthControl

import sttp.tapir.ztapir.*
import zio.*

object AuthUser {
  val login =
    User.login
      .zServerLogic(req =>
        ZIO
          .serviceWithZIO[AuthControl](_.userLogin(req.email, req.password))
          .map(token => User.LoginResponse(token)),
      )
}
