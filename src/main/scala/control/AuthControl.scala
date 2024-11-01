package ge.zgharbi.books
package control

import domain.{Email, JwtToken, Password}

import ge.zgharbi.books.http.HttpError
import zio.*

trait AuthControl {
  def userLogin
    : (email: Email, password: Password) => ZIO[Any, HttpError, JwtToken]
}

object AuthControl {
  val live: ULayer[AuthControl] = ZLayer.succeed(AuthControlLive())

  def userLogin(email: Email, pass: Password) =
    ZIO.serviceWithZIO[AuthControl](_.userLogin(email, pass))
}

private final case class AuthControlLive() extends AuthControl {
  override def userLogin = (email, password) =>
    ZIO.fail(HttpError.InvalidCredentials(Some("a problem")))
}
