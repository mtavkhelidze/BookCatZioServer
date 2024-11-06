package ge.zgharbi.books
package control

import api.InvalidCredentialsError
import domain.{Email, JwtToken, Password}

import zio.*

trait AuthControl {
  def userLogin: (
    email: Email,
    password: Password,
  ) => ZIO[AuthControl, InvalidCredentialsError, JwtToken]
}

object AuthControl {

  val live: ULayer[AuthControl] = ZLayer.succeed(AuthControlLive)

  def userLogin(
    email: Email,
    pass: Password,
  ): ZIO[AuthControl, InvalidCredentialsError, JwtToken] =
    ZIO.serviceWithZIO[AuthControl](_.userLogin(email, pass))
}

private object AuthControlLive extends AuthControl {

  override def userLogin = (email, password) =>
    ZIO.fail(InvalidCredentialsError())
}
