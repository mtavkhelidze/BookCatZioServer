/*
 * © 2024 Misha Tavkhelidze. Some rights reserved.
 */

package ge.zgharbi.books

import auth.AuthCtrlLive
import services.auth.AuthService

import sttp.tapir.ztapir.ZServerEndpoint
import zio.*

object ServerEndpoints {
  def routes: ZIO[AuthService, Nothing, List[ZServerEndpoint[Any, Any]]] =
    AuthCtrlLive.live.map(_.routes)
}
