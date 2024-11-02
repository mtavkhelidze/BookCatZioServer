package ge.zgharbi.books

import http.HttpError.{InvalidCredentialsError, JsonDecodeFailureError}
import http.HttpError

package object http {

  import scala.compiletime.{constValue, constValueTuple, error}
  import scala.deriving.Mirror

  extension (s: String) {
    def toError: HttpError = s match {
      case "InvalidCredentials" => InvalidCredentialsError()
      case "JsonDecodeFailure"  => JsonDecodeFailureError()
    }
  }
  inline def httpErrorChildren(using
    m: Mirror.SumOf[HttpError],
  ): List[HttpError] =
    val values = constValueTuple[m.MirroredElemLabels].productIterator.toList
    values.map(_.toString).map(_.toError)
}
