package ge.zgharbi.books
package http

import http.HttpError.{*, given}

import sttp.model.StatusCode
import sttp.tapir.{oneOfVariantClassMatcher, statusCode, EndpointOutput}
import sttp.tapir.json.jsoniter.jsonBody
import sttp.tapir.server.model.ValuedEndpointOutput

import scala.reflect.{classTag, ClassTag}

package object api {
  def defaultErrorHandler(e: String): ValuedEndpointOutput[?] =
    ValuedEndpointOutput[HttpError](
      statusCode(StatusCode.UnprocessableEntity).and(jsonBody[HttpError]),
      JsonDecodeFailure(Some(e)),
    )

  inline def errorVariant[ErrorClassType <: HttpError: ClassTag]
    : EndpointOutput.OneOfVariant[HttpError] = {
    val example = HttpError.exmapleOf[ErrorClassType]
    val statusCode = HttpError.httpCodeFor[ErrorClassType]
    oneOfVariantClassMatcher(
      statusCode,
      jsonBody[HttpError]
        .example(HttpError.exmapleOf[ErrorClassType])
        .description(example.message),
      classTag[ErrorClassType].runtimeClass,
    )
  }
}
