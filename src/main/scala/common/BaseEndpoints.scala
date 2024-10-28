/*
 * © 2024 Misha Tavkhelidze. Some rights reserved.
 */

package ge.zgharbi.books
package common

import domain.DomainError
import domain.DomainError.{*, given}

import sttp.model.StatusCode
import sttp.tapir.*
import sttp.tapir.json.jsoniter.jsonBody
import sttp.tapir.server.model.ValuedEndpointOutput

import scala.reflect.*

trait BaseEndpoints {
  val publicEndpoint = endpoint

  protected def errorVariant[ErrorClassType <: DomainError: ClassTag](
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

object BaseEndpoints {
  def defaultErrorHandler(e: String): ValuedEndpointOutput[?] =
    ValuedEndpointOutput[DomainError](
      statusCode(StatusCode.UnprocessableEntity).and(jsonBody[DomainError]),
      JsonDecodeFailure(e),
    )
}
