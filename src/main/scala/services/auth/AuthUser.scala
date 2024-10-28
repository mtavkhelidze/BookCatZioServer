/*
 * © 2024 Misha Tavkhelidze. Some rights reserved.
 */

package ge.zgharbi.books
package services.auth

import domain.{JwtToken, UserId}

final case class AuthUser(userId: UserId, jwtToken: JwtToken)
