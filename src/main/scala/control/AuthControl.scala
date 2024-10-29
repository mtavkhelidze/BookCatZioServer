package ge.zgharbi.books
package control

import domain.*

import zio.*

trait AuthControl {
  def userLogin(
    email: Email,
    password: Password,
  ): ZIO[Any, DomainError, JwtToken]
}

object AuthControl {
  val layer: ULayer[AuthControl] = ZLayer.succeed(AuthControlLive())
}
