/*
 * © 2024 Misha Tavkhelidze. Some rights reserved.
 */

package ge.zgharbi.books
package domain

import com.github.plokhotnyuk.jsoniter_scala.core.{JsonReader, JsonValueCodec, JsonWriter}
import sttp.tapir.{Schema, SchemaType}

opaque type JwtToken <: String = String
object JwtToken:
  given Schema[JwtToken] = Schema(SchemaType.SString())
    .format("JWT jwtToken")
    .encodedExample("eyJhbGciOi...")

  given JsonValueCodec[JwtToken] = new JsonValueCodec[JwtToken] {
    override def decodeValue(in: JsonReader, default: JwtToken): JwtToken =
      JwtToken(in.readString(default))

    override def encodeValue(x: JwtToken, out: JsonWriter): Unit =
      out.writeVal(x)

    override def nullValue: JwtToken = null.asInstanceOf[JwtToken]
  }

  inline def apply(s: String): JwtToken = s

end JwtToken
