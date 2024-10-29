/*
 * © 2024 Misha Tavkhelidze. Some rights reserved.
 */

package ge.zgharbi.books
package common

import domain.DomainError.*

import com.github.plokhotnyuk.jsoniter_scala.core.{readFromString, writeToString}
import org.scalacheck.Gen
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class DomainErrorTest
    extends AnyWordSpec
    with Matchers
    with ScalaCheckPropertyChecks {

  import domain.DomainError.given

  val errorCase = List(
//    CannotCompleteOperation.apply,
    InvalidCredentials.apply,
    JsonDecodeFailure.apply,
//    UserNotFound.apply,
  )
  "DomainError case classes" when {
    "decoded from JSON" must {
      "not care about order of fields" in {
        forAll(Gen.alphaNumStr) { str =>
          errorCase.map(_(str)).foreach { cls =>
            val $type = cls.getClass.getSimpleName
            val details = cls.details
            val jsonOne = s"""{"$$type":"${$type}","details":"$details"}"""
            val jsonTwo = s"""{"details":"$str", "$$type":"${$type}"}"""
            readFromString(jsonOne) must equal(readFromString(jsonTwo))
          }
        }
      }
    }
    "encoded to JSON" must {
      "have $type discriminator and correct details set" in {
        forAll(Gen.alphaNumStr) { str =>
          errorCase.map(_(str)).foreach { error =>
            writeToString(error) must equal(
              s"""{"$$type":"${error.getClass.getSimpleName}","details":"$str"}""",
            )
          }
        }
      }
    }
  }
}
