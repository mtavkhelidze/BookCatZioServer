/*
 * © 2024 Misha Tavkhelidze. Some rights reserved.
 */

package ge.zgharbi.books
package domain

import com.github.plokhotnyuk.jsoniter_scala.core.{JsonReader, JsonValueCodec, JsonWriter}
import sttp.tapir.{Schema, SchemaType}

opaque type Password <: String = String
object Password:
  given Schema[Password] = Schema(SchemaType.SString())
    .encodedExample("User123!")
    .format("Length >= 8, at least 1 number, 1 special and 1 uppercase.")

  given JsonValueCodec[Password] = new JsonValueCodec[Password] {
    override def decodeValue(in: JsonReader, default: Password): Password = {
      // validations go here
      Password(in.readString(default))
    }

    override def encodeValue(x: Password, out: JsonWriter): Unit =
      out.writeVal(x)

    override def nullValue: Password = null.asInstanceOf[Password]
  }

  inline def apply(s: String): Password = s

  inline given Conversion[Password, String] with
    inline def apply(pass: Password): String = pass
end Password
