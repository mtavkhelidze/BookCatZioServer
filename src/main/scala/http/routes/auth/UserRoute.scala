package ge.zgharbi.books
package http.routes.auth

import api.auth.UserApi
import control.AuthControl
import http.HttpError

import sttp.tapir.ztapir.*
import zio.*

object UserRoute {
  val login = UserApi.login.zServerLogic(AuthControl.userLogin(_, _))
}
