/*
 * © 2024 Misha Tavkhelidze. Some rights reserved.
 */

package ge.zgharbi.books

import control.AuthControl
import http.Handlers.defaultErrorHandler
import http.Routes.apiRoutes

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
      .defaultHandlers(defaultErrorHandler, true)
      .appendInterceptor(CORSInterceptor.default)
      .options

  override def run = {
    Server
      .serve(ZioHttpInterpreter(serverOptions).toHttp(serverEndpoints))
      .provide(Server.defaultWithPort(8080), AuthControl.layer)
  }

  private def serverEndpoints = {
    val sw = SwaggerInterpreter(openAPIInterpreterOptions =
      OpenAPIDocsOptions.default.copy(
        defaultDecodeFailureOutput = _ => Option.empty,
        markOptionsAsNullable = true,
      ),
    )
      .fromServerEndpoints(apiRoutes, """Book Cat ¯\_(ツ)_/¯ Zio""", "1.0.0")
    apiRoutes ++ sw
  }
}
