package ge.zgharbi.books

import domain.DomainError
import domain.DomainError.JsonDecodeFailure

import sttp.model.StatusCode
import sttp.tapir.{oneOfVariantClassMatcher, statusCode, EndpointOutput}
import sttp.tapir.json.jsoniter.jsonBody
import sttp.tapir.server.model.ValuedEndpointOutput

import scala.reflect.{classTag, ClassTag}

package object api {
  def defaultErrorHandler(e: String): ValuedEndpointOutput[?] =
    ValuedEndpointOutput[DomainError](
      statusCode(StatusCode.UnprocessableEntity).and(jsonBody[DomainError]),
      JsonDecodeFailure(e),
    )

  def errorVariant[ErrorClassType <: DomainError: ClassTag](
    statusCode: StatusCode,
    example: ErrorClassType,
  ): EndpointOutput.OneOfVariant[DomainError] = {
    oneOfVariantClassMatcher(
      statusCode,
      jsonBody[DomainError]
        .example(example)
        .description(example.getClass.getSimpleName),
      classTag[ErrorClassType].runtimeClass,
    )
  }
}
