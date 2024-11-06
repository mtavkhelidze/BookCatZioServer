/*
 * © 2024 Misha Tavkhelidze. Some rights reserved.
 */

package ge.zgharbi.books
package api

import api.ApiErrorMessage.*

import com.github.plokhotnyuk.jsoniter_scala.core.*
import com.github.plokhotnyuk.jsoniter_scala.macros.*
import sttp.tapir.*
import sttp.tapir.Schema.annotations.*

import java.util.UUID
import scala.compiletime.constValue
import scala.reflect.ClassTag

object ApiErrorMessage {
  type MessageType = NONE | JSON_DECODE_FAILURE | INVALID_CREDENTIALS |
    INTERNAL_SERVER_ERROR

  // @note: at some point we shall translate those
  type INVALID_CREDENTIALS = "Invalid credentials."
  type JSON_DECODE_FAILURE = "Failed to decode JSON."
  type INTERNAL_SERVER_ERROR = "Internal server error."
  type NONE = ""
}

// @note: This probably could be simpler
sealed trait ApiError {
  def $tag: UUID
  def details: List[String]
  def error: MessageType
}

sealed case class InternalServerError(
  override val error: INTERNAL_SERVER_ERROR = constValue[INTERNAL_SERVER_ERROR],
  override val details: List[String] = List.empty,
  override val $tag: UUID = UUID.randomUUID(),
) extends ApiError

case class JsonDecodeFailureError(
  override val error: JSON_DECODE_FAILURE = constValue[JSON_DECODE_FAILURE],
  override val details: List[String] = List.empty,
  override val $tag: UUID = UUID.randomUUID(),
) extends ApiError

case class InvalidCredentialsError(
  override val error: INVALID_CREDENTIALS = constValue[INVALID_CREDENTIALS],
  override val details: List[String] = List.empty,
  override val $tag: UUID = UUID.randomUUID(),
) extends ApiError

object ApiError {
  import domain.config.{jsonIterConfig, given}

  given JsonValueCodec[ApiError] =
    JsonCodecMaker.make(jsonIterConfig)

//  given JsonValueCodec[HttpErrorMessage.MessageType] =
//    new JsonValueCodec[MessageType] {
//      override def decodeValue(
//        in: JsonReader,
//        default: MessageType,
//      ): MessageType =
//        in.readString(default).asInstanceOf[MessageType]
//
//      override def encodeValue(x: MessageType, out: JsonWriter): Unit =
//        out.writeVal(x)
//
//      override def nullValue: MessageType = null.asInstanceOf[MessageType]
//    }

  given schema: Schema[ApiError] = Schema.derived[ApiError]

  //  private val detailsMessageExample =
//    List("Details about the error.", "Can be empty.")
//
//  inline def httpCodeFor[E <: ControlError: ClassTag]: StatusCode = {
//    val c: ClassTag[E] = summon[ClassTag[E]]
//    inline c match {
//      case _: ClassTag[JsonDecodeFailureError] => StatusCode.UnprocessableEntity
//      case _: ClassTag[InvalidCredentialsError] => StatusCode.Unauthorized
//      case _ => StatusCode.InternalServerError
//    }
//
//  }
  inline def exmapleOf[E <: ApiError: ClassTag]: ApiError = {
    val c: ClassTag[E] = summon[ClassTag[E]]
    inline c match {
      case _: ClassTag[JsonDecodeFailureError] =>
        JsonDecodeFailureError()
      case _: ClassTag[InternalServerError] =>
        InternalServerError()
      case _: ClassTag[InvalidCredentialsError] =>
        InvalidCredentialsError()
    }
  }

}

//  case CannotCompleteOperation(details: String = "Cannot complete operation.")
//      extends DomainError("CannotCompleteOperation")
//  case InvalidCredentials(details: String = "Invalid credentials.")
//      extends DomainError("InvalidCredentials")
//
//trait WithJson
//
//object WithJson {
//  extension (json: String) {
//    inline def fromJson[T](using codec: JsonValueCodec[T]): T =
//      readFromString[T](json)
//  }
//  extension [T](t: T) {
//    inline def asJson(using codec: JsonValueCodec[T]) = writeToString[T](t)
//  }
//}
