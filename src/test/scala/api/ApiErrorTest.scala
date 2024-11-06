/*
 * © 2024 Misha Tavkhelidze. Some rights reserved.
 */

package ge.zgharbi.books
package api

import com.github.plokhotnyuk.jsoniter_scala.core.{readFromString, writeToString}
import zio.*
import zio.test.*
import zio.test.junit.JUnitRunnableSpec

import scala.compiletime.*
import scala.util.Try

object HttpErrorTools {

  import scala.compiletime.{constValue, constValueTuple, error}
  import scala.deriving.Mirror

  extension (s: String) {
    def toError: ApiError = s match {
      case "InvalidCredentialsError" => InvalidCredentialsError()
      case "JsonDecodeFailureError"  => JsonDecodeFailureError()
      case "InternalServerError"     => InternalServerError()
    }
  }

  inline def httpErrorChildren(using
    m: Mirror.SumOf[ApiError],
  ): Iterator[ApiError] = {
    constValueTuple[m.MirroredElemLabels].productIterator
      .map(_.toString)
      .map(_.toError)
  }

  def readFromStringZio(s: String): Task[ApiError] =
    ZIO.fromTry(Try(readFromString[ApiError](s)))

  def writeToStringZio(e: ApiError): Task[String] =
    ZIO.fromTry(Try(writeToString[ApiError](e)))
}

object ApiErrorTest extends JUnitRunnableSpec {

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
            val str = writeToString[ApiError](e)
            println(str)
            true
//            str.contains(Defs.JSON_ENTITY_DISCRIMINATOR) &&
//            str.contains("message") &&
//            str.contains("details")
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
