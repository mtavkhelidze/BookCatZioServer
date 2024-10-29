package ge.zgharbi.books
package api

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import sttp.tapir.testing.{EndpointVerificationError, EndpointVerifier}

class ApiTest extends AnyWordSpec with Matchers {
  "api" must {
    "not have chadowed endpoints" in {
      val resultSet: Set[EndpointVerificationError] =
        EndpointVerifier(api.auth.user.endpoints)
      resultSet must be(empty)

    }
  }
}
