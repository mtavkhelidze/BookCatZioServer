/*
 * © 2024 Misha Tavkhelidze. Some rights reserved.
 */

package ge.zgharbi.books
package http

import com.github.plokhotnyuk.jsoniter_scala.core.{readFromString, writeToString}
import zio.*
import zio.test.*
import zio.test.junit.JUnitRunnableSpec

import scala.util.Try

object HttpErrorTools {

  import HttpError.{*, given}

  import scala.compiletime.{constValue, constValueTuple, error}
  import scala.deriving.Mirror

  extension (s: String) {
    def toError: HttpError = s match {
      case "InvalidCredentialsError" => InvalidCredentialsError()
      case "JsonDecodeFailureError"  => JsonDecodeFailureError()
      case "InternalServerError"     => InternalServerError()
    }
  }

  inline def httpErrorChildren(using
    m: Mirror.SumOf[HttpError],
  ): Iterator[HttpError] =
    constValueTuple[m.MirroredElemLabels].productIterator
      .map(_.toString)
      .map(_.toError)

  def readFromStringZio = (s: String) =>
    ZIO.fromTry(Try(readFromString[HttpError](s)))

  def writeToStringZio = (e: HttpError) =>
    ZIO.fromTry(Try(writeToString[HttpError](e)))
}

object HttpErrorTest extends JUnitRunnableSpec {

  import HttpErrorTools.*

  private val suits = suite("JSON encoding") {
    test("must be isomorphic") {
      for {
        errors <- ZIO.succeed(httpErrorChildren.toList)
        result <- ZIO.forall(errors)(e =>
          writeToStringZio(e).flatMap(readFromStringZio).map(_ == e),
        )
      } yield (assertTrue(result))
    }
  } + suite("when encoded to JSON") {
    test("must have all required fields present") {
      val errors = httpErrorChildren.toList
      for {
        result <- ZIO.forall(errors) { e =>
          ZIO.fromTry(Try {
            val str = writeToString[HttpError](e)
            str.contains(Defs.JSON_ENTITY_DISCRIMINATOR) &&
              str.contains("message") &&
              str.contains("details")
          })
        }
      } yield assertTrue(result)
    }
  }
  override def spec: Spec[TestEnvironment & Scope, Any] =
    suite("http.HttpError")(suits)
}
//  "DomainError" when {
//    "encoded to JSON" must {
//      s"have ${Defs.JSON_ENTITY_DISCRIMINATOR}, message, and details fields present" in {
//        httpErrorChildren.foreach { error =>
//          writeToString(error) must (include(
//            Defs.JSON_ENTITY_DISCRIMINATOR,
//          ) and include("message") and include("details"))
//        }
//      }
//      s"have ${Defs.JSON_ENTITY_DISCRIMINATOR} set to case class name" in {
//        httpErrorChildren.foreach { error =>
//          writeToString(error) must include(
//            s""""${Defs.JSON_ENTITY_DISCRIMINATOR}":"${error.getClass.getSimpleName}"""",
//          )
//        }
//      }
//    }
//    "encoded to JSON _without_ details" must {
//      "have details field set to empty array" in {
//        httpErrorChildren.foreach { error =>
//          writeToString(error) must include(""""details":[]""")
//        }
//      }
//    }
//  }
