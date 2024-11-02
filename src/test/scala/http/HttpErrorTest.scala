/*
 * © 2024 Misha Tavkhelidze. Some rights reserved.
 */

package ge.zgharbi.books
package http

import com.github.plokhotnyuk.jsoniter_scala.core.{readFromString, writeToString}
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class HttpErrorTest
    extends AnyWordSpec
    with Matchers
    with ScalaCheckPropertyChecks {

  "DomainError JSON encoding/decoding" must {
    "be isomorphic" in {
      httpErrorChildren.foreach { error =>
        readFromString[HttpError](writeToString(error)) must equal(error)
      }
    }
  }
  "DomainError" when {
    "encoded to JSON" must {
      s"have ${Defs.JSON_ENTITY_DISCRIMINATOR}, message, and details fields present" in {
        httpErrorChildren.foreach { error =>
          writeToString(error) must (include(
            Defs.JSON_ENTITY_DISCRIMINATOR,
          ) and include("message") and include("details"))
        }
      }
      s"have ${Defs.JSON_ENTITY_DISCRIMINATOR} set to case class name" in {
        httpErrorChildren.foreach { error =>
          writeToString(error) must include(
            s""""${Defs.JSON_ENTITY_DISCRIMINATOR}":"${error.getClass.getSimpleName}"""",
          )
        }
      }
    }
    "encoded to JSON _without_ details" must {
      "have details field set to empty array" in {
        httpErrorChildren.foreach { error =>
          writeToString(error) must include(""""details":[]""")
        }
      }
    }
  }
}
