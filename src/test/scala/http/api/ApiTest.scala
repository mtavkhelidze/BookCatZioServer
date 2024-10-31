package ge.zgharbi.books
package http.api

import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.must.Matchers
import sttp.tapir.testing.{EndpointVerificationError, EndpointVerifier}

class ApiTest extends AsyncFlatSpec with Matchers {
  it must "not have chadowed endpoints" in {
    val resultSet: Set[EndpointVerificationError] =
      EndpointVerifier(http.api.auth.endpoints)
    resultSet must be(empty)
  }
}
