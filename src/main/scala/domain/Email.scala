/*
 * © 2024 Misha Tavkhelidze. Some rights reserved.
 */

package ge.zgharbi.books
package domain

import com.github.plokhotnyuk.jsoniter_scala.core.{JsonReader, JsonValueCodec, JsonWriter}
import sttp.tapir.{Schema, SchemaType}

opaque type Email <: String = String
object Email:

  given JsonValueCodec[Email] = new JsonValueCodec[Email] {
    override def decodeValue(in: JsonReader, default: Email): Email = {
      // validation -> decodeError(String)
      Email(in.readString(default))
    }

    override def encodeValue(x: Email, out: JsonWriter): Unit =
      out.writeVal(x)

    override def nullValue: Email = null.asInstanceOf[Email]
  }

  inline def apply(s: String): Email = s

  given emailSchema: Schema[Email] =
    Schema[Email](SchemaType.SString()).format("email")

  inline given Conversion[Email, String] with
    inline def apply(email: Email): String = email

end Email
