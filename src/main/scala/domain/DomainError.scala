/*
 * © 2024 Misha Tavkhelidze. Some rights reserved.
 */

package ge.zgharbi.books
package domain

import com.github.plokhotnyuk.jsoniter_scala.core.*
import com.github.plokhotnyuk.jsoniter_scala.macros.*
import sttp.tapir.*
import sttp.tapir.Schema.*
import sttp.tapir.Schema.annotations.*
import sttp.tapir.SchemaType.{SchemaWithValue, SCoproduct, SRef}

import scala.reflect.ClassTag
//
//trait AsJson[T <: DomainError] {
//  import CodecConfigs.{jsonIter}
//}

enum DomainError(final val $type: String = this.getClass.getSimpleName) {
  val details: String
  case JsonDecodeFailure(
    override val details: String = "Failed to decode JSON.",
  ) extends DomainError

  case InvalidCredentials(
    override val details: String =
      "User with given email/password combination not found.",
  ) extends DomainError
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

object DomainError {
  import CodecConfigs.{jsonIter, schemaConfig}

  given codec: JsonValueCodec[DomainError] = JsonCodecMaker.make(jsonIter)

  given schema: Schema[DomainError] =
    Schema.derived[DomainError](using schemaConfig)
}
