package ge.zgharbi.books

import http.HttpError.{InvalidCredentialsError, JsonDecodeFailureError}

package object http {

  import scala.compiletime.{constValue, constValueTuple, error}
  import scala.deriving.Mirror

  extension (s: String) {
    def toError: HttpError = s match {
      case "InvalidCredentialsError" => InvalidCredentialsError()
      case "JsonDecodeFailureError"  => JsonDecodeFailureError()
      case "InternalServerError"     => JsonDecodeFailureError()
    }
  }
  inline def httpErrorChildren(using
    m: Mirror.SumOf[HttpError],
  ): Iterator[HttpError] =
    constValueTuple[m.MirroredElemLabels].productIterator
      .map(_.toString)
      .map(_.toError)

}
