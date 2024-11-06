package ge.zgharbi.books
package http.routes.auth

import api.auth.{LoginResponse, UserApi}
import control.AuthControl

import sttp.tapir.ztapir.*
object UserRoute {
  val login: ZServerEndpoint[AuthControl, Any] =
    UserApi.login.zServerLogic(req => {
      AuthControl
        .userLogin(req.email, req.password)
        .map(LoginResponse(_))
        .mapError(e => e.copy(details = List("problem with login")))
    })
}
