package ge.zgharbi.books
package http.routes.auth

import api.auth.UserApi
import http.HttpError

import sttp.tapir.ztapir.*
import zio.*

object UserRoute {

  val login =
    UserApi.login
      .zServerLogic(x => ZIO.fail(HttpError.InvalidCredentials()))
      .widen[Any]
}
