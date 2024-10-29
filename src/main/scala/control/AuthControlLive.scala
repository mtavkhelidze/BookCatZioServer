package ge.zgharbi.books
package control

import domain.{DomainError, Email, JwtToken, Password}

import ge.zgharbi.books.domain.DomainError.InvalidCredentials
import zio.*

private[control] case class AuthControlLive() extends AuthControl {

  override def userLogin(
    email: Email,
    password: Password,
  ): ZIO[Any, DomainError, JwtToken] = ZIO.fail(InvalidCredentials())
}
