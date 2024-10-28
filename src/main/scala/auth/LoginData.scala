/*
 * © 2024 Misha Tavkhelidze. Some rights reserved.
 */

package ge.zgharbi.books
package auth

import domain.*

import com.github.plokhotnyuk.jsoniter_scala.core.JsonValueCodec
import com.github.plokhotnyuk.jsoniter_scala.macros.JsonCodecMaker
import sttp.tapir.Schema

final case class LoginRequest(email: Email, password: Password)
final case class LoginResponse(jwtToken: JwtToken)

private[auth] object codecs {

  import CodecConfigs.{jsonIter, schemaConfig}

  given Schema[LoginRequest] = Schema.derived[LoginRequest](using schemaConfig)
  given Schema[LoginResponse] =
    Schema.derived[LoginResponse](using schemaConfig)

  given JsonValueCodec[LoginRequest] =
    JsonCodecMaker.make[LoginRequest](jsonIter)
  given JsonValueCodec[LoginResponse] =
    JsonCodecMaker.make[LoginResponse](jsonIter)
}
