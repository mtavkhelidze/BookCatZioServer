package ge.zgharbi.books
package http

import http.HttpError.JsonDecodeFailureError

import sttp.model.StatusCode
import sttp.tapir.json.jsoniter.jsonBody
import sttp.tapir.server.interceptor.exception.ExceptionHandler
import sttp.tapir.server.model.ValuedEndpointOutput
import sttp.tapir.statusCode
import scala.reflect.ClassTag

object Handlers {
  inline def logAndRespond[E <: HttpError: ClassTag](e: E) = {
    def code = HttpError.httpCodeFor[E]
    println(">>>>>>> " + code)
  }
  def excheptionHandler[F[_]]: ExceptionHandler[F] = ExceptionHandler.pure[F] {
    ctx =>
      logAndRespond(JsonDecodeFailureError())
      None
  }
//  def handler = DecodeFailureHandler.pure[_] { ctx =>
//    ctx match {
//      case _ =>
//        println(ctx)
//        None
//    }
//
//  }
  def defaultErrorHandler(e: String): ValuedEndpointOutput[?] =
    ValuedEndpointOutput[HttpError](
      statusCode(StatusCode.UnprocessableEntity).and(jsonBody[HttpError]),
      JsonDecodeFailureError(List(e)),
    )
}
