/*
 * © 2024 Misha Tavkhelidze. Some rights reserved.
 */

package ge.zgharbi.books
package http

import com.github.plokhotnyuk.jsoniter_scala.core.*
import com.github.plokhotnyuk.jsoniter_scala.macros.*
import sttp.model.StatusCode
import sttp.tapir.*
import sttp.tapir.Schema.*
import sttp.tapir.Schema.annotations.*

import scala.compiletime.constValue
import scala.reflect.ClassTag

object HttpErrorMessage {
  type Type = JSON_DECODE_FAILURE | INVALID_CREDENTIALS | NONE

  // @note: at some point we shall translate those
  type INVALID_CREDENTIALS = "Invalid credentials."
  type JSON_DECODE_FAILURE = "Failed to decode JSON."
  type NONE = ""
}

enum HttpError(
  val message: HttpErrorMessage.Type = constValue[HttpErrorMessage.NONE],
  val details: List[String] = List(),
) {
  case JsonDecodeFailureError(
    override val details: List[String] = List(),
    override val message: HttpErrorMessage.JSON_DECODE_FAILURE =
      constValue[HttpErrorMessage.JSON_DECODE_FAILURE],
  ) extends HttpError

  case InvalidCredentialsError(
    override val details: List[String] = List(),
    override val message: HttpErrorMessage.INVALID_CREDENTIALS =
      constValue[HttpErrorMessage.INVALID_CREDENTIALS],
  ) extends HttpError
}
//  case CannotCompleteOperation(details: String = "Cannot complete operation.")
//      extends DomainError("CannotCompleteOperation")
//  case InvalidCredentials(details: String = "Invalid credentials.")
//      extends DomainError("InvalidCredentials")

trait WithJson

object WithJson {
  extension (json: String) {
    inline def fromJson[T](using codec: JsonValueCodec[T]): T =
      readFromString[T](json)
  }
  extension [T](t: T) {
    inline def asJson(using codec: JsonValueCodec[T]) = writeToString[T](t)
  }
}

object HttpError {
  private val detailsMessageExample = List("Details about the error.", "Can be empty.")
  inline def httpCodeFor[E <: HttpError: ClassTag]: StatusCode = {
    val c: ClassTag[E] = summon[ClassTag[E]]
    inline c match {
      case _: ClassTag[JsonDecodeFailureError]  => StatusCode.UnprocessableEntity
      case _: ClassTag[InvalidCredentialsError] => StatusCode.Unauthorized
    }

  }
  inline def exmapleOf[E <: HttpError: ClassTag]: HttpError = {
    val c: ClassTag[E] = summon[ClassTag[E]]
    inline c match {
      case _: ClassTag[JsonDecodeFailureError] =>
        JsonDecodeFailureError(detailsMessageExample)
      case _: ClassTag[InvalidCredentialsError] =>
        InvalidCredentialsError()
    }
  }

  import domain.config.{jsonIterConfig, given}

  given codec: JsonValueCodec[HttpError] = JsonCodecMaker.make(jsonIterConfig)

  given schema: Schema[HttpError] = Schema.derived[HttpError]
}
