package ge.zgharbi.books
package http.routes.auth

import api.auth.UserApi
import control.AuthControl

import sttp.tapir.ztapir.*

object UserRoute {
  val login = UserApi.login.zServerLogic(AuthControl.userLogin(_, _))
}
