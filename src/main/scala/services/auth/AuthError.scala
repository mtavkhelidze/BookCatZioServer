/*
 * © 2024 Misha Tavkhelidze. Some rights reserved.
 */

package ge.zgharbi.books
package services.auth

trait Misha[+T] { def message: String }

sealed trait AuthServiceError[+T <: Misha[T]]

sealed case class UserNotFound(message: String) extends Misha[UserNotFound]

enum AuthError(val message: String) {
  case ServiceUnavailable extends AuthError("Service unavailable.")
  case InvalidCredentials extends AuthError("Invalid credentials.")
  case UserAlreadyExists extends AuthError(s"User already exists.")
}
