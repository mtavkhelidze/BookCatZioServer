package ge.zgharbi.books
package http

import http.HttpError.JsonDecodeFailure

import sttp.model.StatusCode
import sttp.tapir.json.jsoniter.jsonBody
import sttp.tapir.server.model.ValuedEndpointOutput
import sttp.tapir.statusCode

object Handlers {
  def defaultErrorHandler(e: String): ValuedEndpointOutput[?] =
    ValuedEndpointOutput[HttpError](
      statusCode(StatusCode.UnprocessableEntity).and(jsonBody[HttpError]),
      JsonDecodeFailure(Some(e)),
    )
}
