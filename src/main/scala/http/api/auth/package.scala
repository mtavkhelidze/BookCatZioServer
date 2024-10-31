package ge.zgharbi.books
package http.api

import domain.config.{jsonIterConfig, schemaConfig}

import com.github.plokhotnyuk.jsoniter_scala.core.JsonValueCodec
import com.github.plokhotnyuk.jsoniter_scala.macros.JsonCodecMaker
import sttp.tapir.Schema

package object auth {
  private[api] val endpoints = List(User.login)
  import User.{LoginRequest, LoginResponse}

  given Schema[LoginRequest] = Schema.derived[LoginRequest](using schemaConfig)

  given Schema[LoginResponse] =
    Schema.derived[LoginResponse](using schemaConfig)

  given JsonValueCodec[LoginRequest] =
    JsonCodecMaker.make[LoginRequest](jsonIterConfig)

  given JsonValueCodec[LoginResponse] =
    JsonCodecMaker.make[LoginResponse](jsonIterConfig)

}