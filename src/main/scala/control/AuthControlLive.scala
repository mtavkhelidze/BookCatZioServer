package ge.zgharbi.books
package control

import domain.{Email, JwtToken, Password}
import http.HttpError
import http.HttpError.InvalidCredentials

import zio.*

private[control] case class AuthControlLive() extends AuthControl {

  override def userLogin(
    email: Email,
    password: Password,
  ): ZIO[Any, HttpError, JwtToken] = ZIO.fail(InvalidCredentials(Some("this is a dummy error")))
}
