/*
 * © 2024 Misha Tavkhelidze. Some rights reserved.
 */

package ge.zgharbi.books
package services.auth

import domain.{Email, Password}

final case class AuthCreds(email: Email, password: Password)
