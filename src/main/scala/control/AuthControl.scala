package ge.zgharbi.books
package control

import domain.{Email, JwtToken, Password}
import http.HttpError

import zio.*

trait AuthControl {
  def userLogin(email: Email, password: Password): ZIO[Any, HttpError, JwtToken]
}

object AuthControl {
  val layer: ULayer[AuthControl] = ZLayer.succeed(AuthControlLive())
}
