/*
 * © 2024 Misha Tavkhelidze. Some rights reserved.
 */

package ge.zgharbi.books
package services.auth

import domain.UserId

import zio.IO

trait AuthService {
  def authenticate(creds: AuthCreds): IO[AuthError, AuthUser]
  def register(creds: AuthCreds): IO[AuthError, UserId]
}
