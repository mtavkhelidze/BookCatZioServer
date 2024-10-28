/*
 * © 2024 Misha Tavkhelidze. Some rights reserved.
 */

package ge.zgharbi.books

import services.auth.AuthService
import services.live.DummyAuthService

import zio.*

package object services {
  val authService: ZLayer[Any, Nothing, AuthService] = DummyAuthService.layer
}
