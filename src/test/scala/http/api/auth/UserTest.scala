package ge.zgharbi.books
package http.api.auth

import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.must.Matchers
import sttp.tapir.testing.{EndpointVerificationError, EndpointVerifier}

import scala.concurrent.Future

class UserTest extends AsyncFlatSpec with Matchers {
//  val backendStub =
//    TapirStubInterpreter(SttpBackendStub(new RIOMonadError[Any]))
//      .whenServerEndpoint(http.AuthUser.login)
//      .thenRunLogic()
//      .backend()
//
//  it must "work" in {
//    val response = basicRequest
//      .get(uri"http://test.com/api/")
//      .send(backendStub)
//
//    println(response.map(_.body).map(_.toString))
//  }
  it must "not have chadowed endpoints" in {
    val resultSet: Set[EndpointVerificationError] =
      EndpointVerifier(http.api.auth.endpoints)
    resultSet must be(empty)
  }

}
