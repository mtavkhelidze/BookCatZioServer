/*
 * © 2024 Misha Tavkhelidze. Some rights reserved.
 */

package ge.zgharbi.books

import common.BaseEndpoints
import services.live.DummyAuthService

import sttp.tapir.docs.openapi.OpenAPIDocsOptions
import sttp.tapir.server.interceptor.cors.CORSInterceptor
import sttp.tapir.server.ziohttp.*
import sttp.tapir.swagger.bundle.SwaggerInterpreter
import zio.*
import zio.http.*
import zio.logging.backend.SLF4J

object BookCatZioServer extends ZIOAppDefault {

  override val bootstrap: ZLayer[ZIOAppArgs, Any, Any] =
    Runtime.removeDefaultLoggers >>> SLF4J.slf4j

  private val serverOptions: ZioHttpServerOptions[Any] =
    ZioHttpServerOptions.customiseInterceptors
      .defaultHandlers(BaseEndpoints.defaultErrorHandler, true)
      .appendInterceptor(CORSInterceptor.default)
      .options

  override def run = {
    Console.print(Defs.Banner) *> serverEndpoints
      .flatMap(eps =>
        Server
          .serve(ZioHttpInterpreter(serverOptions).toHttp(eps))
      )
      .provide(Server.defaultWithPort(Defs.SERVER_PORT), DummyAuthService.layer)
  }

  private def serverEndpoints =
    for {
      api <- ServerEndpoints.routes
      sw = SwaggerInterpreter(openAPIInterpreterOptions =
        OpenAPIDocsOptions.default.copy(defaultDecodeFailureOutput =
          _ => Option.empty,
        ),
      )
        .fromServerEndpoints(api, """Book Cat ¯\_(ツ)_/¯ Zio""", "1.0.0")
    } yield (api ++ sw)
}
