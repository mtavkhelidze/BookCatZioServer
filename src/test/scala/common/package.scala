package ge.zgharbi.books

import http.HttpError.{InvalidCredentials, JsonDecodeFailure}
import http.HttpError

package object common {

  import scala.compiletime.{constValue, constValueTuple, error}
  import scala.deriving.Mirror

  extension (s: String) {
    def toError: HttpError = s match {
      case "InvalidCredentials" => InvalidCredentials()
      case "JsonDecodeFailure"  => JsonDecodeFailure()
    }
  }
  inline def domainErrorChildren(using
    m: Mirror.SumOf[HttpError],
  ): List[HttpError] =
    val values = constValueTuple[m.MirroredElemLabels].productIterator.toList
    values.map(_.toString).map(_.toError)
}
